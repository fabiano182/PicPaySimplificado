package com.picPaySimplificado.controller

import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.controller.request.TransactionRequest
import com.picPaySimplificado.service.TransactionService
import extensions.extensions.toTransactionModel
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("transferencia")
class TransactionController(val transactionService: TransactionService) {
    @PostMapping
    fun transferencia(@RequestBody @Valid transaction: TransactionRequest) {
        transactionService.transferencia(transaction.toTransactionModel(transaction))
    }

    @GetMapping
    fun getAll(): List<TransactionModel> {
        return transactionService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(id: Int): TransactionModel {
        return transactionService.getTransaction(id)
    }
}