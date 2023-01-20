@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinJvm)
    id("test-configuration")
}

group = "com.poisonedyouth"
version = "0.0.1"


allprojects{
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


subprojects {
    apply{
        plugin("test-configuration")
    }
}