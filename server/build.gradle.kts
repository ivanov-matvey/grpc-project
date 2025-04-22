plugins {
    alias(libs.plugins.kotlin)
}

group = "dev.matvenoid"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(project(":proto"))

    implementation(libs.logback.classic)

    implementation(libs.grpc.netty)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlin.stub)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgresql)
}