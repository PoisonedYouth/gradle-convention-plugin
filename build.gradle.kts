@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.owaspDependencyCheck)
}

group = "com.poisonedyouth"
version = "0.0.1"

val owaspDependencyCheckId = libs.plugins.owaspDependencyCheck.get().pluginId

allprojects{
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


subprojects {
    apply{
        plugin(owaspDependencyCheckId)
    }

    dependencyCheck {
        autoUpdate=false
        cveValidForHours=1
        format=  org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL
    }


    tasks.withType<Test> {
        testLogging {
            events(
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
            )
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
        useJUnitPlatform()

        val failedTests = mutableListOf<Pair<TestDescriptor, StackTraceElement?>>()

        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {
                // Nothing to do
            }

            override fun beforeTest(testDescriptor: TestDescriptor) {
                // Nothing to do
            }

            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                when (result.resultType) {
                    TestResult.ResultType.FAILURE -> {
                        val relevantStackTrace =
                            result.exception?.stackTrace?.last { it.className == testDescriptor.className }
                        failedTests.add(Pair(testDescriptor, relevantStackTrace))
                    }

                    else -> {}
                }
            }

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    if (failedTests.isNotEmpty()) {
                        logger.lifecycle("\tFailed Tests:")
                        failedTests.forEach {
                            val (testDescriptor, relevantStackTrace) = it
                            val file = testDescriptor.className?.split(".")?.last()
                            val fqcn = testDescriptor.className
                            val testName = testDescriptor.name

                            val intelliJFormatWithFileLink =
                                if (relevantStackTrace != null) "at $relevantStackTrace" else "at $fqcn.$testName($file)"

                            parent?.let { parent ->
                                logger.lifecycle("\t\t${parent.name} - $intelliJFormatWithFileLink")
                            } ?: logger.lifecycle("\t\t$intelliJFormatWithFileLink")
                        }
                    }
                }
            }
        })
    }
}