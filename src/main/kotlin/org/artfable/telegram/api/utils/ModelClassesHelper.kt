package org.artfable.telegram.api.utils

import kotlin.reflect.KProperty1
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

/**
 * @author aveselov
 * @since 22/01/2021
 */

fun Any.notNullFieldsToString(): String {
    return "${this::class.simpleName}(${
        this::class.memberProperties.asSequence()
            .map { field ->
                val value = field.getter.call(this)
                if (value != null) field.name + "=" + if (isPrimitive(field)) value else value.notNullFieldsToString() else null
            }
            .filterNotNull()
            .joinToString()
    })"
}

private fun isPrimitive(field: KProperty1<out Any, *>): Boolean {
    return field.returnType.isSubtypeOf(Number::class.createType())
            || field.returnType.isSubtypeOf(Boolean::class.createType())
            || field.returnType.isSubtypeOf(String::class.createType())
            || field.returnType.isSubtypeOf(Enum::class.createType(listOf(KTypeProjection(null, null))))
}