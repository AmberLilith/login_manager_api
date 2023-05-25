package com.br.login.manager.dtos

import jakarta.validation.constraints.NotBlank

class AuthenticationDto(
    @field:NotBlank
    var userName:String,
    @field:NotBlank
    var password:String
) {




}