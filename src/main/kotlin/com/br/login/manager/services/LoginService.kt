package com.br.login.manager.services

import com.br.login.manager.Cript
import com.br.login.manager.dtos.LoginDto
import com.br.login.manager.models.UserModel
import com.br.login.manager.repositories.LoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class LoginService {

    @Autowired
    lateinit var loginRepository: LoginRepository


    fun create(loginDto: LoginDto): LoginDto.LoginDtoWithoutUserLoginsAndPassword{
        val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserModel
        loginDto.userOwner = loggedUser
        val loginToCreate = loginDto.dtoToLogin()
        val loginCreated = loginRepository.save(loginToCreate)
        return LoginDto.LoginDtoWithoutUserLoginsAndPassword(loginCreated)

    }

    fun findAllFromLoggedUser(pageable: Pageable): Page<LoginDto.LoginDtoWithoutUserLoginsWithId> {
        val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserModel
        val logins = loginRepository.findByUserId(loggedUser.id!!, pageable)
        return logins.map{LoginDto.LoginDtoWithoutUserLoginsWithId(it)}
    }


    fun updateLoginFromLoggedUser(loginDtoWithNewValues: LoginDto, id: Long): LoginDto.LoginDtoWithoutUserLoginsAndPassword {
        val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserModel
        val optionalLoginOldValues = loginRepository.findById(id)
        if(optionalLoginOldValues.isPresent){
            val loginWithOldValues = optionalLoginOldValues.get()
            if(loginWithOldValues.user!!.id == loggedUser.id){
                loginWithOldValues.userName = loginDtoWithNewValues.userName.ifBlank { loginWithOldValues.userName }
                loginWithOldValues.password = Cript.encrypt(loginDtoWithNewValues.password).ifBlank { loginWithOldValues.password }
                loginWithOldValues.site = loginDtoWithNewValues.site.ifBlank { loginWithOldValues.site }
                loginWithOldValues.observation = loginDtoWithNewValues.observation.ifBlank { loginWithOldValues.observation }
                return LoginDto.LoginDtoWithoutUserLoginsAndPassword(loginRepository.save(loginWithOldValues))
            }else{
                throw RuntimeException("O login informado não pertente ao usuário logado")
            }
        }else{
            throw RuntimeException("Login não encontrado com id $id")
        }
    }

    fun deleteLoginFromLoggedUser(id: Long):String {
        val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserModel
        val optionalLoginToDelete = loginRepository.findById(id)
        if(optionalLoginToDelete.isPresent){
            val loginToDelete = optionalLoginToDelete.get()
            if(loginToDelete.user!!.id == loggedUser.id){
                loginRepository.deleteById(id)
                return "Login excluído com sucesso!"
            }else{
                throw RuntimeException("O login informado não pertente ao usuário logado")
            }
        }else{
            throw RuntimeException("Login não encontrado com id $id")
        }
    }




    fun findById(id: Long): LoginDto.LoginDtoWithoutUserLogins {
        val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserModel
       val optionalLogin =  loginRepository.findById(id)
        if(optionalLogin.isPresent){
            val login = optionalLogin.get()
            if(login.user!!.id == loggedUser.id){
                return LoginDto.LoginDtoWithoutUserLogins(login)
            }else{
                throw RuntimeException("O login informado não pertence ao usuário logado")
            }
        }else{
            throw RuntimeException("Login não encontrado com id $id")
        }
    }




}