package com.br.login.manager.services

import com.br.login.manager.repositories.UserModelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService: UserDetailsService {

    @Autowired
    lateinit var userModelRepository: UserModelRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        return userModelRepository.findByuserName(username)
    }
}