package com.pm.billingservice.grpc

import billing.BillingRequest
import billing.BillingResponse
import billing.BillingServiceGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@GrpcService
class BillingGrpcService : BillingServiceGrpc.BillingServiceImplBase() {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(BillingGrpcService::class.java)
    }

    @Override
    override fun createBillingAccount(
        billingRequest: BillingRequest,
        responseObserver: StreamObserver<BillingResponse>
    ) {
        log.info("createBillingAccount request received {}", billingRequest.toString())

        // Business logic - e.g save to database, perform calculates etc.

        val response = BillingResponse.newBuilder()
            .setAccountId("12345")
            .setStatus("ACTIVE")
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}