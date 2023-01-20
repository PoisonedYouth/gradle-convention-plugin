plugins {
    `kotlin-dsl`
}

repositories{
    mavenCentral()
    gradlePluginPortal()
}

dependencies{
    compileOnly(libs.kotlinJvmLib)
    implementation(libs.owaspDependencyLib)
    implementation(libs.ktorPluginLib)
}