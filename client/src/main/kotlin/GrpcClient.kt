package dev.matvenoid

import dev.matvenoid.proto.BookingServiceGrpcKt
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

    val request = GetUsersRequest.newBuilder().build()
    try {
        val response = stub.getUsers(request)

        println("Users list:")
        response.usersList.forEach { user ->
            println("id: ${user.id}, name: ${user.name}, phone: +7${user.phone}, birthday: ${user.birthday}")
        }
    } catch (e: Exception) {
        log.error(e.message)
    }

    channel.shutdown()
    log.info("channel is closed.")
}