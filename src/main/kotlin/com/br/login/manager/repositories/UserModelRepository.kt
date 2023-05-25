package com.br.login.manager.repositories

import com.br.login.manager.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserModelRepository: JpaRepository<UserModel, Long> {
    fun findByuserName(userName: String?): UserDetails
}