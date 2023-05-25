package com.br.login.manager.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lombok.Data
import lombok.NoArgsConstructor
@Table(name = "logins")
@Entity
@Data
@NoArgsConstructor

class Login(
    //TODO não permitir login repetido
    var userName: String,
    var password: String,
    var site: String,
    var observation: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference var user: UserModel?
) {

    constructor() : this("", "", "", "", null)

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


}