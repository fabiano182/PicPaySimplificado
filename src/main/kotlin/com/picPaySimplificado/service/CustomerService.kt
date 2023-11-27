package com.picPaySimplificado.service


import jakarta.transaction.Transactional
import com.picPaySimplificado.model.CustomerModel
import org.springframework.stereotype.Service
import com.picPaySimplificado.repository.CustomerRepository
import kotlin.jvm.optionals.toList

@Service
@Transactional
class CustomerService(private val repository: CustomerRepository) {

    fun getAll(): List<CustomerModel> {
        return repository.findAll().toList()
    }

    fun create(customer: CustomerModel) {
        repository.save(customer)
    }

    fun findCustomer(id: Int): CustomerModel {
        id?.let {
            return repository.findById(id).toList().get(id)
        }
        return repository.findAll().toList().get(id)
    }

    fun update(customer: CustomerModel) {
        if(!repository.existsById(customer.id!!)){
            throw Exception()
        }
        repository.save(customer)
    }
    
    fun delete(id: Int){
        if(!repository.existsById(id)){
            throw Exception()
        }
        repository.deleteById(id)
    }
}
