package com.picPaySimplificado.validations

import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.aprovarTransacaoModel
import com.picPaySimplificado.model.transactionModel
import com.picPaySimplificado.repository.customerRepository
import com.picPaySimplificado.repository.transactionRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate as RestTemplate

@Service
class TransactionValidation(
    private val customerRepository: customerRepository
) {

    fun checarExistencia(transaction: transactionModel): Boolean {
        val enviaCheck: Boolean = customerRepository.existsById(transaction.envia)
        val recebeCheck: Boolean = customerRepository.existsById(transaction.recebe)

        if (enviaCheck && recebeCheck) {
            return true
        } else {
            return false
        }
    }

    fun checarRegistroGoverno(transaction: transactionModel): Boolean {

        val client = customerRepository.findById(transaction.envia)
        val vendedor = customerRepository.findById(transaction.recebe)

        if (client.get().ePF() && vendedor.get().ePJ()) {
            return true
        }
        throw Exception()

//        val registroGovernoEnvia = (registroEnvia as CustomerModel).registroGoverno.toString().length
//        val registroGovernoRecebe = (registroRecebe as CustomerModel).registroGoverno.toString().length
//
//        when (registroGovernoEnvia) {
//            11 ->when (registroGovernoRecebe){
//                11 -> return false
//                14 -> return true
//                else -> throw Exception()
//            }
//            14-> return false
//            else -> throw Exception()
//        }
    }

    fun checarSaldo(transaction: transactionModel): Boolean {
        val PegarsaldoEnvia = customerRepository.findById(transaction.envia)

        val saldoEnvia = (PegarsaldoEnvia as CustomerModel).saldo

        if (transaction.valor >= saldoEnvia) {
            return true
        } else {
            throw Exception()
            return false
        }
    }

    fun checarApiAprovacao(): Boolean {
        val restTemplate = RestTemplate()

        val parametroTransacao = restTemplate.getForObject("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", aprovarTransacaoModel::class.java)

        if (parametroTransacao != null) {
            when(parametroTransacao.message){
                "Autorizado" -> return true
                else -> return false
            }
        }else{
            throw Exception()
            return false
        }
    }
}