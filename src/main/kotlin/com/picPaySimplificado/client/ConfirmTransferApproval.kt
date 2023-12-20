package com.picPaySimplificado.client

import com.picPaySimplificado.enums.Errors
import com.picPaySimplificado.exception.AcceptButDenied
import com.picPaySimplificado.model.ApprovalTransactionModel
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ConfirmTransferApproval {
    fun getValidateTransferApproval(): Boolean {
        val restTemplate = RestTemplate()

        val parametroTransacao = restTemplate.getForObject(
            "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", ApprovalTransactionModel::class.java
        )

        if (parametroTransacao != null)
            when (parametroTransacao.message) {
                "Autorizado" -> return true
                else -> throw AcceptButDenied(
                    Errors.OP002.message,
                    Errors.OP002.code
                )
            }
        return false
    }
}