package com.codemachine.calculator.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.codemachine.calculator.domain.MainInteractor
import com.codemachine.calculator.domain.Result

class MainViewModel(
    private val communication: MainCommunication,
    private val errorCommunication: MainCommunication,
    private val interactor: MainInteractor
    ) {

    fun observe(owner: LifecycleOwner, observer: Observer<String>) {
        communication.observe(owner, observer)
    }

    fun observeError(owner: LifecycleOwner, observer: Observer<String>) {
        errorCommunication.observe(owner, observer)
    }

    fun plus() {
        interactor.plus().map(communication, errorCommunication)
    }

    fun minus() {
        interactor.minus().map(communication, errorCommunication)
    }

    fun divide() {
        interactor.divide().map(communication, errorCommunication)
    }

    fun multiply() {
        interactor.multiply().map(communication, errorCommunication)
    }

    fun calculate() {
        interactor.calculate().map(communication, errorCommunication)
    }

    fun handle(value: String) {
        interactor.handle(value).map(communication, errorCommunication)
    }
}