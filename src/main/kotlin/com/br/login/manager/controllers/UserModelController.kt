package com.br.login.manager.controllers

import com.br.login.manager.dtos.UserModelDto
import com.br.login.manager.services.UserModelService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserModelController {

    @Autowired
    lateinit var service: UserModelService


    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid userModelDto: UserModelDto) : ResponseEntity<UserModelDto.UserModelDtoWithOcultPassword>{
        val userModelResponse = service.create(userModelDto)
        return ResponseEntity(userModelResponse, HttpStatus.CREATED)
    }

    @GetMapping()
    fun showLoggedUser():ResponseEntity<UserModelDto.UserModelDtoWithoutPassword>{
        return ResponseEntity(service.showLoggedUser(), HttpStatus.OK)
    }

    @PutMapping()
    @Transactional
    fun updateLoggedUser(@RequestBody userModelDtoWithNewValues: UserModelDto.UserModelDtoWithNullableFields):ResponseEntity<UserModelDto.UserModelDtoWithOcultPassword>{
        return ResponseEntity(service.updateLoggedUser(userModelDtoWithNewValues), HttpStatus.OK)
    }
}