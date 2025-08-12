package com.pm.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
