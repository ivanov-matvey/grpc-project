package dev.matvenoid

import dev.matvenoid.proto.BookingServiceGrpcKt
import dev.matvenoid.proto.GetPropertiesRequest
import dev.matvenoid.proto.GetReservationsRequest
import dev.matvenoid.proto.GetReviewsRequest
import dev.matvenoid.proto.GetUsersRequest
import io.grpc.ManagedChannelBuilder
import org.slf4j.LoggerFactory
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val log = LoggerFactory.getLogger("GrpcClient")

    val channel = ManagedChannelBuilder
        .forAddress("localhost", 8001)
        .usePlaintext()
        .build()
    log.info("Channel created.")

    val stub = BookingServiceGrpcKt.BookingServiceCoroutineStub(channel)

    val userRequest = GetUsersRequest.newBuilder().build()
    try {
        val response = stub.getUsers(userRequest)

        println("Users list:")
        response.usersList.forEach { user ->
            println("id: ${user.id}," +
                    " name: ${user.name}," +
                    " phone: +7${user.phone}," +
                    " birthday: ${user.birthday}"
            )
        }
    } catch (e: Exception) {
        log.error(e.message, e)
    }

    val propertyRequest = GetPropertiesRequest.newBuilder().build()
    try {
        val response = stub.getProperties(propertyRequest)

        println("Properties list:")
        response.propertyList.forEach { property ->
            println("id: ${property.id}," +
                    " address: \"${property.address}\"," +
                    " price: ${property.price}," +
                    " description: ${property.description}," +
                    " is_available: ${property.isAvailable}"
            )
        }
    } catch (e: Exception) {
        log.error(e.message, e)
    }

    val reservationRequest = GetReservationsRequest.newBuilder().build()
    try {
        val response = stub.getReservations(reservationRequest)

        println("Reservations list:")
        response.reservationList.forEach { reservation ->
            println("id: ${reservation.id}," +
                    " start_date: ${reservation.startDate}," +
                    " end_date: ${reservation.endDate}," +
                    " user_id: ${reservation.userId}," +
                    " property_id: ${reservation.propertyId}"
            )
        }
    } catch (e: Exception) {
        log.error(e.message, e)
    }

    val reviewRequest = GetReviewsRequest.newBuilder().build()
    try {
        val response = stub.getReviews(reviewRequest)

        println("Reviews list:")
        response.reviewList.forEach { review ->
            println("id: ${review.id}," +
                    " rating: ${review.rating}," +
                    " comment: ${review.comment}," +
                    " created_at: ${review.createdAt}," +
                    " updated_at: ${review.updatedAt}," +
                    " user_id: ${review.userId}, " +
                    " property_id: ${review.propertyId}"
            )
        }
    } catch (e: Exception) {
        log.error(e.message, e)
    }

    channel.shutdown()
    log.info("channel is closed.")
}