plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor.plugin)
    alias(libs.plugins.kmp)
}

kotlin {
    js(IR) {
        browser {
            webpackTask {
                mainOutputFileName = "attendoo-client.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(projects.attendooShared)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.js)
            implementation(libs.kotlinx.json.client)
        }
    }
}
