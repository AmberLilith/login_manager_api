package com.br.login.manager.dtos

import com.br.login.manager.Cript
import com.br.login.manager.models.UserModel
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class UserModelDto(
    @field:NotBlank(message = "Login não pode ser nulo")
    var userName: String,
    @field:NotBlank(message = "password não pode ser nulo")
    var pass: String,
    @field:Email
    var email: String
    )
{
    constructor(userModel: UserModel): this(userModel.userName, Cript.bCriptPasswordEncoder().encode(userModel.pass), userModel.email)

    fun dtoToUserModel(): UserModel{
        return UserModel(null, this.userName, Cript.bCriptPasswordEncoder().encode(this.pass), this.email)
    }

    class UserModelDtoWithoutPassword(
        var userName: String
    ){


        constructor(userModel: UserModel):this(userModel.userName)
    }

    class UserModelDtoWithOcultPassword(
        var userName: String,
        var pass : String,
        var email: String
    ){

        constructor(userModel: UserModel): this(userModel.userName,"************", userModel.email )
    }

    class UserModelDtoWithNullableFields(
        var userName: String,
        var pass : String,
        var email: String
    ){

        constructor(userModel: UserModel): this(userModel.userName,userModel.pass, userModel.email)
    }




}