package dev.matvenoid.services.property

import dev.matvenoid.proto.GetPropertiesRequest
import dev.matvenoid.proto.GetPropertiesResponse
import dev.matvenoid.proto.Property
import dev.matvenoid.proto.PropertyServiceGrpc
import dev.matvenoid.repositories.PropertyRepository
import io.grpc.stub.StreamObserver
import org.slf4j.Logger

class PropertyService(val logger: Logger) : PropertyServiceGrpc.PropertyServiceImplBase() {
    private val propertyRepository = PropertyRepository()

    override fun getProperties(
        request: GetPropertiesRequest,
        responseObserver: StreamObserver<GetPropertiesResponse>
    ) {
        try {
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

            val response = GetPropertiesResponse.newBuilder()
                .addAllProperty(propertyMessages)
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }
}
