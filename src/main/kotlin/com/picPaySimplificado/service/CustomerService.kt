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
        when (customer.registroGoverno.length) {
            11 -> repository.save(customer)
            14 -> repository.save(customer)
            else -> throw BadRequestException(
                Errors.CO004.message,
                Errors.CO004.code
            )
        }
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

    fun emailAvailable(email: String): Boolean {
        return !repository.existsByEmail(email)

    }

    fun registroGovernoAvailable(registroGoverno: String): Boolean {
        return !repository.existsByRegistroGoverno(registroGoverno)
    }
}
