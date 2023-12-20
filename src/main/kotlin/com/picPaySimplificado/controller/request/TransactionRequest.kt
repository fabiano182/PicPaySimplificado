package com.picPaySimplificado.controller.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class TransactionRequest(

    @field:NotNull(message = "necessario informar um id que ira enviar")
    val envia: Int,

    @field:NotNull(message = "necessario informar o id do lojista que ira receber")
    val recebe: Int,

    @field:NotNull(message = "necessario informar um valor")
    @field:Positive(message = "O valor não deve ser negativo")
    val valor: Float
)
