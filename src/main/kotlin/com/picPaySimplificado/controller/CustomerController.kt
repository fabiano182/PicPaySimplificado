package com.picPaySimplificado.controller

import extensions.extensions.toCustomerModel
import com.picPaySimplificado.model.CustomerModel
import org.springframework.web.bind.annotation.*
import com.picPaySimplificado.request.PostCustomerRequest
import com.picPaySimplificado.request.PutCustomerRequest
import com.picPaySimplificado.service.CustomerService

@RestController
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
    fun filterCustomer(@RequestBody id: Int): CustomerModel {
        return service.findCustomer(id)
    }

    @PostMapping("/cadastrar")
    fun create(@RequestBody customer: PostCustomerRequest) {
        service.create(customer.toCustomerModel())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest){
        service.update(customer.toCustomerModel(customer))
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Int){
        service.delete(id)
    }
}