plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    /* ## Logging ## */
    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging)

    /* ## Testing ## */
    testImplementation(libs.ktor.test)
    testImplementation(libs.kotlin.test.junit)
}
