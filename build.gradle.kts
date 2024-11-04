plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.kmp) apply false
}

allprojects {

    group = "com.trup10ka.attendoo"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}
