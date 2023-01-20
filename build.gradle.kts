@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.owaspDependencyCheck)
    id("test-configuration")
}

group = "com.poisonedyouth"
version = "0.0.1"

val owaspDependencyCheckId:String = libs.plugins.owaspDependencyCheck.get().pluginId

allprojects{
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


subprojects {
    apply{
        plugin(owaspDependencyCheckId)
        plugin("test-configuration")
    }

    dependencyCheck {
        autoUpdate=false
        cveValidForHours=1
        format=  org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL
    }
}