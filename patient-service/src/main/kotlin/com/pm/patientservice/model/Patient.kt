package com.pm.patientservice.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.util.*

@Entity
data class Patient(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @field:NotNull
    var name: String? = null,

    @field:NotNull
    @field:Email
    @Column(unique = true)
    var email: String? = null,

    @field:NotNull
    var address: String? = null,

    @field:NotNull
    var dateOfBirth: LocalDate? = null,

    @field:NotNull
    var registeredDate: LocalDate? = null,
)
