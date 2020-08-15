import com.jfrog.bintray.gradle.BintrayExtension

buildscript {
    repositories {
        mavenLocal()
        jcenter()
        maven(url = "http://dl.bintray.com/artfable/gradle-plugins")

    }

    dependencies {
        classpath("com.github.artfable.gradle:gradle-artifact-plugin:0.0.1")
    }
}

plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.5"
}

apply(plugin = "artfable.artifact")
apply(plugin = "maven-publish")

group = "org.artfable"
version = "0.3.0"

val kotlin_version = "1.3.72"
val spring_version = "5.2.8.RELEASE"
val jackson_version = "2.11.1"
val slf4j_version = "1.7.22"
val javax_annotation_version = "1.3.2"

val junit_version = "5.6.2"
val mockito_version = "3.4.6"

dependencies {
    implementation("javax.annotation:javax.annotation-api:$javax_annotation_version")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("org.springframework:spring-context:$spring_version")
    implementation("org.springframework:spring-core:$spring_version")
    implementation("org.springframework:spring-web:$spring_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")
    implementation("org.slf4j:slf4j-api:$slf4j_version")

    testImplementation("org.springframework:spring-test:$spring_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockito_version")
}

repositories {
    mavenLocal()
    jcenter()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    test {
        useJUnitPlatform()
    }
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourceJar"])
            artifact(tasks["javadocJar"])
            groupId = "org.artfable"
            artifactId = "telegram-api"
            version = project.version.toString()
        }
    }
}

configure<BintrayExtension> {
    user = if (project.hasProperty("bintrayUser")) {
        project.ext["bintrayUser"] as String
    } else System.getenv("BINTRAY_USER")
    key = if (project.hasProperty("bintrayKey")) {
        project.ext["bintrayKey"] as String
    } else System.getenv("BINTRAY_KEY")
    setPublications("mavenJava")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "telegram-tools"
        name = "telegram-api"
        setLicenses("MIT")
        vcsUrl = "https://github.com/artfable/telegram-api-java.git"
    })
}