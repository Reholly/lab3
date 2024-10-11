package com.example.lab3.enums

enum class Sign(val value: String) {
    Plus("+"){
        override fun doOp(first: Double, second: Double): Double {
            return first + second
        }
    },
    Minus("-"){
        override fun doOp(first: Double, second: Double): Double {
            return first - second
        }
    },
    Multiply("*"){
        override fun doOp(first: Double, second: Double): Double {
            return first * second
        }
    },
    Divide("/")
    {
        override fun doOp(first: Double, second: Double): Double {
            if (second == 0.0) {
                return 0.0
            }

            return first / second
        }
    };

    abstract fun doOp(first: Double, second: Double): Double
}
