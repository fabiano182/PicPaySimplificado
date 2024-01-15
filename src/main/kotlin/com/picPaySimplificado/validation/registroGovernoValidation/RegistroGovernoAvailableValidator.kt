package com.picPaySimplificado.validation.registroGovernoValidation


import com.picPaySimplificado.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class RegistroGovernoAvailableValidator(private var customerService: CustomerService): ConstraintValidator<RegistroGovernoAvailable, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()) {
            return false
        }
        return customerService.registroGovernoAvailable(value)
    }
}

