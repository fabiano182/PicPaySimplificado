package com.picPaySimplificado.enums

enum class Errors(val code: String, val message: String) {
    CO001("CO-001", "Customer [%s] não existe"),
    TO001("TO-001", "Customer [%s] não existe ou não é lojista ")

}