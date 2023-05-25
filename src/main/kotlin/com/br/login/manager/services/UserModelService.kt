package com.br.login.manager.services

import com.br.login.manager.Cript
import com.br.login.manager.dtos.UserModelDto
import com.br.login.manager.models.UserModel
import com.br.login.manager.repositories.UserModelRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserModelService(val repository: UserModelRepository) {

    fun create(userModelDto: UserModelDto): UserModelDto.UserModelDtoWithOcultPassword {
        val userModelToCreate = userModelDto.dtoToUserModel()
        val userModelCreated = repository.save(userModelToCreate)
        return UserModelDto.UserModelDtoWithOcultPassword(userModelCreated)
    }


    fun showLoggedUser():UserModelDto.UserModelDtoWithoutPassword {
        return UserModelDto.UserModelDtoWithoutPassword(SecurityContextHolder.getContext().authentication.principal as UserModel)
    }

    fun updateLoggedUser(userModelDtoWithNewValues: UserModelDto.UserModelDtoWithNullableFields): UserModelDto.UserModelDtoWithOcultPassword {
            val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserModel
            loggedUser.userName = userModelDtoWithNewValues.userName.ifBlank { loggedUser.userName }
            loggedUser.pass = if(Cript.bCriptPasswordEncoder().matches(userModelDtoWithNewValues.pass,loggedUser.pass )) loggedUser.pass else Cript.bCriptPasswordEncoder().encode(userModelDtoWithNewValues.pass)
            return UserModelDto.UserModelDtoWithOcultPassword(repository.save(loggedUser))
    }


}