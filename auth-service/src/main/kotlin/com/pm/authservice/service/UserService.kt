package com.pm.authservice.service

import com.pm.authservice.model.User
import com.pm.authservice.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun findByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }
}