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
    private var tvRes: TextView? = null

    private val operators = mapOf<String, Char>(
        "MOD" to '\u0025',
        "DIV" to '\u00f7',
        "MUL" to '\u00d7',
        "SUB" to '\u2212',
        "ADD" to '\u002b'
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tv_calc)
        tvRes = findViewById(R.id.tv_res)
    }

    fun onDigitClicked(view: View) {
        tvInput?.append((view as Button).text)
    }

    fun onOperatorClicked(view: View) {
        tvInput?.text?.let {
            val inputStr = it.toString()
            if (inputStr.isEmpty() && view.id == R.id.btn_sub)
                tvInput?.append((view as Button).text)
            else if (inputStr.isNotEmpty())
                tvInput?.append((view as Button).text)
            else
                tvInput
        }
    }

    fun onPeriodClicked(view: View) {
        //Handle cases where more than one period should not be allowed in a single operand
    }

    fun calculate(view: View) {
        tvInput?.text?.let {
            try {
                val inputStr = it.toString()
                for (entry in operators.entries) {
                    if (inputStr.contains(entry.value)) {
                        var resString = calculateUtil(inputStr, entry.key).toString()
                        if(resString.substring(resString.length-2) == ".0") {
                            resString = resString.substring(0, resString.length-2)
                        }
                        tvInput?.text = resString
                        break
                    }
                }
            } catch (e: ArithmeticException) {
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
            if (inputStr.isNotEmpty()) {
                tvInput?.text = inputStr.substring(0, inputStr.length - 1)
            }
        }
    }

    private fun calculateUtil(inputStr: String, operator: String): Double {
        val operands = operators[operator]?.let { inputStr.split(it) }
        val operand1 = operands?.get(0)?.toDouble()
        val operand2 = operands?.get(1)?.toDouble()

        val operatorUni = operators[operator]
        var res: Double? = null;

        operand1?.let {
            operand2?.let {
                return when (operator) {
                    "MOD" -> operand1 % operand2
                    "DIV" -> operand1 / operand2
                    "MUL" -> operand1 * operand2
                    "SUB" -> operand1 - operand2
                    "ADD" -> operand1 + operand2
                    else -> 0.0
                }
            }
        }
        return 0.0
    }
}