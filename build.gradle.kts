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

tasks.register<Copy>("buildTestPackage") {
    dependsOn(
        ":attendoo-client:jsBrowserDevelopmentExecutableDistribution",
        ":attendoo-server:shadowJar"
    )
    
    project(":attendoo-client")
    
    from(project(":attendoo-client").layout.buildDirectory.dir("dist/js/developmentExecutable"))
    
    from(project(":attendoo-server").layout.buildDirectory.dir("libs")) {
        include("*.jar")
    }
    
    into(rootProject.layout.buildDirectory.dir("output"))
}
