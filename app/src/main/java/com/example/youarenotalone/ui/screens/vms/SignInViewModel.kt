package com.example.youarenotalone.ui.screens.vms

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    var messageLogin = mutableStateOf("")
    var messagePassword = mutableStateOf("")
}