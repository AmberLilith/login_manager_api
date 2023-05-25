package com.br.login.manager

import org.jasypt.util.text.BasicTextEncryptor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Cript {

    //Ao chamar encrypt e decrypt tanto key quanto iv deve ter 16 caracteres

    companion object{
        fun bCriptPasswordEncoder():BCryptPasswordEncoder{
            return BCryptPasswordEncoder()
        }


        fun encrypt(text: String):String{
            val textEncrytor = BasicTextEncryptor()
            textEncrytor.setPassword("senhaprotecao")
            return textEncrytor.encrypt(text)
        }

        fun decrypt(encryptedText: String):String{
            val textEncrytor = BasicTextEncryptor()
            textEncrytor.setPassword("senhaprotecao")
            return textEncrytor.decrypt(encryptedText)
        }

    }
}