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


class PaymentFragment : Fragment() {

    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_payment, container, false)

        val serviceNameEditText: EditText = inflatedView.findViewById(R.id.fragment_payment_service_name_et)
        val methodNameEditText: EditText = inflatedView.findViewById(R.id.fragment_payment_method_name_et)

        val chooseDateFromButton: Button = inflatedView.findViewById(R.id.fragment_payment_choose_date_from_btn)
        val chooseDateToButton: Button = inflatedView.findViewById(R.id.fragment_payment_choose_date_to_btn)

        val payButton: Button = inflatedView.findViewById(R.id.fragment_payment_pay_btn)

        val resultTextView: TextView = inflatedView.findViewById(R.id.fragment_payment_result_tv)



        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
