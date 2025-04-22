dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "gRPCKotlin"

include("server")
include("client")
include("proto")