package com.picPaySimplificado.service

import com.picPaySimplificado.client.ConfirmTransferApproval
import com.picPaySimplificado.client.PostConfirmationTransactionByEmail
import com.picPaySimplificado.exception.NotFoundException
import com.picPaySimplificado.model.TransactionModel
import com.picPaySimplificado.repository.CustomerRepository
import com.picPaySimplificado.repository.TransactionRepository
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
import java.util.*


@ExtendWith(MockKExtension::class)
@SpringBootTest
class TransactionServiceTest() {

    @MockK
    private lateinit var customerService: CustomerService

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var confirmTransferApproval: ConfirmTransferApproval

    @MockK
    private lateinit var postConfirmationTransactionByEmail: PostConfirmationTransactionByEmail

    @MockK
    private lateinit var repository: TransactionRepository

    @InjectMockKs
    lateinit var transactionService: TransactionService

    @Test
    fun `should return all transactions`() {
        val fakeTransaction = listOf(buildTransaction())

        every { repository.findAll() } returns fakeTransaction

        val transaction = transactionService.getAll()

        assertEquals(fakeTransaction, transaction)
        verify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun `should return the specific transaction`() {
        val id = Random().nextInt()
        val fakeTransaction = buildTransaction(id = id)


        every { repository.findById(id) } returns Optional.of(fakeTransaction)

        val transaction = transactionService.getTransaction(id)

        assertEquals(fakeTransaction, transaction)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun `should return the specific exception transaction`() {
        val id = Random().nextInt()

        every { repository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> { transactionService.getTransaction(id) }

        assertEquals("Transação [${id}] não existe", error.message)
        assertEquals("TO-001", error.errorCode)

        verify(exactly = 1) { repository.findById(id) }
    }
    //Transference

    fun buildTransaction(
        id: Int? = null,
        envia: Int = Random().nextInt(),
        recebe: Int = Random().nextInt(),
        valor: Float = 50.0F
    ) = TransactionModel(
        ID = id,
        envia = envia,
        recebe = recebe,
        valor = valor
    )

}