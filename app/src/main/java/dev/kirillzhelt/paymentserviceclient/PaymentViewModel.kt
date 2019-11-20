package dev.kirillzhelt.paymentserviceclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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

    fun onDateFromPicked(year: Int, month: Int, day: Int) {
        _dateFrom.value = LocalDate.of(year, month, day)
    }

    fun onDateToPicked(year: Int, month: Int, day: Int) {
        _dateTo.value = LocalDate.of(year, month, day)
    }

    companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    }
}
