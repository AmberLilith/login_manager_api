package com.br.login.manager.security

import com.br.login.manager.repositories.UserModelRepository
import com.br.login.manager.services.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
class SecurityFilter: OncePerRequestFilter() {

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var userModelRepository: UserModelRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenJWT = getToken(request)
        if(tokenJWT != null){
           val subject = tokenService.getSubject(tokenJWT)
            val user = userModelRepository.findByuserName(subject)

            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    fun getToken(request: HttpServletRequest):String?{
        val authorizationHeader = request.getHeader("Authorization")
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "")
        }

        return null
    }
}