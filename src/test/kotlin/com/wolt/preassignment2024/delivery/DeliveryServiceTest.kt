package com.wolt.preassignment2024.delivery

import com.wolt.preassignment2024.delivery.service.DeliveryFeeService
import com.wolt.preassignment2024.delivery.utils.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeliveryServiceTest {

    private lateinit var deliveryFeeService: DeliveryFeeService

    @BeforeEach
    fun setup() {
        deliveryFeeService = DeliveryFeeService()
    }

    @Test
    fun `calculateMinCartValueFee returns correct fee for cart values below minimum`() {
        val cartValue = MIN_CART_VALUE - 200
        val expectedFee = MIN_CART_VALUE - cartValue
        val actualFee = deliveryFeeService.calculateMinCartValueFee(cartValue)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateMinCartValueFee returns zero for cart values equal or above minimum`() {
        val cartValue = MIN_CART_VALUE
        val expectedFee = 0
        val actualFee = deliveryFeeService.calculateMinCartValueFee(cartValue)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateDistanceFee returns correct fee for distances above threshold`() {
        val deliveryDistance = DEFAULT_DISTANCE_THRESHOLD + 1000
        val expectedFee = EXTRA_DELIVERY_FEE * 2
        val actualFee = deliveryFeeService.calculateDistanceFee(deliveryDistance)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateDistanceFee returns zero for distances within threshold`() {
        val deliveryDistance = DEFAULT_DISTANCE_THRESHOLD
        val expectedFee = 0
        val actualFee = deliveryFeeService.calculateDistanceFee(deliveryDistance)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateBulkOrderFee returns correct fee for 5-12 items`() {
        val numberOfItems = LOWER_BULK_ORDER_THRESHOLD + 2 // 7
        val expectedFee = LOWER_BULK_ORDER_FEE * 3
        val actualFee = deliveryFeeService.calculateBulkOrderFee(numberOfItems)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateBulkOrderFee returns higher bulk fee for very large orders 13 or more items`() {
        val numberOfItems = HIGHER_BULK_ORDER_THRESHOLD + 1 // 13
        val expectedFee = (LOWER_BULK_ORDER_FEE * (numberOfItems - LOWER_BULK_ORDER_THRESHOLD + 1)) + HIGHER_BULK_ORDER_FEE
        val actualFee = deliveryFeeService.calculateBulkOrderFee(numberOfItems)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateBulkItemFee returns zero for items count below 5`() {
        val service = DeliveryFeeService()
        val numberOfItems = LOWER_BULK_ORDER_THRESHOLD - 1 // 4
        val expectedFee = 0
        val actualFee = service.calculateBulkOrderFee(numberOfItems)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateRushHourFee applies additional fee during rush hours`() {
        val date = "2024-01-19T16:00:00Z" // Rush hour. Friday and between 15 and 19
        val deliveryFee = 1000
        val expectedFee = (deliveryFee * RUSH_HOUR_RATE - deliveryFee).toInt()
        val actualFee = deliveryFeeService.calculateRushHourFee(date, deliveryFee)

        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `calculateRushHourFee applies no additional fee outside rush hours`() {
        val date = "2024-01-19T20:00:00Z" // Friday, but after rush hour
        val deliveryFee = 1000
        val expectedFee = 0
        val actualFee = deliveryFeeService.calculateRushHourFee(date, deliveryFee)

        assertEquals(expectedFee, actualFee)
    }
}