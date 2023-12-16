package com.picPaySimplificado.enums

enum class Errors(val code: String, val message: String) {
    //Generic
    OP001("OP-001", "Invalid Request"),
    OP002("OP-002", "Request cannot be null"),
    //Customer
    CO001("CO-001", "Customer [%s] não existe."),
    CO002("CO-002", "Não foi possivel atualizar o Customer [%s]."),
    CO003("CO-003", "Não foi possivel deletar o customer [%s]."),
    CO004("CO-004", "Insira um CPF ou um CNPJ"),
    //Transaction
    TO001("TO-001", "Customer [%s] não existe ou não é lojista.")
}