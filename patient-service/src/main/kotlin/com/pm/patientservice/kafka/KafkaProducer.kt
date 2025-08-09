package com.pm.patientservice.kafka

import com.pm.patientservice.exception.GlobalExceptionHandler
import com.pm.patientservice.model.Patient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import patient.events.PatientEvent

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, ByteArray>
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)
    }

    fun sendEvent(patient: Patient) {
        val event = PatientEvent.newBuilder()
            .setPatientId(patient.id.toString())
            .setName(patient.name)
            .setEmail(patient.email)
            .setEventType("PATIENT_CREATED")
            .build()

        try {
            kafkaTemplate.send("patient", event.toByteArray())
        } catch (e: Exception) {
            log.error("Error sending PatientCreated event: {}", event)
        }
    }
}