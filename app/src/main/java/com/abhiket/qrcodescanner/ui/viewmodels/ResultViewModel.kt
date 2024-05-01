package com.abhiket.qrcodescanner.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
    private val _result = mutableStateOf("")
    val result: State<String> get() = _result

    fun setResultValue(value: String) {
        _result.value = value
    }
}
