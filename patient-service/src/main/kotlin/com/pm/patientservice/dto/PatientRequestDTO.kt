package com.pm.patientservice.dto

import com.pm.patientservice.dto.validator.CreatePatientValidationGroup
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class PatientRequestDTO(
    @field:NotBlank("Name is required")
    @field:Size(max = 100, message = "Name must be less than 100 characters")
    val name: String? = null,

    @field:NotBlank("Email is required")
    @field:Email(message = "Invalid email format")
    val email: String? = null,

    @field:NotBlank("Address is required")
    val address: String? = null,

    @field:NotBlank("Date of birth is required")
    val dateOfBirth: String? = null,

    @field:NotBlank(groups = [CreatePatientValidationGroup::class], message = "Registered date is required")
    val registeredDate: String? = null
)
