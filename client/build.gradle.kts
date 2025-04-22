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
    implementation(project(":server"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.logback.classic)

    implementation(libs.grpc.netty)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlin.stub)
}