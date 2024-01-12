package com.picPaySimplificado.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.picPaySimplificado.helper.buildCustomer
import com.picPaySimplificado.repository.CustomerRepository
import com.picPaySimplificado.service.CustomerServiceTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
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
            .andExpect(jsonPath("$[0].status").value(customer2.status.name))

            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].nomeCompleto").value(customer2.nomeCompleto))
            .andExpect(jsonPath("$[1].registroGoverno").value(customer2.registroGoverno))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].senha").value(customer2.senha))
            .andExpect(jsonPath("$[1].saldo").value(customer2.saldo))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))

    }
}