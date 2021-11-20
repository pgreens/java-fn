/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.1.1/userguide/building_java_projects.html
 */

plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

java {
    withSourcesJar()
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencyLocking {
    lockAllConfigurations()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:latest.release")
    testImplementation("org.hamcrest:hamcrest:latest.release")
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
