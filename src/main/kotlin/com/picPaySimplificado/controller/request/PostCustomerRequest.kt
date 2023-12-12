package com.picPaySimplificado.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PostCustomerRequest(

    @field:NotEmpty(message = "nomeCompleto não pode ser nulo")
    var nomeCompleto: String,

    @field:NotEmpty(message = "registroGoverno não pode ser nulo")
    var registroGoverno: String,

    @field:Email(message = "email invalido") @field:NotEmpty( message = "email não pode ser nulo")
    var email: String,

    @field:NotEmpty(message = "senha não pode ser nula")
    var senha: String,

    @field:NotNull(message = "saldo não pode ser nulo")
    var saldo: Float
) {
}