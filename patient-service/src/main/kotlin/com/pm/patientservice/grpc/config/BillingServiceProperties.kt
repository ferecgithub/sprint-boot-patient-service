package com.pm.patientservice.grpc.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "billing.service")
data class BillingServiceProperties(
    val address: String = "localhost",
    val grpc: Grpc = Grpc()
) {
    data class Grpc(var port: Int = 9001)
}
