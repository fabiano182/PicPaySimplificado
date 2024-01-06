package com.picPaySimplificado.model

import com.picPaySimplificado.enums.CustomerStatus
import jakarta.persistence.*

@Entity(name = "customer")
data class CustomerModel(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,

    @Column var nomeCompleto: String,

    @Column(unique = true) var registroGoverno: String,

    @Column(unique = true) var email: String,

    @Column var senha: String,

    @Column var saldo: Float,


    @Column @Enumerated(EnumType.STRING) var status: CustomerStatus
) {

    fun ePJ() = this.registroGoverno.length == 14

    fun ePF() = this.registroGoverno.length == 11

}
