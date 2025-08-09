package com.pm.patientservice.mapper

import com.pm.patientservice.dto.PatientRequestDTO
import com.pm.patientservice.dto.PatientResponseDTO
import com.pm.patientservice.model.Patient
import java.time.LocalDate

fun Patient.toPatientResponseDto(): PatientResponseDTO {
    return PatientResponseDTO(
        id = id.toString(),
        name = name,
        email = email,
        address = address,
        dateOfBirth = dateOfBirth.toString()
    )
}

fun PatientRequestDTO.toPatient(): Patient {
    return Patient(
        name = name,
        email = email,
        address = address,
        dateOfBirth = dateOfBirth?.let { LocalDate.parse(it) },
        registeredDate = registeredDate?.let { LocalDate.parse(it) },
    )
}