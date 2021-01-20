package com.example.socialintegration.customLogin

import android.os.Bundle
import androidx.lifecycle.*

class CustomLoginViewModelFactory(
    private val savedInstanceState: Bundle?,
    private val activity: FacebookCustomLoginActivity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomLoginViewModel::class.java)) {
            return CustomLoginViewModel(savedInstanceState, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}