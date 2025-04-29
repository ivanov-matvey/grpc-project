package dev.matvenoid.services.review

import dev.matvenoid.proto.GetReviewsRequest
import dev.matvenoid.proto.GetReviewsResponse
import dev.matvenoid.proto.Review
import dev.matvenoid.proto.ReviewServiceGrpc
import dev.matvenoid.repositories.ReviewRepository
import io.grpc.stub.StreamObserver
import org.slf4j.Logger

class ReviewService(val logger: Logger) : ReviewServiceGrpc.ReviewServiceImplBase() {
    private val reviewRepository = ReviewRepository()

    override fun getReviews(
        request: GetReviewsRequest,
        responseObserver: StreamObserver<GetReviewsResponse>
    ) {
        try {
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

            val response = GetReviewsResponse.newBuilder()
                .addAllReview(reviewMessages)
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }
}
