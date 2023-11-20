package com.picPaySimplificado.request


data class PutCustomerRequest(

    var nomeCompleto: String,

    var registroGoverno: Int,

    var email: String,

    var senha: String,

    var saldo: Float
) {
}