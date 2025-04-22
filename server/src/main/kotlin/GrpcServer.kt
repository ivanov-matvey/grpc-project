package dev.matvenoid

import dev.matvenoid.core.DatabaseFactory
import dev.matvenoid.services.BookingService
import io.grpc.ServerBuilder
import org.slf4j.LoggerFactory

fun main() {
    val log = LoggerFactory.getLogger("GrpcServer")

    DatabaseFactory.init()

    val port = 8001

    val server = ServerBuilder
        .forPort(port)
        .addService(BookingService())
        .build()
        .start()

    log.info("Server started, listening on $port")

    Runtime.getRuntime().addShutdownHook(Thread {
        log.info("Shutting down server...")
        server.shutdown()
        log.info("Server shut down")
    })

    server.awaitTermination()
}