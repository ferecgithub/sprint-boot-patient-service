package com.pm.patientservice.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(exception: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>?> {
        val errors = exception.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(exception: EmailAlreadyExistsException): ResponseEntity<Map<String, String?>?> {
        log.warn(
            "Email already exists {}", exception.message
        )
        val errors = mapOf("email" to exception.message)
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(PatientNotFoundException::class)
    fun handlePatientNotFoundException(exception: PatientNotFoundException): ResponseEntity<Map<String, String?>?> {
        log.warn(
            "Patient not found {}", exception.message
        )
        val errors = mapOf("message" to "Patient not found")
        return ResponseEntity.badRequest().body(errors)
    }
}