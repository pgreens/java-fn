plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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

tasks {
  javadoc {
    options {
      tags = listOf("implSpec:a:Implementation Requirements:")
    }
  }
//  named<Javadoc>("javadoc") {
//    options.tags = listOf("implSpec:a:Implementation Requirements:")
//    named<StandardJavadocDocletOptions>("options") {
//    }
//  }
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
