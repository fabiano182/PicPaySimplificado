package com.picPaySimplificado.controller

import com.picPaySimplificado.request.transactionRequest
import com.picPaySimplificado.service.TransactionService
import extensions.extensions.toTransactionModel
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class transactionController( val transactionService: TransactionService) {
    @PostMapping("/transferencia")
    fun transferencia(@RequestBody transaction: transactionRequest){
        transactionService.transferencia(transaction.toTransactionModel(transaction))
    }

}