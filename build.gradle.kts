
buildscript {
    extra["kotlin_version"] = "1.7.10"
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
