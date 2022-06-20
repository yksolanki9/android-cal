package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var tvMsg: TextView? = null

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
        tvMsg = findViewById(R.id.tv_msg)
    }

    fun onDigitClicked(view: View) {
        tvMsg?.text = ""
        tvInput?.append((view as Button).text)
    }

    fun onOperatorClicked(view: View) {
        tvMsg?.text = ""
        tvInput?.text?.let {
            val inputStr = it.toString()
            val opUni = (view as Button).text[0]
            if(inputStr.isNotEmpty()) {
                if (!isOperator(inputStr[inputStr.length-1]))
                    tvInput?.append(opUni.toString())
                else {
                    tvInput?.text = inputStr.substring(0, inputStr.length - 1) + opUni
                }
            }
        }
    }

    fun onPeriodClicked(view: View) {
        //Handle cases where more than one period should not be allowed in a single operand
        tvMsg?.text = ""
        tvInput?.text?.let {
            val inputStr = it.toString()
            if(inputStr.isEmpty() || inputStr[inputStr.length-1] != '.') {
                tvInput?.append(".")
            }
        }
    }

    fun calculate(view: View) {
        tvInput?.text?.let {
            try {
                val inputStr = it.toString()
                for (entry in operators.entries) {
                    if (inputStr.contains(entry.value)) {
                        var resString = calculateUtil(inputStr, entry.key).toString()
                        if(resString.length > 2 && resString.substring(resString.length-2) == ".0") {
                            resString = resString.substring(0, resString.length-2)
                        }
                        tvInput?.text = resString
                        break
                    }
                }
            } catch (e: Exception) {
                tvMsg?.text = "Invalid Operation"
            }
        }
    }

    fun clearInput(view: View) {
        tvMsg?.text = ""
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
        try {
            val operands = operators[operator]?.let { inputStr.split(it) }
            val operand1 = operands?.get(0)?.toDouble()
            val operand2 = operands?.get(1)?.toDouble()

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
        } catch(e: Exception) {
            throw Exception()
        }
    }

    private fun isOperator(operatorUni: Char): Boolean {
        for (opUni in operators.values) {
            if(operatorUni == opUni) {
                return true
            }
        }
        return false
    }
}