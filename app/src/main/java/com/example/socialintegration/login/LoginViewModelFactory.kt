package com.example.socialintegration.login

import android.os.Bundle
import androidx.lifecycle.*

class LoginViewModelFactory(
    private val savedInstanceState: Bundle?,
    private val activity: FacebookLoginActivity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(savedInstanceState,activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}