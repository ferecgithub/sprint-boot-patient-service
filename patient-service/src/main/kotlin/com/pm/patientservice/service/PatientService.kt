package com.pm.patientservice.service

import com.pm.patientservice.dto.PatientRequestDTO
import com.pm.patientservice.dto.PatientResponseDTO
import com.pm.patientservice.exception.EmailAlreadyExistsException
import com.pm.patientservice.exception.PatientNotFoundException
import com.pm.patientservice.grpc.BillingServiceGrpcClient
import com.pm.patientservice.kafka.KafkaProducer
import com.pm.patientservice.mapper.toPatient
import com.pm.patientservice.mapper.toPatientResponseDto
import com.pm.patientservice.repository.PatientRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import java.time.LocalDate
import java.util.UUID

@Service
class PatientService(
    private val patientRepository: PatientRepository,
    private val billingServiceGrpcClient: BillingServiceGrpcClient,
    private val kafkaProducer: KafkaProducer
) {

    fun getPatients(): List<PatientResponseDTO> {
        return patientRepository.findAll().map { it.toPatientResponseDto() }
    }

    fun createPatient(patientRequestDTO: PatientRequestDTO): PatientResponseDTO {
        if (patientRequestDTO.email?.let { patientRepository.existsByEmail(it) } == true) {
            throw EmailAlreadyExistsException("A patient with this email already exists ${patientRequestDTO.email}")
        }
        val newPatient = patientRepository.save(patientRequestDTO.toPatient())

        billingServiceGrpcClient.createBillingAccount(
            patientId = newPatient.id.toString(),
            name = newPatient.name.toString(),
            email = newPatient.email.toString()
        )

        kafkaProducer.sendEvent(newPatient)

        return newPatient.toPatientResponseDto()
    }

    fun updatePatient(
        id: UUID,
        patientRequestDTO: PatientRequestDTO
    ): PatientResponseDTO {
        val patient = patientRepository.findById(id).orElseThrow {
            PatientNotFoundException("Patient with id $id not found")
        }
        if (patientRequestDTO.email?.let { patientRepository.existsByEmailAndIdNot(it, id) } == true) {
            throw EmailAlreadyExistsException("A patient with this email already exists ${patientRequestDTO.email}")
        }

        val updatedPatient = patient.copy(
            name = patientRequestDTO.name,
            address = patientRequestDTO.address,
            email = patientRequestDTO.email,
            dateOfBirth = patientRequestDTO.dateOfBirth?.let { LocalDate.parse(it) }
        )
        return patientRepository.save(updatedPatient).toPatientResponseDto()
    }

    fun deletePatient(id: UUID) {
        if (patientRepository.existsById(id).not()) {
            throw PatientNotFoundException("Patient with id $id not found")
        }
        patientRepository.deleteById(id)
    }
}