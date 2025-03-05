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
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines)
        }
    }
}
