package com.picPaySimplificado.repository

import com.picPaySimplificado.model.transactionModel
import org.springframework.data.repository.CrudRepository

interface transactionRepository: CrudRepository<transactionModel, Int> {
    fun existsByEnvia (envia: Int): Boolean
    fun existsByRecebe (recebe: Int): Boolean
}