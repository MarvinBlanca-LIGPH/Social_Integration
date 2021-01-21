package com.example.socialintegration.friendInfo

import android.os.Bundle
import androidx.lifecycle.*

class FriendInfoViewModelFactory(
    private val savedInstanceState: Bundle?,
    private val activity: FacebookFriendInfoActivity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendInfoViewModel::class.java)) {
            return FriendInfoViewModel(savedInstanceState, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}