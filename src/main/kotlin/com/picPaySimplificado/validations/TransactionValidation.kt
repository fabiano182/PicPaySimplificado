package com.picPaySimplificado.validations

import com.picPaySimplificado.model.AprovarTransacaoModel
import com.picPaySimplificado.model.ConfirmarEnvioModel
import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.repository.CustomerRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TransactionValidation(
    private val customerRepository: CustomerRepository
) {

    fun checarExistencia(transaction: TransactionModel): Boolean {
        val enviaCheck: Boolean = customerRepository.existsById(transaction.envia)
        val recebeCheck: Boolean = customerRepository.existsById(transaction.recebe)

        if (enviaCheck && recebeCheck) {
            return true
        } else {
            return false
        }
    }

    fun checarRegistroGoverno(transaction: TransactionModel): Boolean {

        val client = customerRepository.findById(transaction.envia)
        val vendedor = customerRepository.findById(transaction.recebe)

        if (client.get().ePF() && vendedor.get().ePJ()) {
            return true
        }
        return false
    }

    fun checarSaldo(transaction: TransactionModel): Boolean {
        val PegarsaldoEnvia = customerRepository.findById(transaction.envia)

        val saldoEnvia = (PegarsaldoEnvia.get() as CustomerModel).saldo

        if (transaction.valor <= saldoEnvia) {
            return true
        }
        return false
    }

    fun checarApiAprovacao(): Boolean {
        val restTemplate = RestTemplate()

        val parametroTransacao = restTemplate.getForObject(
            "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", AprovarTransacaoModel::class.java
        )

        if (parametroTransacao != null) {
            when (parametroTransacao.message) {
                "Autorizado" -> return true
                else -> return false
            }
        }
        return false
    }

    fun postForEmailApi(transactionModel: TransactionModel): Boolean {
        val restTemplate = RestTemplate()

        // Fazer com que ele retorne um boolean
        val parametroTransacao = restTemplate.postForEntity(
            "https://run.mocky.falseio/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", transactionModel, ConfirmarEnvioModel::class.java
        )

        return true


    }
}