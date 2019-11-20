package dev.kirillzhelt.paymentserviceclient

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseData(

    @JsonProperty("token")
    val token: String? = null,

    @JsonProperty("status_code")
    val statusCode: Int,

    @JsonProperty("status_message")
    val statusMessage: String? = null

)