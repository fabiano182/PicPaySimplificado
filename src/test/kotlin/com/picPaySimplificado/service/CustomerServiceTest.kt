package com.picPaySimplificado.service

import com.picPaySimplificado.enums.CustomerStatus
import com.picPaySimplificado.exception.BadRequestException
import com.picPaySimplificado.exception.NotFoundException
import com.picPaySimplificado.helper.buildCustomer
import com.picPaySimplificado.helper.buildTransaction
import com.picPaySimplificado.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.*


@ExtendWith(MockKExtension::class)
@SpringBootTest
class CustomerServiceTest {


    @MockK
    private lateinit var repository: CustomerRepository

    @InjectMockKs
    lateinit var customerService: CustomerService

    @Test
    fun `should return all customers`() {
        val fakeCustomers = listOf(buildCustomer())
        //given
        every { repository.findAll() } returns fakeCustomers  //Toda vez que for chamado o repository.findAll(), ele ira retornar fakeCustomer ao invés de bater no banco
        //when
        val customers = customerService.getAll()

        //then
        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun `should create customers cpf`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        //given
        every { repository.save(fakeCustomer) } returns fakeCustomer
        //when
        customerService.create(fakeCustomer)

        //then
        verify(exactly = 1) { repository.save(fakeCustomer) }

    }

    @Test
    fun `should create customers cnpj`() {
        val fakeMerchant = buildCustomer(registroGoverno = "12345678910111")

        //given
        every { repository.save(fakeMerchant) } returns fakeMerchant
        //when
        customerService.create(fakeMerchant)
        //then
        verify(exactly = 1) { repository.save(fakeMerchant) }

    }


    @Test
    fun `should return the specific exception create customers`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)
        fakeCustomer.registroGoverno = fakeCustomer.registroGoverno + 1

        //given
        every { repository.save(fakeCustomer) } returns fakeCustomer
        //when
        val error = assertThrows<BadRequestException> { customerService.create(fakeCustomer) }
        //then

        assertEquals("Insira um CPF ou um CNPJ", error.message)
        assertEquals("CO-004", error.errorCode)
        verify(exactly = 0) { repository.save(any()) }
    }

    @Test
    fun `should return the specific customers`() {
        val id = Random().nextInt()
        val fakeCustomers = buildCustomer(id = id)
        //given
        every { repository.findById(id) } returns Optional.of(fakeCustomers)
        //when
        val customer = customerService.findCustomer(id)
        //then
        assertEquals(fakeCustomers, customer)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun `should return the specific exception of customers`() {
        val id = Random().nextInt()

        //given
        every { repository.findById(id) } returns Optional.empty()
        //when
        val error = assertThrows<NotFoundException> { customerService.findCustomer(id) }
        //then

        assertEquals("Customer [${id}] não existe.", error.message)
        assertEquals("CO-001", error.errorCode)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun `should update customers`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        //given
        every { repository.existsById(id) } returns true
        every { repository.findById(id).get() } returns fakeCustomer
        every { repository.save(fakeCustomer) } returns fakeCustomer

        //when
        customerService.update(fakeCustomer)
        //then
        verify(exactly = 1) { repository.existsById(id) }
        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { repository.save(fakeCustomer) }

    }

    @Test
    fun `should return the specific exception update customers`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        //given
        every { repository.existsById(id) } returns false
        //when
        val error = assertThrows<BadRequestException> { customerService.update(fakeCustomer) }
        //then

        assertEquals("Não foi possivel atualizar o Customer [${id}].", error.message)
        assertEquals("CO-002", error.errorCode)

        verify(exactly = 1) { repository.existsById(id) }
    }

    @Test
    fun `should delete customers`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        //given
        every { repository.existsById(id) } returns true
        every { repository.findById(id).get() } returns fakeCustomer
        every { repository.save(fakeCustomer) } returns fakeCustomer

        //when
        customerService.delete(id)
        //then
        assertEquals(fakeCustomer.status, CustomerStatus.INATIVO)
        verify(exactly = 1) { repository.existsById(id) }
        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { repository.save(fakeCustomer) }

    }

    @Test
    fun `should return the specific exception delete customers`() {
        val id = Random().nextInt()

        //given
        every { repository.existsById(id) } returns false
        //when
        val error = assertThrows<BadRequestException> { customerService.delete(id) }
        //then

        assertEquals("Não foi possivel deletar o customer [${id}].", error.message)
        assertEquals("CO-003", error.errorCode)

        verify(exactly = 1) { repository.existsById(id) }
    }

    @Test
    fun `should return true when email available`() {
        val email = "${Random().nextInt()}@email.com"

        every { repository.existsByEmail(email) } returns false

        val emailAvailable = customerService.emailAvailable(email)

        assertTrue(emailAvailable)
        verify(exactly = 1) { repository.existsByEmail(email) }
    }

    @Test
    fun `should return false when email unavailable`() {
        val email = "${Random().nextInt()}@email.com"

        every { repository.existsByEmail(email) } returns true

        val emailAvailable = customerService.emailAvailable(email)

        assertFalse(emailAvailable)
        verify(exactly = 1) { repository.existsByEmail(email) }
    }

    @Test
    fun `should return true when registroGoverno available`() {
        val registroGoverno: String = (1..11).joinToString("") { (0..9).random().toString() }

        every { repository.existsByRegistroGoverno(registroGoverno) } returns false

        val registroGovernoAvailable = customerService.registroGovernoAvailable(registroGoverno)

        assertTrue(registroGovernoAvailable)
        verify(exactly = 1) { repository.existsByRegistroGoverno(registroGoverno) }
    }

    @Test
    fun `should return true when registroGoverno unavailable`() {
        val registroGoverno: String = (1..11).joinToString("") { (0..9).random().toString() }

        every { repository.existsByRegistroGoverno(registroGoverno) } returns true

        val registroGovernoAvailable = customerService.registroGovernoAvailable(registroGoverno)

        assertFalse(registroGovernoAvailable)
        verify(exactly = 1) { repository.existsByRegistroGoverno(registroGoverno) }
    }

    //TRANSFERENCE
    @Test
    fun `should validate sender and return sender`() {
        val id = Random().nextInt()
        val sender = buildCustomer(id = id)
        val transaction = buildTransaction(envia = id)

        every { repository.existsById(id) } returns true
        every { repository.findById(id).get() } returns sender

        val compareSender = customerService.senderValidate(transaction)

        assertEquals(sender, compareSender)
        verify { repository.existsById(id) }
        verify { repository.findById(id) }
    }

    @Test
    fun `should return first exception in senderValidate`(){
        val id = Random().nextInt()
        val sender = buildCustomer(id = id)
        val transaction = buildTransaction(envia = id)

        every { repository.existsById(id) } returns false
        every { repository.findById(id).get() } returns sender

        val error = assertThrows<BadRequestException> { customerService.senderValidate(transaction) }

        assertEquals("Customer [${id}] não existe", error.message)
        assertEquals("TO-003", error.errorCode)
        verify(exactly = 1) { repository.existsById(id) }
    }

    @Test
    fun `should return second exception in senderValidate`(){
        val id = Random().nextInt()
        val sender = buildCustomer(id = id, status = CustomerStatus.INATIVO)
        val transaction = buildTransaction(envia = id)

        every { repository.existsById(id) } returns true
        every { repository.findById(id).get() } returns sender

        val error = assertThrows<BadRequestException> { customerService.senderValidate(transaction) }

        assertEquals("Impossivel prosseguir com a operação. Customer [${id}] está inativo ", error.message)
        assertEquals("TO-005", error.errorCode)
        verify(exactly = 1) { repository.existsById(id) }
    }

    @Test
    fun `should return third exception in senderValidate`(){
        val id = Random().nextInt()
        val cnpjGenerator = (1..14).joinToString("") { (0..9).random().toString() }
        val sender = buildCustomer(id = id, registroGoverno = cnpjGenerator)
        val transaction = buildTransaction(envia = id)

        every { repository.existsById(id) } returns true
        every { repository.findById(id).get() } returns sender

        val error = assertThrows<BadRequestException> { customerService.senderValidate(transaction) }

        assertEquals("Customer [${id}] não é cliente.", error.message)
        assertEquals("TO-002", error.errorCode)
        verify(exactly = 1) { repository.existsById(id) }
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun `should validate recipient and return sender`() {
        val id = Random().nextInt()
        val sender = buildCustomer(id = id)
        val transaction = buildTransaction(recebe = id)

        every { repository.existsById(id) } returns true
        every { repository.findById(id).get() } returns sender

        val compareSender = customerService.recipientValidate(transaction)

        assertEquals(sender, compareSender)
        verify { repository.existsById(id) }
        verify { repository.findById(id) }
    }

    @Test
    fun `should return exception in recipientValidate`(){
        val id = Random().nextInt()
        val sender = buildCustomer(id = id)
        val transaction = buildTransaction(recebe = id)

        every { repository.existsById(id) } returns false
        every { repository.findById(id).get() } returns sender

        val error = assertThrows<BadRequestException> { customerService.recipientValidate(transaction) }

        assertEquals("Customer [${id}] não existe", error.message)
        assertEquals("TO-003", error.errorCode)
        verify(exactly = 1) { repository.existsById(id) }
    }

    @Test
    fun `should validate the sender balance for transference`(){
        val sender = buildCustomer()
        val transaction = buildTransaction().valor

        val checkBalanceOperation = customerService.checkBalance(transaction, sender.saldo)

        assertTrue(checkBalanceOperation)
    }

    @Test
    fun `should return exception sender balance for transference`(){
        val sender = buildCustomer(saldo = 10.0f)
        val transaction = buildTransaction().valor

        val error = assertThrows<BadRequestException> { customerService.checkBalance(transaction, sender.saldo) }

        assertEquals("Saldo insuficiente para efetuar esta operação", error.message)
        assertEquals("TO-004", error.errorCode)
    }


}
