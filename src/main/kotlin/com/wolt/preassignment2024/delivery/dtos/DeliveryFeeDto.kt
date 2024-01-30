package com.wolt.preassignment2024.delivery.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryFeeDto(
    @SerialName("cart_value")
    var cartValue: Int,
    @SerialName("delivery_distance")
    var deliveryDistance: Int,
    @SerialName("number_of_items")
    var numberOfItems: Int,
    @SerialName("time")
    var time: String
)