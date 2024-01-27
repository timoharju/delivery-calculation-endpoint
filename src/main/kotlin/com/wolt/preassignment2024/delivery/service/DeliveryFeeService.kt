package com.wolt.preassignment2024.delivery.service

import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDto
import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDtoResponse
import org.springframework.stereotype.Service

@Service
class DeliveryFeeService {

    fun calculateDeliveryFee(data: DeliveryFeeDto): DeliveryFeeDtoResponse {
        val deliveryFee = 1
        return DeliveryFeeDtoResponse(deliveryFee = deliveryFee)
    }
}