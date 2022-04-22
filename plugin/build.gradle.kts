@file:Suppress("UnstableApiUsage")

plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.18.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

group = "com.nocwriter.gradle.props"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:unchecked")
    options.isDeprecation = true
}


testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.8.1")
        }

        // Create a new test suite
        @Suppress("UNUSED_VARIABLE")
        val functionalTest by registering(JvmTestSuite::class) {
            dependencies {
                // functionalTest test suite depends on the production code in tests
                implementation(project)
                implementation("commons-io:commons-io:2.11.0")
            }

            targets {
                all {
                    // This test suite should run after the built-in test suite has run its tests
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin {
    // Define the plugin
    @Suppress("UNUSED_VARIABLE")
    val propsPlugin by plugins.creating {
        id = "com.nocwriter.gradle.props"
        implementationClass = "com.nocwriter.gradle.props.GradlePropsPlugin"
        displayName = "Gradle properties plugin"
        description =
            "A Gradle plugin that loads external properties file into project extra properties and system properties sets."
    }
}
gradlePlugin.testSourceSets(sourceSets["functionalTest"])

pluginBundle {
    website = "https://github.com/NocWriter/gradle-props-plugin"
    vcsUrl = "https://github.com/NocWriter/gradle-props-plugin"
    tags = listOf("props", "properties")
}

tasks.named<Task>("check") {
    // Include functionalTest as part of the check lifecycle
    dependsOn(testing.suites.named("functionalTest"))
}



