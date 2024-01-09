package com.picPaySimplificado.service


import com.picPaySimplificado.enums.CustomerStatus
import com.picPaySimplificado.enums.Errors
import com.picPaySimplificado.exception.BadRequestException
import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.model.TransactionModel
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
                Errors.CO004.message, Errors.CO004.code
            )
        }
    }

    fun findCustomer(id: Int): CustomerModel {
        return repository.findById(id).orElseThrow {
            com.picPaySimplificado.exception.NotFoundException(
                Errors.CO001.message.format(id), Errors.CO001.code
            )
        }
    }


    fun update(customer: CustomerModel) {
        if (!repository.existsById(customer.id!!)) {
            throw BadRequestException(
                Errors.CO002.message.format(customer.id), Errors.CO002.code
            )
        }
        customer.status = repository.findById(customer.id!!).get().status
        repository.save(customer)
    }

    fun delete(id: Int) {
        if (!repository.existsById(id)) {
            throw BadRequestException(
                Errors.CO003.message.format(id), Errors.CO003.code
            )
        }
        val customer = repository.findById(id).get()

        customer.status = CustomerStatus.INATIVO
        repository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !repository.existsByEmail(email)

    }

    fun registroGovernoAvailable(registroGoverno: String): Boolean {
        return !repository.existsByRegistroGoverno(registroGoverno)
    }

    //TRANSFERENCE

    fun senderValidate(transaction: TransactionModel): CustomerModel {

        val senderCheck: Boolean = repository.existsById(transaction.envia)
        val sender = repository.findById(transaction.envia)

        if (!senderCheck) throw BadRequestException(
            Errors.TO003.message.format(transaction.envia), Errors.TO003.code
        )

        if(!sender.get().customerStatus()) throw BadRequestException(
            Errors.TO005.message.format(transaction.envia), Errors.TO005.code
        )

        if (!sender.get().ePF()) throw BadRequestException(
            Errors.TO002.message.format(transaction.envia), Errors.TO002.code
        )

        return sender.get()
    }

    fun recipientValidate(transaction: TransactionModel): CustomerModel {

        val recipientCheck: Boolean = repository.existsById(transaction.recebe)
        val recipient = repository.findById(transaction.recebe)

        if (!recipientCheck) throw BadRequestException(
            Errors.TO003.message.format(transaction.recebe), Errors.TO003.code
        )

        return recipient.get()
    }

    fun checkBalance(valor: Float, senderBalance: Float): Boolean {
        if (valor <= senderBalance) return true
        throw BadRequestException(
            Errors.TO004.message, Errors.TO004.code
        )
    }
}
