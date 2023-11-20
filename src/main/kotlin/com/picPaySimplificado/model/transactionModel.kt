package com.picPaySimplificado.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Entity(name = "transaction_history")
data class transactionModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var ID: Int? = null,

    @Column var envia: Int,

    @Column var recebe: Int,

    @Column var valor: Float,

    @Column(name = "transaction_date") var date: LocalDate? = null
) {
}