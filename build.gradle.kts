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
        ":kotlinUpgradeYarnLock",
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


tasks.register<Copy>("buildPublicDist") {
    dependsOn(
        ":kotlinUpgradeYarnLock",
        ":attendoo-client:jsBrowserDistribution",
        ":attendoo-server:shadowJar"
    )

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    project(":attendoo-client")
    
    from(project(":attendoo-client").layout.buildDirectory.dir("dist/js/productionExecutable")) {
        include("index.html")
        into("resources")
    }
    
    from(project(":attendoo-client").layout.buildDirectory.dir("dist/js/productionExecutable")) {
        include("attendoo-client.js")
        into("resources/public")
    }
    
    from(project(":attendoo-client").layout.buildDirectory.dir("dist/js/productionExecutable/public")) {
        into("resources/public")
    }
    
    val versionRegex = Regex("""attendoo-\d+\.\d+\.\d+\.jar""")
    from(project(":attendoo-server").layout.buildDirectory.dir("libs")) {
        include("attendoo-*.jar")
        eachFile {
            if (!versionRegex.matches(name)) {
                exclude()
            }
        }
    }

    into(rootProject.layout.projectDirectory.dir("dist"))
}
