package com.pm.analyticsservice.kafka

import org.apache.kafka.shaded.com.google.protobuf.InvalidProtocolBufferException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import patient.events.PatientEvent

@Service
class KafkaConsumer {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    @KafkaListener(topics = ["patient"], groupId = "analytics-service")
    fun consumeEvent(event: ByteArray) {
        try {
            val patientEvent = PatientEvent.parseFrom(event)
            // Perform any business related to analytics here

            log.info("Received Patient Event: $patientEvent")
        } catch (e: InvalidProtocolBufferException) {
            log.error("Error deserializing event: ${e.message}")
        }

    }
}