package com.picPaySimplificado.service

import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.repository.CustomerRepository
import com.picPaySimplificado.repository.TransactionRepository
import com.picPaySimplificado.validations.TransactionValidation
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val transactionValidation: TransactionValidation,
    private val customerRepository: CustomerRepository
) {

    fun transferencia(transaction: TransactionModel) {

        val validar1: Boolean = transactionValidation.checarExistencia(transaction)
        val validar2: Boolean = transactionValidation.checarRegistroGoverno(transaction)
        val validar3: Boolean = transactionValidation.checarSaldo(transaction)
        val validar4: Boolean = transactionValidation.checarApiAprovacao()

        val GetsubtrairValor = customerRepository.findById(transaction.envia).get()
        val subtrairValor = GetsubtrairValor

        val GetsomarValor = customerRepository.findById(transaction.recebe).get()
        val somarValor = GetsomarValor


        val dataTransacao: LocalDate? = java.time.LocalDate.now()
        val postHistory = TransactionModel(
            envia = transaction.envia,
            recebe = transaction.recebe,
            valor = transaction.valor,
            date = dataTransacao,
        )

//        if (!validar1) {
//            return
//        }


        if (validar1 && validar2 && validar3 && validar4) {
            subtrairValor.saldo = subtrairValor.saldo - transaction.valor

            customerRepository.save(subtrairValor)

            somarValor.saldo = transaction.valor + somarValor.saldo
            customerRepository.save(somarValor)

            transactionRepository.save(postHistory)
        } else {
            return
        }
    }
    fun getAll(): List<TransactionModel> {
        return transactionRepository.findAll().toList()
    }

}