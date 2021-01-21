package com.example.socialintegration.userInfo

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.facebook.*

class UserInfoViewModel(
    savedInstanceState: Bundle?,
    activity: FacebookUserInfoActivity
) : ViewModel() {
    var uiHelper: UiLifecycleHelper
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
    private val _userPhoto = MutableLiveData<String>()
    val userPhoto
        get() = _userPhoto

    private fun onSessionChanged(session: Session?, state: SessionState?, exception: Exception?) {
        state?.let {
            if (it.isOpened) {
                _isOpened.value = true
                getUserDetails(session)
            } else if (it.isClosed) {
                _isOpened.value = false
                _userDetails.value = ""
                _userPhoto.value = ""
            }
        }
    }

    init {
        uiHelper = UiLifecycleHelper(activity, callback)
        uiHelper.onCreate(savedInstanceState)
    }

    private fun getUserDetails(session: Session?) {
        Request.newMeRequest(session) { user, _ ->
            if (user != null) {
                val name = user.name
                val id = user.id

                _userPhoto.value = "https://graph.facebook.com/$id/picture?type=large&redirect=true"
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