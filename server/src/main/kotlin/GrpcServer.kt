package dev.matvenoid

import dev.matvenoid.core.DatabaseFactory
import dev.matvenoid.services.property.PropertyService
import dev.matvenoid.services.reservation.ReservationService
import dev.matvenoid.services.review.ReviewService
import dev.matvenoid.services.user.UserService
import io.grpc.ServerBuilder
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("GrpcServer")

    DatabaseFactory.init()

    val port = 8001

    val server = ServerBuilder
        .forPort(port)
        .addService(UserService(logger))
        .addService(PropertyService(logger))
        .addService(ReservationService(logger))
        .addService(ReviewService(logger))
        .build()
        .start()

    logger.info("Server started, listening on $port")

    Runtime.getRuntime().addShutdownHook(Thread {
        logger.info("Shutting down server...")
        server.shutdown()
        logger.info("Server shut down")
    })

    server.awaitTermination()
}