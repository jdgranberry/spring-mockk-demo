package com.tgt.springmockkdemo

import com.tgt.springmockkdemo.controller.Server
import com.tgt.springmockkdemo.service.OrderService
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerTest {
    val mockOrderService: OrderService = mockk()

    val server = Server(mockOrderService)

    @AfterEach
    fun clearMocks() = clearAllMocks()

    @Test
    fun `when an order is placed, and the orderService responds true, Server responds with HttpStatus_OK`() {
        val order = "DUCK_AU_POIVRE"

        // tableNumber doesn't factor into the result of the method, so we use the any() matcher
        every { mockOrderService.placeOrder(order, any()) } returns true

        val result = server.order(order, 42)

        assertEquals(ResponseEntity<HttpStatus>(HttpStatus.OK), result)
        verify(exactly = 1) { mockOrderService.placeOrder(any(), any()) }
        confirmVerified(mockOrderService)
    }


    /* Without clearing mocks, this test will fail since the mock has data from the previous test. */
    @Test
    fun `when an order is placed, and the orderService responds false, Server responds with HttpStatus_NOT_FOUND`() {
        val order = "MEAT_LOAF"

        every { mockOrderService.placeOrder(order, any()) } returns false

        val result = server.order(order, 42)

        assertEquals(ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND), result)
        verify(exactly = 1) { mockOrderService.placeOrder(any(), any()) }
        confirmVerified(mockOrderService)
    }
}