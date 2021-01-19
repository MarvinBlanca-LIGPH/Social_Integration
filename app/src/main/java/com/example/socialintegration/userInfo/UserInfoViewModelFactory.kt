package com.example.socialintegration.userInfo

import android.os.Bundle
import androidx.lifecycle.*

class UserInfoViewModelFactory(
    private val savedInstanceState: Bundle?,
    private val activity: FacebookUserInfoActivity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(savedInstanceState, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}