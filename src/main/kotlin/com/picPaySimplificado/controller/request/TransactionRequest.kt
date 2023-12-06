package com.picPaySimplificado.controller.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class TransactionRequest(
    @field:NotNull(message = "necessario informar um id que ira enviar")
    val envia: Int,

    @field:NotNull(message = "necessario informar o id do lojista que ira receber")
    val recebe: Int,

    @field:NotNull(message = "necessario informar um valor")
    val valor: Float
)
