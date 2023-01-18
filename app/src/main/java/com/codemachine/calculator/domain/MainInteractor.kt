package com.codemachine.calculator.domain

import com.codemachine.calculator.data.CalculationState
import com.codemachine.calculator.data.MainRepository
import com.codemachine.calculator.data.Operation
import com.codemachine.calculator.presentation.Communication
import com.codemachine.calculator.presentation.MainCommunication

interface MainInteractor {

    fun plus(): Result

    fun minus(): Result

    fun divide(): Result

    fun multiply(): Result

    fun calculate(): Result // =

    fun handle(value: String): Result

    class Base(private val repository: MainRepository) : MainInteractor {

        private val handleOperation: HandelOperation = HandelOperation.Base(repository)

        override fun plus(): Result {
            return handleOperation.handle(Operation.Plus)
        }

        override fun minus(): Result {
            return handleOperation.handle(Operation.Minus)
        }

        override fun divide(): Result {
            return handleOperation.handle(Operation.Divide)
        }

        override fun multiply(): Result {
            return handleOperation.handle(Operation.Multiply)
        }

        override fun calculate(): Result {
            if (repository.isLeftPartEmpty() || repository.isRightPartEmpty())
                return Result.Nothing
            return try {
                val value = repository.calculate().toString()
                Result.Success(value)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        override fun handle(value: String): Result {
            return if (repository.compareState(CalculationState.INITIAL) || repository.compareState(CalculationState.DONE)) {
                repository.updateLeftPart(value)
                Result.Success(repository.leftPart())
            } else {
                repository.updateRightPart(value)
                Result.Success(repository.leftPart() + repository.operation() + repository.rightPart())
            }
        }

        private interface HandelOperation {

            fun handle(operation: Operation): Result

            class Base(private val repository: MainRepository) : HandelOperation {

                override fun handle(operation: Operation): Result {
                    if  (repository.compareState(CalculationState.INITIAL)) {
                        return Result.Nothing
                    }

                    if (repository.isLeftPartEmpty()) {
                        return Result.Nothing
                    }

                    if (!repository.isRightPartEmpty()) {
                        return Result.Nothing
                    }

                    if (repository.compareState(CalculationState.LEFT_PART_DONE)) {
                        repository.changeOperation(operation)
                        return Result.Success(repository.leftPart() + repository.operation())
                    }

                    if (repository.compareState(CalculationState.DONE))
                        return Result.Nothing

                    repository.changeOperation(operation)
                    return Result.Success(repository.leftPart() + repository.operation())
                }
            }
        }
    }
}

sealed class Result {

    abstract fun map(
        success: Communication<String>,
        fail: Communication<String>
    )

    data class Success(private val value: String) : Result() {
        override fun map(success: Communication<String>, fail: Communication<String>) =
            success.map(value)
    }

    data class Error(private val e: Exception) : Result() {
        override fun map(success: Communication<String>, fail: Communication<String>) =
            fail.map(e.message ?: "error")
    }

    object Nothing : Result() {
        override fun map(success: Communication<String>, fail: Communication<String>) = Unit
    }
}





