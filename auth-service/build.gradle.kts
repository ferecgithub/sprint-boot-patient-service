plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.depencendy.management)
}

group = "com.pm"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.kotlin.reflect)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jjwt.api)
    implementation(libs.jjwt.impl)
    implementation(libs.jjwt.jackson)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.h2.database)
    runtimeOnly(libs.postgresql.jdbc.driver)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
