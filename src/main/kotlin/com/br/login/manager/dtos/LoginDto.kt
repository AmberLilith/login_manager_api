package com.br.login.manager.dtos

import com.br.login.manager.Cript
import com.br.login.manager.models.Login
import com.br.login.manager.models.UserModel
import jakarta.validation.constraints.NotBlank

class LoginDto(
    var id: Long,
    //TODO anotação @NotNull não está funcionando como esperado. A classe ExceptionHandler não a está capiturando
    @field:NotBlank(message = "Valor informado é inválido!")
    var userName: String,
    @field:NotBlank(message = "Valor informado é inválido!")
    var password: String,
    var site: String,
    var observation: String
) {

    var userOwner: UserModel? = null

    constructor(login: Login): this(login.id!!, login.userName, Cript.encrypt(login.password), login.site, login.observation){

    }


    fun dtoToLogin(): Login{
        return Login( this.userName,Cript.encrypt(this.password), this.site, this.observation, this.userOwner)
    }


    class LoginDtoWithoutUserLogins(
        var id: Long,
        var userName: String,
        var password: String,
        var site: String,
        var observation: String
    ){

        constructor(login: Login): this(login.id!!, login.userName,Cript.decrypt(login.password), login.site, login.observation)

    }

    class LoginDtoWithoutUserLoginsAndPassword(
        var userName: String,
        var site: String,
        var observation: String
    ){

        constructor(login: Login): this(login.userName, login.site, login.observation)

    }

    class LoginDtoWithoutUserLoginsWithId(
        var id: Long,
        var userName: String,
        var site: String,
        var observation: String
    ){

        constructor(login: Login): this(login.id!!, login.userName, login.site, login.observation)

    }
}