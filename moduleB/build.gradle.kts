@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("com.poisonedyouth.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies{
    implementation(libs.ktorCore)
    implementation(libs.ktorAuth)
    implementation(libs.ktorNetty)
    testImplementation(libs.ktorTest)
}