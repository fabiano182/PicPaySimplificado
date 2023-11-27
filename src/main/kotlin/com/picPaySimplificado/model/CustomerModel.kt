package com.picPaySimplificado.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "customer")
data class CustomerModel(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,

    @Column var nomeCompleto: String,

    @Column(unique = true) var registroGoverno: String,

    @Column(unique = true) var email: String,

    @Column var senha: String,

    @Column var saldo: Float
) {

    fun ePJ() = this.registroGoverno.length == 14

    fun ePF() = this.registroGoverno.length == 11

}
