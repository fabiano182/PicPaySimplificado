package com.picPaySimplificado.request

data class PostCustomerRequest(

    var nomeCompleto: String,

    var registroGoverno: Int,

    var email: String,

    var senha: String,

    var saldo: Float
) {
}