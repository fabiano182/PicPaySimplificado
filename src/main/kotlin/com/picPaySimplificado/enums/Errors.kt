package com.picPaySimplificado.enums

enum class Errors(val code: String, val message: String) {
    //Customer
    CO001("CO-001", "Customer [%s] não existe."),
    CO002("CO-002", "Não foi possivel atualizar o Customer [%s]."),
    CO003("CO-003", "Não foi possivel deletar o customer [%s]."),
    //Transaction
    TO001("TO-001", "Customer [%s] não existe ou não é lojista.")
}