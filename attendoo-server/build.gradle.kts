plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor.plugin)
    application
}

dependencies {

    /* Shared code */
    implementation(projects.attendooShared)

    /* ## Ktor Core ## */
    implementation(libs.ktor.core)
    implementation(libs.ktor.netty)

    /* ## Ktor Features ## */
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.host.common)
    implementation(libs.ktor.status.pages)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.auth.jwt)
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
    implementation(libs.exposed.dao)
    implementation(libs.exposed.crypt)
    implementation(libs.exposed.java.time)

    /* ## DB ## */
    implementation(libs.h2db)
    implementation(libs.mariadb.driver)
    implementation(libs.mysql.driver)

    /* ## Logging ## */
    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging)

    /* ## Testing ## */
    testImplementation(libs.ktor.test)
    testImplementation(libs.kotlin.test.junit)
}

ktor {
    fatJar {
        archiveFileName.set("attendoo-${version}.jar")
    }
}

application {
    mainClass.set("com.trup10ka.attendoo.MainKt")
}
