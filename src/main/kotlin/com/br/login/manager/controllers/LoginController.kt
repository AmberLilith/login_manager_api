package com.br.login.manager.controllers

import com.br.login.manager.dtos.LoginDto
import com.br.login.manager.services.LoginService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/logins")
class LoginController {

    @Autowired
    lateinit var service: LoginService

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid loginDto: LoginDto) : ResponseEntity<LoginDto.LoginDtoWithoutUserLoginsAndPassword>{
        val loginResponse = service.create(loginDto)
        return ResponseEntity(loginResponse, HttpStatus.CREATED)
    }

    @GetMapping
    fun findAllFromLoggedUser(pageable: Pageable):ResponseEntity<Page<LoginDto.LoginDtoWithoutUserLoginsWithId>>{
        return ResponseEntity(service.findAllFromLoggedUser(pageable), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<LoginDto.LoginDtoWithoutUserLogins>{
        return ResponseEntity(service.findById(id), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@RequestBody loginDto: LoginDto, @PathVariable id: Long): ResponseEntity<LoginDto.LoginDtoWithoutUserLoginsAndPassword>{
        return ResponseEntity(service.updateLoginFromLoggedUser(loginDto, id), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: Long): ResponseEntity<String>{
        return ResponseEntity(service.deleteLoginFromLoggedUser(id), HttpStatus.OK)
    }

}