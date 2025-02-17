plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kmp)
}

kotlin {

    jvm()

    js(IR) {
        browser()
        binaries.executable()
    }
}
