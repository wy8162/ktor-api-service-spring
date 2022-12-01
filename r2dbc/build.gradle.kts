val junit_version: String by project
val assertj_version: String by project
val mockk_version: String by project

plugins {
    jacoco
    application
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") apply true
    id("org.owasp.dependencycheck") apply true
    id("org.jlleitschuh.gradle.ktlint") apply true
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.assertj:assertj-core:$assertj_version")
    testImplementation("io.mockk:mockk:$mockk_version")
}
