package com.pm.billingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BillingServiceApplication

fun main(args: Array<String>) {
    runApplication<BillingServiceApplication>(*args)
}
