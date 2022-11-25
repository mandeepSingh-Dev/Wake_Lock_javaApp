package com.example.wakelockjavaapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class Myviewmodel : ViewModel() {

    val _mutablestateFlow  = MutableStateFlow("Hello")
    val stateFlow : StateFlow<String> = _mutablestateFlow

    val _mutablesharedFlow = MutableSharedFlow<String>()
    val sharedFlow : SharedFlow<String> = _mutablesharedFlow



    override fun onCleared() {
        super.onCleared()
    }
}