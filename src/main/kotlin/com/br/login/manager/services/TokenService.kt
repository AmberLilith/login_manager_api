package com.br.login.manager.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.br.login.manager.models.UserModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


@Service
class TokenService {
    @Value("\${token.jwt.SECRET}")
    val secret = "secret"

    @Value("\${token.jwt.EXPIRATION}")
    val expiration = 0L

    fun generateToken(userModel: UserModel):String{
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.create()
                .withIssuer("Login Manager")
                .withSubject(userModel.userName)
                .withClaim("id", userModel.id.toString())
                .withExpiresAt(expirationDate())
                .sign(algorithm)

        }catch (ex: JWTCreationException){
            throw RuntimeException("Erro ao gerar token jwt", ex)
        }
    }

    fun getSubject(tokenJWT: String):String{
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.require(algorithm)
                .withIssuer("Login Manager")
                .build()
                .verify(tokenJWT)
                .subject
        } catch (exception: JWTVerificationException) {
            throw RuntimeException("Token JWT inválido ou expirado!")
        }
    }

    fun getLoggedUserId(tokenJWT: String):String{
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.require(algorithm)
                .withIssuer("Login Manager")
                .build()
                .verify(tokenJWT)
                .id
        } catch (exception: JWTVerificationException) {
            throw RuntimeException("Token JWT inválido ou expirado!")
        }
    }

    fun expirationDate(): Instant{
        return LocalDateTime.now().plusDays(expiration).toInstant(ZoneOffset.of("-03:00"))
    }
}