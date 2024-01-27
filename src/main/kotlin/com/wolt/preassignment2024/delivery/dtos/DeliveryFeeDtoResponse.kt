package com.wolt.preassignment2024.delivery.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryFeeDtoResponse(
    @SerialName("delivery_fee")
    var deliveryFee: Int
)
