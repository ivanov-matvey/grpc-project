package dev.matvenoid.services

import dev.matvenoid.models.properties.PropertyRepository
import dev.matvenoid.models.reservations.ReservationRepository
import dev.matvenoid.models.reviews.ReviewRepository
import dev.matvenoid.models.users.UserRepository
import dev.matvenoid.proto.BookingServiceGrpcKt
import dev.matvenoid.proto.GetPropertiesRequest
import dev.matvenoid.proto.GetPropertiesResponse
import dev.matvenoid.proto.GetReservationsRequest
import dev.matvenoid.proto.GetReservationsResponse
import dev.matvenoid.proto.GetReviewsRequest
import dev.matvenoid.proto.GetReviewsResponse
import dev.matvenoid.proto.GetUsersRequest
import dev.matvenoid.proto.GetUsersResponse
import dev.matvenoid.proto.Property
import dev.matvenoid.proto.Reservation
import dev.matvenoid.proto.Review
import dev.matvenoid.proto.User

class BookingService: BookingServiceGrpcKt.BookingServiceCoroutineImplBase() {
    val userRepository = UserRepository()
    val propertyRepository = PropertyRepository()
    val reservationRepository = ReservationRepository()
    val reviewRepository = ReviewRepository()

    override suspend fun getUsers(request: GetUsersRequest): GetUsersResponse {
        val users = userRepository.getAll()

        val userMessages = users.map { user ->
            User.newBuilder()
                .setId(user.id)
                .setName(user.name)
                .setPhone(user.phone)
                .setBirthday(user.birthday.toString())
                .build()
        }

        return GetUsersResponse.newBuilder()
            .addAllUsers(userMessages)
            .build()
    }

    override suspend fun getProperties(request: GetPropertiesRequest): GetPropertiesResponse {
        val properties = propertyRepository.getAll()

        val propertyMessages = properties.map { property ->
            Property.newBuilder()
                .setId(property.id)
                .setAddress(property.address)
                .setPrice(property.price)
                .setDescription(property.description)
                .setIsAvailable(property.isAvailable)
                .build()
        }

        return GetPropertiesResponse.newBuilder()
            .addAllProperty(propertyMessages)
            .build()
    }

    override suspend fun getReservations(request: GetReservationsRequest): GetReservationsResponse {
        val reservations = reservationRepository.getAll()

        val reservationMessages = reservations.map { reservation ->
            Reservation.newBuilder()
                .setId(reservation.id)
                .setStartDate(reservation.startDate.toString())
                .setEndDate(reservation.endDate.toString())
                .setUserId(reservation.userId)
                .setPropertyId(reservation.propertyId)
                .build()
        }

        return GetReservationsResponse.newBuilder()
            .addAllReservation(reservationMessages)
            .build()
    }

    override suspend fun getReviews(request: GetReviewsRequest): GetReviewsResponse {
        val reviews = reviewRepository.getAll()

        val reviewMessages = reviews.map { review ->
            Review.newBuilder()
                .setId(review.id)
                .setRating(review.rating)
                .setComment(review.comment)
                .setCreatedAt(review.createdAt.toString())
                .setUpdatedAt(review.updatedAt.toString())
                .setUserId(review.userId)
                .setPropertyId(review.propertyId)
                .build()
        }

        return GetReviewsResponse.newBuilder()
            .addAllReview(reviewMessages)
            .build()
    }
}
