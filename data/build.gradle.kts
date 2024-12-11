import java.util.Properties

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("jacoco")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test)

    testImplementation(libs.ktor.client.mock)
}

// Load properties from local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

// Extract BASE_URL and KEY from local.properties
val baseUrl = System.getenv("BASE_URL")
    ?: localProperties.getProperty("BASE_URL") ?: "https://default-url.com"
val key = System.getenv("KEY")
    ?: localProperties.getProperty("KEY") ?: "default_key"

// Generate BuildConfig file for the library
tasks.register("generateBuildConfig") {
    val outputDir = layout.buildDirectory.dir("generated/sources/buildConfig")
    val buildConfigFile = outputDir.get().file("BuildConfig.kt")

    inputs.property("baseUrl", baseUrl)
    inputs.property("key", key)
    outputs.file(buildConfigFile)

    doLast {
        val content = """
            package com.dzaky.data

            object BuildConfig {
                const val BASE_URL: String = "$baseUrl"
                const val KEY: String = "$key"
            }
        """.trimIndent()

        buildConfigFile.asFile.apply {
            parentFile.mkdirs()
            writeText(content)
        }
    }
}

// Include the generated BuildConfig in the source set
sourceSets {
    main {
        java.srcDirs("build/generated/sources/buildConfig")
    }
}

// Ensure generateBuildConfig runs before compileKotlin
tasks.named("compileKotlin") {
    dependsOn("generateBuildConfig")
}

tasks.withType<Test> {
    useJUnit()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}