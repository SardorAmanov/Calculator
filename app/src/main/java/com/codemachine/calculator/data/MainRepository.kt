package com.codemachine.calculator.data

import java.math.BigInteger

interface MainRepository : Validation{

    fun updateLeftPart(value: String)

    fun updateRightPart(value: String)

    fun compareState(other: CalculationState) : Boolean

    fun changeOperation(operation: Operation)

    fun leftPart(): String

    fun rightPart(): String

    fun operation(): String

    fun calculate(): BigInteger

    class Base(private val left: Part, private val right: Part) : MainRepository {

        private var operation : Operation = Operation.None
        private var state : CalculationState = CalculationState.INITIAL

        override fun updateLeftPart(value: String) {
            left.update(value)
        }

        override fun updateRightPart(value: String) {
            right.update(value)
        }

        override fun compareState(other: CalculationState): Boolean {
            return this.state == other
        }

        override fun changeOperation(operation: Operation) {
            this.operation = operation
            this.state = CalculationState.OPERATION_PRESENT
        }

        override fun leftPart(): String {
            return left.value().toString()
        }

        override fun rightPart(): String {
            return right.value().toString()
        }

        override fun operation(): String {
            return operation.toString()
        }

        override fun calculate(): BigInteger {
            val calculate = operation.calculate(left, right)
            this.state = CalculationState.INITIAL
            left.clear()
            right.clear()
            return calculate
        }

        override fun isLeftPartEmpty(): Boolean {
            return left.isEmpty()
        }

        override fun isRightPartEmpty(): Boolean {
            return right.isEmpty()
        }

    }
}

interface Validation {
    fun isLeftPartEmpty() : Boolean

    fun isRightPartEmpty() : Boolean
}