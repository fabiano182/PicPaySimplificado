package com.picPaySimplificado.controller.request

import com.picPaySimplificado.validation.emailValidation.EmailAvailable
import com.picPaySimplificado.validation.registroGovernoValidation.RegistroGovernoAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull


data class PutCustomerRequest(

    @field:NotEmpty(message = "NomeCompleto não pode ser nulo")
    var nomeCompleto: String,

    @field:NotEmpty(message = "RegistroGoverno não pode ser nulo")
    @RegistroGovernoAvailable
    var registroGoverno: String,

    @field:Email(message = "E-mail invalido") @field:NotEmpty( message = "E-mail não pode ser nulo")
    @EmailAvailable
    var email: String,

    @field:NotEmpty(message = "Senha não pode ser nula")
    var senha: String,

    @field:NotNull(message = "Saldo não pode ser nulo")
    var saldo: Float
) {
}