package com.picPaySimplificado.service


import com.picPaySimplificado.enums.Errors
import com.picPaySimplificado.exception.BadRequestException
import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.repository.CustomerRepository
import jakarta.transaction.Transactional
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
        return repository.findById(id)
            .orElseThrow {
                com.picPaySimplificado.exception.NotFoundException(
                    Errors.CO001.message.format(id),
                    Errors.CO001.code
                )
            }
    }


    fun update(customer: CustomerModel) {
        if (!repository.existsById(customer.id!!)) {
            throw BadRequestException(
                Errors.CO002.message.format(customer.id),
                Errors.CO002.code
            )
        }
        repository.save(customer)
    }

    fun delete(id: Int) {
        if (!repository.existsById(id)) {
            throw BadRequestException(
                Errors.CO003.message.format(id),
                Errors.CO003.code
            )
        }
        repository.deleteById(id)
    }
}
