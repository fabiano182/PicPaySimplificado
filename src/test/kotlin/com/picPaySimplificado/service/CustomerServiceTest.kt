package com.picPaySimplificado.service

import com.picPaySimplificado.model.CustomerModel
import com.picPaySimplificado.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*


@ExtendWith(MockKExtension::class)
@SpringBootTest
class CustomerServiceTest() {

    @Autowired
    lateinit var customerService: CustomerService

    val repository: CustomerRepository = mockk()

    @Test
    fun `should return all customers`() {
        val fakeCustomers = listOf(buildCustomer1())
        //given
        every { repository.findAll() } returns fakeCustomers
        //when
        val customers = customerService.getAll()

        //then
        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { repository.findAll() }
    }

    fun buildCustomer1(
        id: Int? = null,
        nomeCompleto: String = "customer name",
        registroGoverno: String = "12345678910",
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

    fun buildCustomer(
        id: Int? = null,
        nomeCompleto: String = "customer name",
        registroGoverno: String = (1..11).joinToString { (0..9).random().toString() },
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
