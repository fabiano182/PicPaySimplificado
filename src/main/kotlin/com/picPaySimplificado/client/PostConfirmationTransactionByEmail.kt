package com.picPaySimplificado.client

import com.picPaySimplificado.model.ConfirmSendModel
import com.picPaySimplificado.model.TransactionModel
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
@Service
class PostConfirmationTransactionByEmail {
    fun sendConfirmationForEmailApi(transactionModel: TransactionModel): Boolean {
        val restTemplate = RestTemplate()

        val transferenceParam: ResponseEntity<ConfirmSendModel> = restTemplate.postForEntity(
            "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6",
            transactionModel,
            ConfirmSendModel::class.java
        )
        if (transferenceParam != null) {
            return true
        }
        return false
    }
}