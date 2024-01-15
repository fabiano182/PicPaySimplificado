package com.picPaySimplificado.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.picPaySimplificado.controller.request.PostCustomerRequest
import com.picPaySimplificado.helper.buildCustomer
import com.picPaySimplificado.repository.CustomerRepository
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
class CustomerControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `Should return ola`() {
        mockMvc.perform(get("/customer/teste"))
            .andExpect(status().isOk)
    }

    @Test
    fun `Should return all customers`(){
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())
        mockMvc.perform(get("/customer"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].nomeCompleto").value(customer1.nomeCompleto))
            .andExpect(jsonPath("$[0].registroGoverno").value(customer1.registroGoverno))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].senha").value(customer1.senha))
            .andExpect(jsonPath("$[0].saldo").value(customer1.saldo))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))

            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].nomeCompleto").value(customer2.nomeCompleto))
            .andExpect(jsonPath("$[1].registroGoverno").value(customer2.registroGoverno))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].senha").value(customer2.senha))
            .andExpect(jsonPath("$[1].saldo").value(customer2.saldo))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }

    @Test
    fun `should get user by id`(){
        val customer = customerRepository.save(buildCustomer())
        mockMvc.perform(get("/customer/${customer.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.nomeCompleto").value(customer.nomeCompleto))
            .andExpect(jsonPath("$.registroGoverno").value(customer.registroGoverno))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.senha").value(customer.senha))
            .andExpect(jsonPath("$.saldo").value(customer.saldo))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should create customer`(){
        val request = PostCustomerRequest(
            "fake name",
            "12345678910",
            "fakecustomer@gmail.com",
            "123455",
            500.0F
        )

        mockMvc.perform(post("/customer/cadastrar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val customer = customerRepository.findAll().toList()
        assertEquals(1, customer.size)
    }

}