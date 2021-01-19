package com.example.socialintegration.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.facebook.*

class LoginViewModel(
    savedInstanceState: Bundle?,
    activity: FacebookLoginActivity
) : ViewModel() {
    var uiHelper: UiLifecycleHelper
    private val callback: Session.StatusCallback =
        Session.StatusCallback { session, state, exception ->
            onSessionChanged(session, state, exception)
        }
    private val _isOpened = MutableLiveData<Boolean>()
    val isOpened
        get() = _isOpened

    private fun onSessionChanged(session: Session?, state: SessionState?, exception: Exception?) {
        if (state!!.isOpened) {
            _isOpened.value = true
        } else if (state.isClosed) {
            _isOpened.value = false
        }
    }

    init {
        uiHelper = UiLifecycleHelper(activity, callback)
        uiHelper.onCreate(savedInstanceState)
    }

    fun resume() {
        uiHelper.onResume()
    }

    fun pause() {
        uiHelper.onPause()
    }

    fun destroy() {
        uiHelper.onDestroy()
    }

    fun savedInstance(bundle: Bundle) {
        uiHelper.onSaveInstanceState(bundle)
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        uiHelper.onActivityResult(requestCode, resultCode, data)
    }
}