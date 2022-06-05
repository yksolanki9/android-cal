package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

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

    fun clearInput(view: View) {
        tvInput?.text = ""
    }
}