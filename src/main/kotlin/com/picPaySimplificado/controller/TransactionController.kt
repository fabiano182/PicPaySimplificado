package com.picPaySimplificado.controller

import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.controller.request.TransactionRequest
import com.picPaySimplificado.service.TransactionService
import com.picPaySimplificado.extensions.toTransactionModel
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("transference")
class TransactionController(val transactionService: TransactionService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun transference(@RequestBody @Valid transaction: TransactionRequest) {
        transactionService.transference(transaction.toTransactionModel(transaction))
    }

    @GetMapping
    fun getAll(): List<TransactionModel> {
        return transactionService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): TransactionModel {
        return transactionService.getTransaction(id)
    }
}