package com.picPaySimplificado.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity(name = "transaction_history")
data class TransactionModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,

    @Column var envia: Int,

    @Column var recebe: Int,

    @Column var valor: Float,

    @Column(name = "transaction_date") var date: LocalDate? = null
) {
}