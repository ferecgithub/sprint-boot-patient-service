package com.pm.patientservice.controller

import com.pm.patientservice.dto.PatientRequestDTO
import com.pm.patientservice.dto.PatientResponseDTO
import com.pm.patientservice.dto.validator.CreatePatientValidationGroup
import com.pm.patientservice.service.PatientService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.groups.Default
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing patients")
class PatientController(
    private val patientService: PatientService
) {

    @GetMapping
    @Operation(summary = "Get patients")
    fun getAllPatients(): ResponseEntity<List<PatientResponseDTO>> {
        return ResponseEntity.ok().body(patientService.getPatients())
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    fun createPatient(
        @Validated(
            Default::class,
            CreatePatientValidationGroup::class
        ) @RequestBody patientRequestDTO: PatientRequestDTO
    ): ResponseEntity<PatientResponseDTO> {
        return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    fun updatePatient(
        @PathVariable id: UUID,
        @Validated(Default::class) @RequestBody patientRequestDTO: PatientRequestDTO
    ): ResponseEntity<PatientResponseDTO> {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    fun deletePatient(@PathVariable id: UUID): ResponseEntity<Unit> {
        patientService.deletePatient(id)
        return ResponseEntity.ok().build()
    }
}