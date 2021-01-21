package com.example.socialintegration.shareContent

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.*

class ShareContentViewModel(
    val activity: WhatsAppShareContentActivity
) : ViewModel() {
    private val _isAppAvailable = MutableLiveData<Boolean>()
    val isAppAvailable
        get() = _isAppAvailable
    private val _message = MutableLiveData<String>()
    val message
        get() = _message

    fun onClick() {
        val packageManager = activity.packageManager

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            _isAppAvailable.value = true

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, _message.value)
            intent.type = "text/plain"
            intent.`package` = "com.whatsapp"
            activity.startActivity(intent)

        } catch (e: PackageManager.NameNotFoundException) {
            _isAppAvailable.value = false
        }
    }
}