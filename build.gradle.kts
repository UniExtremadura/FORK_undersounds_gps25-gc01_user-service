plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.sonarqube") version "7.1.0.6387"
}
 

sonar {
  properties {
    property("sonar.projectKey", "UniExtremadura_FORK_undersounds_gps25-gc01_user-service")
    property("sonar.organization", "uniextremadura")
  }
}

val springdocVersion by extra("2.8.13")
val springCloudVersion by extra("2025.0.0")
val commonsIoVersion by extra("2.14.0")

group = "es.undersounds.gc01"
version = "0.0.1-SNAPSHOT"
description = "UnderSounds - Users Service API"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // ----------------------------------------------------
    // 1. DEPENDENCIAS DE IMPLEMENTACIÓN (COMPILACIÓN Y TIEMPO DE EJECUCIÓN)
    // ----------------------------------------------------
    
    // Spring Boot Starters (Funcionalidad principal)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    
    // Spring Cloud (Descubrimiento de servicios)
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    
    // Others (Kotlin, Documentación, Utilidades)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.slf4j:slf4j-api")
    implementation("commons-io:commons-io:$commonsIoVersion")

    // ----------------------------------------------------
    // 2. DEPENDENCIAS DE TIEMPO DE EJECUCIÓN (runtimeOnly)
    // ----------------------------------------------------

    // Database Driver
    runtimeOnly("com.mysql:mysql-connector-j")
    
    // Logging Implementation
    runtimeOnly("ch.qos.logback:logback-classic") 

    // ----------------------------------------------------
    // 3. DEPENDENCIAS DE DESARROLLO (developmentOnly)
    // ----------------------------------------------------
    
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    // ----------------------------------------------------
    // 4. DEPENDENCIAS DE PRUEBAS (Testing)
    // ----------------------------------------------------

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
