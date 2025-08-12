package com.pm.authservice.service

import com.pm.authservice.dto.LoginRequestDTO
import com.pm.authservice.util.JwtUtil
import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(AuthService::class.java)
    }

    fun authenticate(loginRequestDTO: LoginRequestDTO): Optional<String> {
        val token = userService.findByEmail(loginRequestDTO.email)
            .filter { user -> passwordEncoder.matches(loginRequestDTO.password, user.password!!) }
            .map { user ->
                val generated = jwtUtil.generateToken(user.email!!, user.role!!)
                log.info("Generated token for user ${user.email}: $generated")
                generated
            }

        return token
    }

    fun validateToken(token: String): Boolean {
        return try {
            jwtUtil.validateToken(token)
            true
        } catch (e: JwtException) {
            false
        }
    }
}
