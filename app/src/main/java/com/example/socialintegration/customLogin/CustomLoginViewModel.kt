package com.example.socialintegration.customLogin

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.example.socialintegration.R
import com.facebook.*

class CustomLoginViewModel(
    val savedInstanceState: Bundle?,
    private val activity: FacebookCustomLoginActivity
) : ViewModel() {
    private var uiHelper: UiLifecycleHelper
    private val callback: Session.StatusCallback =
        Session.StatusCallback { session, state, exception ->
            onSessionChanged(session, state, exception)
        }
    private val _isOpened = MutableLiveData<Boolean>()
    val isOpened
        get() = _isOpened
    private val _userDetails = MutableLiveData<String>()
    val userDetails
        get() = _userDetails
    private val _buttonText = MutableLiveData<String>()
    val buttonText
        get() = _buttonText

    private fun onSessionChanged(session: Session?, state: SessionState?, exception: Exception?) {
        state?.let {
            if (it.isOpened) {
                _isOpened.value = true
                _buttonText.value = activity.resources.getString(R.string.logout)
                getUserDetails(session)
            } else if (it.isClosed) {
                _isOpened.value = false
            }
        }
    }

    init {
        _buttonText.value = activity.resources.getString(R.string.login)
        uiHelper = UiLifecycleHelper(activity, callback)
        uiHelper.onCreate(savedInstanceState)
    }

    fun loginClicked() {
        val session: Session? = Session.getActiveSession()

        if (_buttonText.value.equals("Logout")) {
            session?.closeAndClearTokenInformation()
            _buttonText.value = activity.resources.getString(R.string.login)
            _userDetails.value = ""
            return
        }

        session?.let {
            if (!it.isOpened && !it.isClosed) {
                session.openForRead(
                    Session.OpenRequest(activity)
                        .setPermissions(arrayListOf("public_profile", "email"))
                        .setCallback(callback)
                )
            } else {
                Session.openActiveSession(
                    activity,
                    true,
                    arrayListOf("public_profile", "email"),
                    callback
                )
            }
        }
    }

    private fun getUserDetails(session: Session?) {
        Request.newMeRequest(session) { user, _ ->
            if (user != null) {
                val name = user.name

                _userDetails.value = "You are logged in as $name"
            }
        }.executeAsync()
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