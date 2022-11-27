package com.example.wakelockjavaapp

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SingletonClass {


    val _trigger = MutableStateFlow(false)
    val trigger : StateFlow<Boolean> = _trigger
}