package dev.kirillzhelt.paymentserviceclient

import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentInfo(
    @JsonProperty("date_from")
    val dateFrom: String,

    @JsonProperty("date_to")
    val dateTo: String
)