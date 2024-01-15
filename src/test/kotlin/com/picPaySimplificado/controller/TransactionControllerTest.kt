package com.picPaySimplificado.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.picPaySimplificado.controller.request.TransactionRequest
import com.picPaySimplificado.helper.buildCustomer
import com.picPaySimplificado.helper.buildTransaction
import com.picPaySimplificado.repository.CustomerRepository
import com.picPaySimplificado.repository.TransactionRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
class TransactionControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var transactionRepository: TransactionRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setupTransaction() = transactionRepository.deleteAll()

    @BeforeEach
    fun setupCustomer() = customerRepository.deleteAll()

    @BeforeEach
    fun tearDownCustomer() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown()= transactionRepository.deleteAll()

    @Test
    fun `Should return all transference History`(){
        val transference1 = transactionRepository.save(buildTransaction())
        val transference2 = transactionRepository.save(buildTransaction())
        mockMvc.perform(get("/transference"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].envia").value(transference1.envia))
            .andExpect(jsonPath("$[0].recebe").value(transference1.recebe))
            .andExpect(jsonPath("$[0].valor").value(transference1.valor))

            .andExpect(jsonPath("$[1].envia").value(transference2.envia))
            .andExpect(jsonPath("$[1].recebe").value(transference2.recebe))
            .andExpect(jsonPath("$[1].valor").value(transference2.valor))

    }

    @Test
    fun `should get transference by id`(){
        val transaction = transactionRepository.save(buildTransaction())
        mockMvc.perform(get("/transference/${transaction.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(transaction.id))
            .andExpect(jsonPath("$.envia").value(transaction.envia))
            .andExpect(jsonPath("$.recebe").value(transaction.recebe))
            .andExpect(jsonPath("$.valor").value(transaction.valor))
    }

    @Test
    fun `should make transference`(){
        val fakeCustomer1 = customerRepository.save(buildCustomer())
        val fakeCustomer2 = customerRepository.save(buildCustomer())
        val request = TransactionRequest(
            fakeCustomer1.id!!,
            fakeCustomer2.id!!,
            50.0F,
        )

        mockMvc.perform(post("/transference")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val transference = transactionRepository.findAll().toList()
        assertEquals(1, transference.size)
    }
}