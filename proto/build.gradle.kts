import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.protobuf)
}

group = "dev.matvenoid"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlin.stub)
    implementation(libs.protobuf.java)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.30.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.72.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}
