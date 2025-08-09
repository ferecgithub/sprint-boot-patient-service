package com.pm.patientservice.grpc

import billing.BillingRequest
import billing.BillingResponse
import billing.BillingServiceGrpc
import com.pm.patientservice.grpc.config.BillingServiceProperties
import io.grpc.ManagedChannelBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BillingServiceGrpcClient(
    props: BillingServiceProperties
) {
    private val blockingStub: BillingServiceGrpc.BillingServiceBlockingStub

    companion object {
        private val log: Logger = LoggerFactory.getLogger(BillingServiceGrpcClient::class.java)
    }

    init {
        val serverAddress = props.address
        val serverPort = props.grpc.port

        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort)

        val channel = ManagedChannelBuilder
            .forAddress(serverAddress, serverPort)
            .usePlaintext()
            .build()

        blockingStub = BillingServiceGrpc.newBlockingStub(channel)
    }


    fun createBillingAccount(
        patientId: String,
        name: String,
        email: String
    ): BillingResponse {
        val request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email).build()
        val response = blockingStub.createBillingAccount(request)
        log.info("Response from Billing Service via GRPC: {}", response)
        return response
    }
}
