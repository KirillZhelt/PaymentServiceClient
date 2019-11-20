package dev.kirillzhelt.paymentserviceclient

import androidx.lifecycle.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PaymentViewModel : ViewModel() {

    private val _dateFrom = MutableLiveData<LocalDate>()
    val dateFrom: LiveData<String> = Transformations.map(_dateFrom) {
        it.format(FORMATTER)
    }

    private val _dateTo = MutableLiveData<LocalDate>()
    val dateTo: LiveData<String> = Transformations.map(_dateTo) {
        it.format(FORMATTER)
    }

    private val _paymentResult = MutableLiveData<String>()
    val paymentResult: LiveData<String> = _paymentResult

    private val okHttpClient = OkHttpClient()
    private val jsonObjectMapper = jacksonObjectMapper()

    fun onDateFromPicked(year: Int, month: Int, day: Int) {
        _dateFrom.value = LocalDate.of(year, month + 1, day)
    }

    fun onDateToPicked(year: Int, month: Int, day: Int) {
        _dateTo.value = LocalDate.of(year, month + 1, day)
    }

    fun pay(serviceName: String, methodName: String, invalidDataMessageString: String) {
        if (serviceName.isNotEmpty() && methodName.isNotEmpty() && _dateFrom.value?.isBefore(_dateTo.value) == true) {
            _paymentResult.value = "Sending request"

            val serviceUrl = "$PAYMENT_SERVICE_URL/$serviceName/$methodName"

            val paymentInfo = PaymentInfo(_dateFrom.value!!.format(FORMATTER_FOR_JSON),
                _dateTo.value!!.format(FORMATTER_FOR_JSON))

            val jsonString = jsonObjectMapper.writeValueAsString(paymentInfo)
            val requestBody = jsonString.toRequestBody(JSON_MEDIA_TYPE)

            val request = Request.Builder()
                .url(serviceUrl)
                .post(requestBody)
                .build()

            viewModelScope.launch(Dispatchers.IO) {
                val responseJsonString = okHttpClient.newCall(request).execute().body?.string()

                val message: String = when {
                    responseJsonString != null -> {
                        val responseData: ResponseData = jsonObjectMapper.readValue(responseJsonString)

                        when (responseData.statusCode) {
                            201 -> responseData.token!!.toString()
                            404 -> responseData.statusMessage!!
                            else -> "Bad Request"
                        }
                    }
                    else -> "Bad Request"
                }

                withContext(Dispatchers.Main) {
                    _paymentResult.value = message
                }
            }
        } else {
            _paymentResult.value = invalidDataMessageString
        }
    }

    companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

        const val PAYMENT_SERVICE_URL = "https://cryptic-beach-05943.herokuapp.com/token"
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        val FORMATTER_FOR_JSON: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    }
}
