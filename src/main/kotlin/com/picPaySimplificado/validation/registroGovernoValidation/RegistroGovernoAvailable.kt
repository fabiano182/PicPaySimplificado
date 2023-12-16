package com.picPaySimplificado.validation.registroGovernoValidation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Constraint(validatedBy = [RegistroGovernoAvailableValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class RegistroGovernoAvailable(
    val message: String = "registroGoverno já em uso",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

