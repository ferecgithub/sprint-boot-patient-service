package com.pm.authservice.controller

import com.pm.authservice.dto.LoginRequestDTO
import com.pm.authservice.dto.LoginResponseDTO
import com.pm.authservice.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val authService: AuthService
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(AuthController::class.java)
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    fun login(@RequestBody loginRequestDTO: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val tokenOptional = authService.authenticate(loginRequestDTO)

        if (tokenOptional.isEmpty) {
            log.warn("Login failed for email: ${loginRequestDTO.email}")
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val token = tokenOptional.get()
        log.info("Token returned to client: $token")

        return ResponseEntity.ok(LoginResponseDTO(token))
    }


    @Operation(summary = "Validate token")
    @GetMapping("/validate")
    fun validateToken(
        @RequestHeader("Authorization") authHeader: String?
    ): ResponseEntity<Unit> {

        // Authorization: Bearer <token>

        if (authHeader == null || authHeader.startsWith("Bearer ").not()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        return if (authService.validateToken(authHeader.substring(7))) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}