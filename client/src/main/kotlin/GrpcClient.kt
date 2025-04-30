package dev.matvenoid

import dev.matvenoid.proto.GetPropertiesRequest
import dev.matvenoid.proto.GetReservationsRequest
import dev.matvenoid.proto.GetReviewsRequest
import dev.matvenoid.proto.Empty
import dev.matvenoid.proto.PropertyServiceGrpc
import dev.matvenoid.proto.ReservationServiceGrpc
import dev.matvenoid.proto.ReviewServiceGrpc
import dev.matvenoid.proto.UserIdRequest
import dev.matvenoid.proto.UserRequest
import dev.matvenoid.proto.UserServiceGrpc
import io.grpc.ManagedChannelBuilder
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("GrpcClient")

    val channel = ManagedChannelBuilder
        .forAddress("localhost", 8001)
        .usePlaintext()
        .build()
    logger.info("Channel created.")

    val userStub = UserServiceGrpc.newBlockingStub(channel)
    val propertyStub = PropertyServiceGrpc.newBlockingStub(channel)
    val reservationStub = ReservationServiceGrpc.newBlockingStub(channel)
    val reviewStub = ReviewServiceGrpc.newBlockingStub(channel)

    try {
        val userRequest = Empty.newBuilder().build()
        val userResponse = userStub.listUsers(userRequest)
        println("Users list:")
        userResponse.usersList.forEach { user ->
            println("id: ${user.id}," +
                    " name: ${user.name}," +
                    " phone: +7${user.phone}," +
                    " birthday: ${user.birthday}"
            )
        }
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val userRequest = UserRequest.newBuilder()
            .setName("Тест")
            .setPhone("9991112233")
            .setBirthday("2000-12-12")
            .build()

        val userResponse = userStub.createUser(userRequest)
        print("User created: ")
        println("id: ${userResponse.user.id}," +
                " name: ${userResponse.user.name}," +
                " phone: +7${userResponse.user.phone}," +
                " birthday: ${userResponse.user.birthday}"
        )
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val userRequest = UserRequest.newBuilder()
            .setId(50)
            .setName("Тест2")
            .setPhone("9991112233")
            .setBirthday("2000-12-12")
            .build()

        val userResponse = userStub.updateUser(userRequest)
        print("User updated: ")
        println("id: ${userResponse.user.id}," +
                " name: ${userResponse.user.name}," +
                " phone: +7${userResponse.user.phone}," +
                " birthday: ${userResponse.user.birthday}"
        )
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val userRequest = UserIdRequest.newBuilder()
            .setId(2)
            .build()

        val userResponse = userStub.getUser(userRequest)
        print("User: ")
        println("id: ${userResponse.user.id}," +
                " name: ${userResponse.user.name}," +
                " phone: +7${userResponse.user.phone}," +
                " birthday: ${userResponse.user.birthday}"
        )
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val userRequest = UserIdRequest.newBuilder()
            .setId(50)
            .build()

        userStub.deleteUser(userRequest)

        println("User deleted")
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val propertyRequest = GetPropertiesRequest.newBuilder().build()
        val propertyResponse = propertyStub.getProperties(propertyRequest)
        println("Properties list:")
        propertyResponse.propertyList.forEach { property ->
            println("id: ${property.id}," +
                    " address: \"${property.address}\"," +
                    " price: ${property.price}," +
                    " description: ${property.description}," +
                    " is_available: ${property.isAvailable}"
            )
        }
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val reservationRequest = GetReservationsRequest.newBuilder().build()
        val reservationResponse = reservationStub.getReservations(reservationRequest)
        println("Reservations list:")
        reservationResponse.reservationList.forEach { reservation ->
            println("id: ${reservation.id}," +
                    " start_date: ${reservation.startDate}," +
                    " end_date: ${reservation.endDate}," +
                    " user_id: ${reservation.userId}," +
                    " property_id: ${reservation.propertyId}"
            )
        }
    } catch (e: Exception) {
        logger.error(e.message)
    }

    try {
        val reviewRequest = GetReviewsRequest.newBuilder().build()
        val reviewResponse = reviewStub.getReviews(reviewRequest)
        println("Reviews list:")
        reviewResponse.reviewList.forEach { review ->
            println("id: ${review.id}," +
                    " rating: ${review.rating}," +
                    " comment: ${review.comment}," +
                    " created_at: ${review.createdAt}," +
                    " updated_at: ${review.updatedAt}," +
                    " user_id: ${review.userId}," +
                    " property_id: ${review.propertyId}"
            )
        }
    } catch (e: Exception) {
        logger.error(e.message)
    }

    channel.shutdown()
    logger.info("Channel is closed.")
}
