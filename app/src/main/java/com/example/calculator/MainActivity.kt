package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tv_calc)
    }

    fun onDigitClicked(view: View) {
        tvInput?.append((view as Button).text)
    }

    fun onOperatorClicked(view: View) {
        tvInput?.text?.let {
            val inputStr = it.toString()
            if(inputStr.isEmpty() && view.id == R.id.btn_sub)
                tvInput?.append((view as Button).text)
            else if(inputStr.isNotEmpty())
                tvInput?.append((view as Button).text)
            else
                tvInput
        }
    }

    fun onPeriodClicked(view: View) {
        //Handle cases where more than one period should not be allowed in a single operand
    }

    // Need to create a mapping from operator unicode to actual operator instead of using unicode
    // everytime to compare strings
    fun calculate(view: View) {
        tvInput?.text?.let {
            try {
                val inputStr = it.toString()
                Log.d("TEST", inputStr)
                Log.d("CHAR", inputStr.contains('\u2212').toString())

                if(inputStr.contains('\u2212')) {
                    val operands = inputStr.split('\u2212')
                    Log.d("TEST", operands.toString())
                    tvInput?.text = (operands[0].toDouble() - operands[1].toDouble()).toString()
                }

            } catch(e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun clearInput(view: View) {
        tvInput?.text = ""
    }

    fun clearLastChar(view: View) {
        tvInput?.text?.let {
            val inputStr = it.toString()
            if(inputStr.isNotEmpty()) {
                tvInput?.text = inputStr.substring(0, inputStr.length - 1)
            }

        }
    }
}