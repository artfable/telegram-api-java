buildscript {
    repositories {
        mavenLocal()
        mavenCentral()

    }
}

plugins {
    java
    kotlin("jvm") version "1.5.21"
    id("artfable.artifact") version "0.0.3"
    id("com.jfrog.artifactory") version "4.24.14"
    `maven-publish`
}

group = "com.artfable.telegram"
version = "1.0.1"

val kotlin_version = "1.5.21"
val spring_version = "5.3.3"
val spring_boot_version = "2.4.2"
val jackson_version = "2.11.1"
val slf4j_version = "1.7.22"
val javax_annotation_version = "1.3.2"

val junit_version = "5.6.2"
val mockito_version = "3.4.6"

dependencies {
    api("javax.annotation:javax.annotation-api:$javax_annotation_version")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")
    api("org.slf4j:slf4j-api:$slf4j_version")

    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockito_version")
}

repositories {
    mavenLocal()
    mavenCentral()
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourceJar"])
            artifact(tasks["javadocJar"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom {
                description.set("API for Telegram bots")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://raw.githubusercontent.com/artfable/telegram-api-java/master/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("artfable")
                        name.set("Artem Veselov")
                        email.set("art-fable@mail.ru")
                    }
                }
            }
        }
    }
}

artifactory {
    setContextUrl("https://artfable.jfrog.io/artifactory/")
    publish {
        repository {
            setRepoKey("default-maven-local")
            setUsername(artifactoryCredentials.user)
            setPassword(artifactoryCredentials.key)
        }
        defaults {
            publications ("mavenJava")

            setPublishArtifacts(true)
            setPublishPom(true)
            setPublishIvy(false)
        }
    }
}