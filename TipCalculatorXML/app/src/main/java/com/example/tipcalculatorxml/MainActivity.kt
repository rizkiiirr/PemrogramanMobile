package com.example.tipcalculatorxml

import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculatorxml.databinding.ActivityMainBinding
import androidx.core.widget.addTextChangedListener
import java.util.Locale
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tipPercentage = listOf ("15%", "18%", "20%")
    private var selectedTipPercentage = 15.0
    private var roundUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTipPercentageInput()
        setupBillInput()
        setupRoundUpTip()
    }

    private fun setupTipPercentageInput() {
        //dropdown menu for tip options
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipPercentage)
        binding.tipPercentageInput.setAdapter(adapter)

        binding.tipPercentageInput.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                (view as? AutoCompleteTextView)?.showDropDown()
            }
        }

        //handle selection change in dropdown
        binding.tipPercentageInput.setOnItemClickListener { _, _, position, _ ->
            selectedTipPercentage = tipPercentage[position].dropLast(1).toDouble()
            val billAmount = binding.billAmountInput.text.toString().toDoubleOrNull() ?: 0.0
            updateCalculatedTip(billAmount)
        }
    }


            private fun setupBillInput(){
        //handle changes in bill amount input
        binding.billAmountInput.addTextChangedListener{editable ->
            val billAmount = editable?.toString()?.toDoubleOrNull() ?: 0.0
            updateCalculatedTip(billAmount)
        }
    }

    private fun setupRoundUpTip(){
        binding.roundUpSwitch.setOnCheckedChangeListener {_, isChecked ->
            roundUp = isChecked
            val billAmount = binding.billAmountInput.text.toString().toDoubleOrNull() ?: 0.0
            updateCalculatedTip(billAmount)
        }
    }

    private fun updateCalculatedTip(billAmount: Double){
        var tip = selectedTipPercentage / 100 * billAmount
        if (roundUp) {
            tip = ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        binding.calculatedTip.text = getString(R.string.tip_amount) + " " + formattedTip
    }
}