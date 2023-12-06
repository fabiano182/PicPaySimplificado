package com.picPaySimplificado.service


import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.repository.CustomerRepository
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

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
        return repository.findById(id).orElseThrow{
            com.picPaySimplificado.exception.NotFoundException(
                "Customer ${id} não existe",
                "0001"
            )
        }
    }


    fun update(customer: CustomerModel) {
        if (!repository.existsById(customer.id!!)) {
            throw Exception()
        }
        repository.save(customer)
    }

    fun delete(id: Int) {
        if (!repository.existsById(id)) {
            throw Exception()
        }
        repository.deleteById(id)
    }
}
