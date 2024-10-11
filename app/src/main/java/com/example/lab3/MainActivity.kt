package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab3.enums.Sign

class MainActivity : AppCompatActivity() {
    private lateinit var sequence: TextView
    private lateinit var curOp: Sign
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sequence = findViewById<TextView>(R.id.sequence)
        curOp = Sign.Plus
        result = findViewById<TextView>(R.id.result)
        setNumbersListeners()
        setOperatorListeners()
    }

    private fun numberButtonClickListener(numberString: String) {
        addToSequence(numberString)
    }

    private fun addToSequence(value: String) {
        if (!checkIsEnough()) {
            sequence.text = sequence.text.toString() + value
        } else {
            result.text = "Уже много операндов"
        }
    }

    private fun setOperatorListeners() {
        arrayOf(
            R.id.plus,
            R.id.minus,
            R.id.multiply,
            R.id.divide
        ).forEach {
            with(findViewById<TextView>(it)) {
                setOnClickListener {
                    if (!checkIsEnough()) {
                        curOp = when (this.text.toString()) {
                            "+" -> Sign.Plus
                            "-" -> Sign.Minus
                            "/" -> Sign.Divide
                            "*" -> Sign.Multiply
                            else -> Sign.Plus
                        }

                        addToSequence(' ' + this.text.toString() + ' ')
                    }
                }
            }
        }

        val getResult = findViewById<TextView>(R.id.get_result)
        getResult.setOnClickListener{
            calculate()
            sequence.text = ""
        }
    }

    private fun setNumbersListeners() {
        arrayOf(
            R.id.one,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine,
            R.id.zero,
        ).forEach {
            with(findViewById<TextView>(it)) {
                setOnClickListener {
                    numberButtonClickListener(this.text.toString())
                }
            }
        }
    }

    private fun calculate() {
        if (sequence.text.isEmpty()) {
            return
        }
        val operands = sequence.text.toString().split(' ')
        if (operands.size > 3) {
            result.text = "Недостаточно операндов"

        }
        try {
            val op1 = operands.first().toDouble()
            val op2 = operands.last().toDouble()
            when(operands[1]) {
                "+" -> result.text = Sign.Plus.doOp(op1, op2).toString()
                "-" -> result.text = Sign.Minus.doOp(op1, op2).toString()
                "*" -> result.text = Sign.Multiply.doOp(op1, op2).toString()
                "/" -> result.text = Sign.Divide.doOp(op1, op2).toString()
            }

            sequence.text = ""
        } catch (e: Exception) {
            result.text = "недопустимая операция"
            sequence.text = ""
        }

    }

    private fun checkIsEnough(): Boolean {
        val operands = sequence.text.toString().split(' ')
        return operands.size > 3
    }
}