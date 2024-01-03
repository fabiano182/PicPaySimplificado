package com.picPaySimplificado.service

import com.picPaySimplificado.exception.BadRequestException
import com.picPaySimplificado.exception.NotFoundException
import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NO_CONTENT
import java.util.*


@ExtendWith(MockKExtension::class)
@SpringBootTest
class CustomerServiceTest() {

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
        every { repository.save(fakeCustomer) } returns fakeCustomer

        //when
        customerService.update(fakeCustomer)
        //then
        verify(exactly = 1) { repository.existsById(id) }
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
    fun `should create customers`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        //given

        //when

        //then

    }


    fun buildCustomer(
        id: Int? = null,
        nomeCompleto: String = "customer name",
        registroGoverno: String = (1..11).joinToString("") { (0..9).random().toString() },
        email: String = "${UUID.randomUUID()}@email.com",
        senha: String = "password",
        saldo: Float = 500.0F
    ) = CustomerModel(
        id = id,
        nomeCompleto = nomeCompleto,
        registroGoverno = registroGoverno,
        email = email,
        senha = senha,
        saldo = saldo
    )
}
