package com.pm.authservice.util

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

class JwtUtil(private val secretKey: Key) {

    fun generateToken(email: String, role: String): String {
        val expiry = Date(System.currentTimeMillis() + Duration.ofHours(10).toMillis())
        return Jwts.builder().apply {
            subject(email)
            claim("role", role)
            issuedAt(Date())
            expiration(expiry)
            signWith(secretKey)
        }.compact()
    }


    fun validateToken(token: String) {
        try {
            Jwts.parser()
                .verifyWith(secretKey as SecretKey)
                .build()
                .parseSignedClaims(token)
        } catch (e: SignatureException) {
            throw JwtException("Invalid JWT signature", e)
        } catch (e: JwtException) {
            throw JwtException("Invalid JWT", e)
        }
    }
}

@Configuration
class JwtConfig {

    @Bean
    fun jwtUtil(@Value("\${jwt.secret}") secret: String): JwtUtil {
        val keyBytes = Base64.getDecoder().decode(secret.toByteArray(StandardCharsets.UTF_8))
        val secretKey = Keys.hmacShaKeyFor(keyBytes)
        return JwtUtil(secretKey)
    }
}
