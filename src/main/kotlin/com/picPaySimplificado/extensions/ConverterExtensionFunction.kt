package com.picPaySimplificado.extensions

import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.controller.request.PostCustomerRequest
import com.picPaySimplificado.controller.request.PutCustomerRequest
import com.picPaySimplificado.controller.request.TransactionRequest
import com.picPaySimplificado.enums.CustomerStatus

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(

        nomeCompleto = this.nomeCompleto,

        registroGoverno = this.registroGoverno,

        email = this.email,

        senha = this.senha,

        saldo = this.saldo,

        status = CustomerStatus.ATIVO
        )
}

fun PutCustomerRequest.toCustomerModel(previousValue: CustomerModel): CustomerModel {
    return CustomerModel(

        nomeCompleto = this.nomeCompleto,

        registroGoverno = this.registroGoverno,

        email = this.email,

        senha = this.senha,

        saldo = this.saldo,

        status = previousValue.status

        )
}

fun TransactionRequest.toTransactionModel(transaction: TransactionRequest): TransactionModel {
    return TransactionModel(
        envia = this.envia,

        recebe = this.recebe,

        valor = this.valor
    )
}
