package com.picPaySimplificado.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(

    //@field:NotEmpty
    var nomeCompleto: String,

    //@field:NotEmpty
    var registroGoverno: String,

    //@field:Email
    var email: String,

    //@field:NotEmpty
    var senha: String,

    //@field:NotEmpty
    var saldo: Float
) {
}