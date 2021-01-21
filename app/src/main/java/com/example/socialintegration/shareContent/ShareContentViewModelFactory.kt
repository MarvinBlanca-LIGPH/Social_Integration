package com.example.socialintegration.shareContent

import androidx.lifecycle.*

class ShareContentViewModelFactory(
    private val activity: WhatsAppShareContentActivity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShareContentViewModel::class.java)) {
            return ShareContentViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}