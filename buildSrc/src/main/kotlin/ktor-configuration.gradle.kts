import org.gradle.kotlin.dsl.dependencies

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("io.ktor.plugin")
    id("owasp-configuration")
}

application {
    mainClass.set("com.poisonedyouth.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies{
    implementation("io.ktor:ktor-server-core-jvm:2.2.1")
    implementation("io.ktor:ktor-server-auth-jvm:2.2.1")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.2.1")
}