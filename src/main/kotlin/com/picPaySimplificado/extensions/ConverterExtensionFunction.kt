package extensions.extensions

import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.transactionModel
import com.picPaySimplificado.request.PostCustomerRequest
import com.picPaySimplificado.request.PutCustomerRequest
import com.picPaySimplificado.request.transactionRequest

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(

        nomeCompleto = this.nomeCompleto,

        registroGoverno = this.registroGoverno,

        email = this.email,

        senha = this.senha,

        saldo = this.saldo,

        )
}

fun PutCustomerRequest.toCustomerModel(customer: PutCustomerRequest): CustomerModel {
    return CustomerModel(

        nomeCompleto = this.nomeCompleto,

        registroGoverno = this.registroGoverno,

        email = this.email,

        senha = this.senha,

        saldo = this.saldo,

        )
}

fun transactionRequest.toTransactionModel(transaction: transactionRequest): transactionModel {
    return transactionModel(
        envia = this.envia,

        recebe = this.recebe,

        valor = this.valor
    )
}
