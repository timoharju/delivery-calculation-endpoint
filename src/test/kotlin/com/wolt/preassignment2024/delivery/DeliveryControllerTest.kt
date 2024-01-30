package com.wolt.preassignment2024.delivery

import com.wolt.preassignment2024.delivery.dtos.DeliveryFeeDto
import com.wolt.preassignment2024.delivery.service.DeliveryFeeService
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryFeeControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var deliveryFeeService: DeliveryFeeService

    private val json = Json

    private val rushHourPayload = DeliveryFeeDto(790, 2235, 14, "2024-01-26T16:00:59Z")
    private val nonRushHourPayload = DeliveryFeeDto(790, 2235, 4, "2024-01-15T13:00:00Z")

    @Test
    fun `calculate delivery fee with rush hour and return the expected delivery fee and HTTP status 200`() {
        mockMvc.perform(
            post("/delivery/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.encodeToString(DeliveryFeeDto.serializer(), rushHourPayload))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"delivery_fee\": 1500}"))
    }

    @Test
    fun `calculate delivery fee without rush hour and return the expected delivery fee and HTTP status 200`() {
        mockMvc.perform(
            post("/delivery/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.encodeToString(DeliveryFeeDto.serializer(), nonRushHourPayload))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"delivery_fee\":710}"))
    }

}
