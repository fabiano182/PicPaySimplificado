package com.picPaySimplificado.repository

import com.picPaySimplificado.model.TransactionModel
import org.springframework.data.repository.CrudRepository

interface TransactionRepository: CrudRepository<TransactionModel, Int>