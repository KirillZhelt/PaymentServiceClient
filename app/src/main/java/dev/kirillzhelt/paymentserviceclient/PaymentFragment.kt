package dev.kirillzhelt.paymentserviceclient

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog


class PaymentFragment : Fragment() {

    private lateinit var viewModel: PaymentViewModel

    private lateinit var serviceNameEditText: EditText
    private lateinit var methodNameEditText: EditText

    private lateinit var chooseDateFromButton: Button
    private lateinit var dateFromTextView: TextView
    private lateinit var dateFromPickerDialog: DatePickerDialog

    private lateinit var chooseDateToButton: Button
    private lateinit var dateToTextView: TextView
    private lateinit var dateToPickerDialog: DatePickerDialog

    private lateinit var payButton: Button

    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_payment, container, false)

        serviceNameEditText = inflatedView.findViewById(R.id.fragment_payment_service_name_et)
        methodNameEditText = inflatedView.findViewById(R.id.fragment_payment_method_name_et)

        chooseDateFromButton = inflatedView.findViewById(R.id.fragment_payment_choose_date_from_btn)
        chooseDateToButton = inflatedView.findViewById(R.id.fragment_payment_choose_date_to_btn)

        dateFromTextView = inflatedView.findViewById(R.id.fragment_payment_date_from_tv)
        dateToTextView = inflatedView.findViewById(R.id.fragment_payment_date_to_tv)

        payButton = inflatedView.findViewById(R.id.fragment_payment_pay_btn)

        resultTextView = inflatedView.findViewById(R.id.fragment_payment_result_tv)

        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)

        dateFromPickerDialog = createDatePickerDialog(viewModel::onDateFromPicked)
        dateToPickerDialog = createDatePickerDialog(viewModel::onDateToPicked)

        viewModel.dateFrom.observe(this, Observer { date ->
            dateFromTextView.text = date
        })

        viewModel.dateTo.observe(this, Observer { date ->
            dateToTextView.text = date
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseDateFromButton.setOnClickListener {
            dateFromPickerDialog.show(fragmentManager!!, "DateFromPickerDialog")
        }

        chooseDateToButton.setOnClickListener {
            dateToPickerDialog.show(fragmentManager!!, "DateToPickerDialog")
        }
    }

    private fun createDatePickerDialog(onDatePicked: (Int, Int, Int) -> Unit): DatePickerDialog {
        return DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            onDatePicked(year, monthOfYear, dayOfMonth)
        }.apply {
            isThemeDark = false
            showYearPickerFirst(false)
        }
    }
}
