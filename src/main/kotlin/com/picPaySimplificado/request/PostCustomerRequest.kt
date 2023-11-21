package com.picPaySimplificado.request

data class PostCustomerRequest(

    var nomeCompleto: String,

    var registroGoverno: String,

    var email: String,

    var senha: String,

    var saldo: Float
) {
}