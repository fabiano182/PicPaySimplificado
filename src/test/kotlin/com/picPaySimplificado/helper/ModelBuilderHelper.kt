package com.picPaySimplificado.helper

import com.picPaySimplificado.enums.CustomerStatus
import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.TransactionModel
import java.util.*


fun buildCustomer(
    id: Int? = null,
    nomeCompleto: String = "customer name",
    registroGoverno: String = (1..11).joinToString("") { (0..9).random().toString() },
    email: String = "${UUID.randomUUID()}@email.com",
    senha: String = "password",
    saldo: Float = 500.0F,
    status: CustomerStatus = CustomerStatus.ATIVO
): CustomerModel = CustomerModel(
    id = id,
    nomeCompleto = nomeCompleto,
    registroGoverno = registroGoverno,
    email = email,
    senha = senha,
    saldo = saldo,
    status = status
)


fun buildTransaction(
    id: Int? = null,
    envia: Int = Random().nextInt(),
    recebe: Int = Random().nextInt(),
    valor: Float = 50.0F
) = TransactionModel(
    id = id,
    envia = envia,
    recebe = recebe,
    valor = valor,
    date = null
)