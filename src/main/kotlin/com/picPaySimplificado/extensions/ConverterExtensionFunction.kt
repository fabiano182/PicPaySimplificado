package extensions.extensions

import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.request.PostCustomerRequest
import com.picPaySimplificado.request.PutCustomerRequest
import com.picPaySimplificado.request.TransactionRequest

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

fun TransactionRequest.toTransactionModel(transaction: TransactionRequest): TransactionModel {
    return TransactionModel(
        envia = this.envia,

        recebe = this.recebe,

        valor = this.valor
    )
}
