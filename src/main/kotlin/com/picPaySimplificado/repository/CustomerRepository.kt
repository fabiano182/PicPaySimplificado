package com.picPaySimplificado.repository

import com.picPaySimplificado.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<CustomerModel, Int> {
    fun existsByEmail(email: String): Boolean
    fun existsByRegistroGoverno(registroGoverno: String): Boolean
}