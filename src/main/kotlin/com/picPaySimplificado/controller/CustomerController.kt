package com.picPaySimplificado.controller

import extensions.extensions.toCustomerModel
import com.picPaySimplificado.model.CustomerModel
import org.springframework.web.bind.annotation.*
import com.picPaySimplificado.controller.request.PostCustomerRequest
import com.picPaySimplificado.controller.request.PutCustomerRequest
import com.picPaySimplificado.service.CustomerService
import jakarta.transaction.Transactional
import jakarta.validation.Valid

@RestController
@Transactional
@RequestMapping("customer")
class CustomerController(
    val service: CustomerService
) {
    @GetMapping("teste")
    fun teste(): String {
        return "olá"
    }

    @GetMapping
    fun getAll() : List<CustomerModel> {
        return service.getAll()
    }

    @GetMapping("/{id}")
    fun filterCustomer(@PathVariable id: Int): CustomerModel {
        return service.findCustomer(id)
    }

    @PostMapping("/cadastrar")
    fun create(@RequestBody @Valid customer: PostCustomerRequest) {
        service.create(customer.toCustomerModel())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest){
        service.update(customer.toCustomerModel(customer))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int){
        service.delete(id)
    }
}