package com.br.login.manager.repositories

import com.br.login.manager.models.Login
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginRepository: JpaRepository<Login, Long> {
    fun findByUserId(id: Long, pageable: Pageable): Page<Login>
}