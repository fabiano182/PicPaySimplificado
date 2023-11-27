package com.picPaySimplificado.controller

import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.request.TransactionRequest
import com.picPaySimplificado.service.TransactionService
import extensions.extensions.toTransactionModel
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("transferencia")
class TransactionController(val transactionService: TransactionService) {
    @PostMapping
    fun transferencia(@RequestBody transaction: TransactionRequest) {
        transactionService.transferencia(transaction.toTransactionModel(transaction))
    }

    @GetMapping("/osama")
    fun getAll(): List<TransactionModel> {
        return transactionService.getAll()
    }

}