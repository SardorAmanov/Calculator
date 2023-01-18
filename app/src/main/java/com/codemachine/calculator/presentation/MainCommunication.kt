package com.codemachine.calculator.presentation

interface MainCommunication : Communication<String> {
    class Base : Communication.Abstract<String>(), MainCommunication
}