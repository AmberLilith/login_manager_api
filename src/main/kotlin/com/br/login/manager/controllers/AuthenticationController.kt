package com.br.login.manager.controllers

import com.br.login.manager.dtos.AuthenticationDto
import com.br.login.manager.dtos.TokenDto
import com.br.login.manager.models.UserModel
import com.br.login.manager.services.TokenService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthenticationController {

    @Autowired
    lateinit var manager: AuthenticationManager

    @Autowired
    lateinit var tokenService: TokenService


    @PostMapping
    fun auth(@RequestBody @Valid authenticationDto: AuthenticationDto): ResponseEntity<TokenDto>{
        val authenticationToken = UsernamePasswordAuthenticationToken(authenticationDto.userName, authenticationDto.password)
        val authentication = manager.authenticate(authenticationToken)

        val tokenJWT = tokenService.generateToken((authentication.principal as UserModel))
        return ResponseEntity.ok(TokenDto(tokenJWT, "BEARER"))
    }


}