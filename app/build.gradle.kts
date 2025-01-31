import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	application
	id("checkstyle")
	jacoco
	id("io.freefair.lombok") version "8.6"
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.github.ben-manes.versions") version "0.51.0"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

application { mainClass.set("hexlet.code.AppApplication") }


repositories {
	mavenCentral()
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// implementation("io.github.wimdeblauwe:error-handling-spring-boot-starter:4.2.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	implementation("org.instancio:instancio-junit:3.3.0")
	implementation("org.apache.commons:commons-lang3:3.13.0")
	implementation("org.apache.commons:commons-text:1.10.0")
	implementation("net.datafaker:datafaker:2.0.1")

	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	runtimeOnly("com.h2database:h2")
	// runtimeOnly("org.postgresql:postgresql:42.6.0")
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		exceptionFormat = TestExceptionFormat.FULL
		events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
		showStandardStreams = true
	}
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
	}
}
