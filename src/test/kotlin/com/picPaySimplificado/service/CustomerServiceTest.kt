package com.picPaySimplificado.service

import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
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
