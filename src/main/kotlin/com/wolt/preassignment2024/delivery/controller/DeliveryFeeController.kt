package com.wolt.preassignment2024.delivery.controller;

import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDto;
import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDtoResponse;
import com.wolt.preassignment2024.delivery.service.DeliveryFeeService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("delivery")
class DeliveryFeeController {

    @Autowired
    lateinit var deliveryFeeService: DeliveryFeeService

    @PostMapping("calculate", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun calculateDeliveryFee(@RequestBody deliveryPayload: DeliveryFeeDto): DeliveryFeeDtoResponse {
        return deliveryFeeService.calculateTotalDeliveryFee(deliveryPayload)
    }
}