package org.artfable.telegram.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.URI

/**
 * @author aveselov
 * @since 08/08/2020
 */
class UrlHelperTest {
    @Test
    fun getUri() {
        assertEquals(URI("https://url-test"), UrlHelper.getUri("https://url{test}", mapOf(Pair("test", "-test"))))
        assertEquals(URI("https://url%20test"), UrlHelper.getUri("https://url{test}", mapOf(Pair("test", " test"))))
        assertEquals(URI("https://url-test?param=test"), UrlHelper.getUri("https://url{test}", mapOf(Pair("test", "-test")), mapOf(Pair("param", "test"))))
    }
}