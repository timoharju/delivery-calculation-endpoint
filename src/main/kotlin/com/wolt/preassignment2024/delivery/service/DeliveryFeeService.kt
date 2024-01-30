package com.wolt.preassignment2024.delivery.service

import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDto
import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDtoResponse
import com.wolt.preassignment2024.delivery.utils.*
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import kotlin.math.ceil

@Service
class DeliveryFeeService {

    fun calculateTotalDeliveryFee(payload: DeliveryFeeDto): DeliveryFeeDtoResponse {
        // Initial default delivery fee
        var deliveryFee: Int = DEFAULT_DELIVERY_FEE

        deliveryFee += calculateMinCartValueFee(payload.cartValue)
        deliveryFee += calculateDistanceFee(payload.deliveryDistance)
        deliveryFee += calculateBulkOrderFee(payload.numberOfItems)
        deliveryFee += calculateRushHourFee(payload.time, deliveryFee)

        return when {
            deliveryFee >= HIGH_VALUE_ORDER -> DeliveryFeeDtoResponse(deliveryFee = 0)
            deliveryFee >= MAX_DELIVERY_FEE -> DeliveryFeeDtoResponse(deliveryFee = MAX_DELIVERY_FEE)
            else -> DeliveryFeeDtoResponse(deliveryFee = deliveryFee)
        }
    }

    fun calculateMinCartValueFee(cartValue: Int): Int {
        if (MIN_CART_VALUE > cartValue) {
            return MIN_CART_VALUE - cartValue
        }
        return 0
    }

    fun calculateDistanceFee(deliveryDistance: Int): Int {
        if (deliveryDistance > DEFAULT_DISTANCE_THRESHOLD) {
            // Calculate how many times we have gone past 500-meter threshold after initial 1000-meter threshold
            // Use Match.ceil rounds value up to the next positive integer to get the times the 500-meter threshold has been exceeded
            val timesExceeded: Int =
                ceil((deliveryDistance - DEFAULT_DISTANCE_THRESHOLD).toDouble() / ADDITIONAL_DISTANCE_THRESHOLD).toInt()
            return timesExceeded * EXTRA_DELIVERY_FEE
        }
        return 0
    }

    fun calculateBulkOrderFee(numberOfItems: Int): Int {
        if (numberOfItems >= LOWER_BULK_ORDER_THRESHOLD) {
            var bulkOrderFee: Int
            // Plus one to account the 5th item in the cart
            val lowerThresholdExceeded = numberOfItems - LOWER_BULK_ORDER_THRESHOLD + 1
            bulkOrderFee = lowerThresholdExceeded * LOWER_BULK_ORDER_FEE

            if (numberOfItems > HIGHER_BULK_ORDER_THRESHOLD) {
                bulkOrderFee += HIGHER_BULK_ORDER_FEE
            }

            return bulkOrderFee;
        }
        return 0
    }

    fun calculateRushHourFee(date: String, deliveryFee: Int): Int {
        if (TimeUtils.getCurrentWeekday(date) == DayOfWeek.FRIDAY && TimeUtils.isDateBetweenHours(date, 15, 19)) {
            // Round up to an integer
            return (deliveryFee * RUSH_HOUR_RATE - deliveryFee).toInt()
        }
        return 0
    }
}