package com.picPaySimplificado.service

import com.picPaySimplificado.client.ConfirmTransferApproval
import com.picPaySimplificado.client.PostConfirmationTransactionByEmail
import com.picPaySimplificado.enums.Errors
import com.picPaySimplificado.exception.NotFoundException
import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.repository.CustomerRepository
import com.picPaySimplificado.repository.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val customerService: CustomerService,
    private val customerRepository: CustomerRepository,
    private val confirmTransferApproval: ConfirmTransferApproval,
    private val postConfirmationTransactionByEmail: PostConfirmationTransactionByEmail

) {

    fun transference(transaction: TransactionModel) {

        val getSenderData = customerService.senderValidate(transaction)  //Erro tratado
        val getMerchantData = customerService.merchantValidate(transaction) //Erro tratado

        customerService.checkBalance(transaction.valor, getSenderData.saldo)  //Erro tratado

        val subtractValueSender = getSenderData
        val addMerchantValue = getMerchantData

        val dataTransacao: LocalDate? = java.time.LocalDate.now()
        val postHistory = TransactionModel(
            envia = transaction.envia,
            recebe = transaction.recebe,
            valor = transaction.valor,
            date = dataTransacao,
        )

        if (confirmTransferApproval.getValidateTransferApproval())
            subtractValueSender.saldo = subtractValueSender.saldo - transaction.valor

            customerRepository.save(subtractValueSender)

            addMerchantValue.saldo = transaction.valor + addMerchantValue.saldo
            customerRepository.save(addMerchantValue)

            postConfirmationTransactionByEmail.sendConfirmationForEmailApi(transaction)

            transactionRepository.save(postHistory)
    }

    fun getAll(): List<TransactionModel> {
        return transactionRepository.findAll().toList()
    }

    fun getTransaction(id: Int): TransactionModel {
        return transactionRepository.findById(id).orElseThrow {
            NotFoundException(Errors.TO001.message.format(id), Errors.TO001.code)
        }

    }


}