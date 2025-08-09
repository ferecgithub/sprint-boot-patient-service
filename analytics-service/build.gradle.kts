import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.depencendy.management)
    alias(libs.plugins.protobuf)
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

val protocArtifact = "${libs.protobuf.protoc.get().module}:${libs.versions.protoc.version.get()}"
val grpcPluginArtifact = "${libs.protoc.gen.grpc.java.get().module}:${libs.versions.grpc.codegen.version.get()}"

protobuf {
    protoc {
        artifact = protocArtifact
    }
    plugins {
        id("grpc") {
            artifact = grpcPluginArtifact
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.grpc.netty.shaded)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.stub)
    implementation(libs.protobuf.java)
    implementation(libs.grpc.spring.boot.starter)
    implementation(libs.spring.kafka)
    compileOnly(libs.annotations.api)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain(21)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}