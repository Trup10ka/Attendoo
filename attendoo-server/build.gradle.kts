plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor.plugin)
}

dependencies {
    /* ## Ktor Core ## */
    implementation(libs.ktor.core)
    implementation(libs.ktor.netty)

    /* ## Ktor Features ## */
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.host.common)
    implementation(libs.ktor.status.pages)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.compression)
    implementation(libs.ktor.cors)
    implementation(libs.ktor.default.headers)
    implementation(libs.ktor.http.redirect)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.websockets)

    /* ### Config - Hoplite ### */
    implementation(libs.hoplite.core)
    implementation(libs.hoplite.hocon)

    /* ## Exposed ## */
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)

    /* ## DB ## */
    implementation(libs.h2db)

    /* ## Logging ## */
    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging)

    /* ## Testing ## */
    testImplementation(libs.ktor.test)
    testImplementation(libs.kotlin.test.junit)
}
