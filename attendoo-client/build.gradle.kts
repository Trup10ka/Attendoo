plugins {
    alias(libs.plugins.kotlinx.serialization)
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
            implementation(projects.shared)
        }
    }
}
