package com.codemachine.calculator.data

import java.math.BigInteger

interface Part {

    fun isEmpty() : Boolean

    fun clear()

    fun update(value: String)

    fun value() : BigInteger

    class Base() : Part {

        private var value: BigInteger = BigInteger.ZERO

        override fun isEmpty(): Boolean {
            return value == BigInteger.ZERO
        }

        override fun clear() {
            value = BigInteger.ZERO
        }

        override fun update(value: String) {
            if (isEmpty())
            this.value = BigInteger(value)
            else {
                val old = this.value.toString()
                val new = old + value
                this.value = BigInteger(value)
            }
        }

        override fun value(): BigInteger {
            return value
        }

    }
}