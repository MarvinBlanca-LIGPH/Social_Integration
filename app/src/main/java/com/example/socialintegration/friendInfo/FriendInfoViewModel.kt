package com.example.socialintegration.friendInfo

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.example.socialintegration.R
import com.facebook.*

class FriendInfoViewModel(
    savedInstanceState: Bundle?,
    private val activity: FacebookFriendInfoActivity
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
    private val _friendDetails = MutableLiveData<String>()
    val friendDetails
        get() = _friendDetails
    private val _userPhoto = MutableLiveData<String>()
    val userPhoto
        get() = _userPhoto

    private fun onSessionChanged(session: Session?, state: SessionState?, exception: Exception?) {
        state?.let {
            if (it.isOpened) {
                _isOpened.value = true
                getUserDetails(session)
                getFriendDetails(session)
            } else if (it.isClosed) {
                _isOpened.value = false
                _userDetails.value = ""
                _userPhoto.value = ""
                _friendDetails.value = ""
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

    private fun getFriendDetails(session: Session?) {
        val request = Request.newMyFriendsRequest(session) { graphUsers, _ ->
            graphUsers.forEach {
                val id = it.id
                val name = it.name

                _friendDetails.value = "id = $id name = $name"
            }
        }

        if (request.graphObject == null)
            _friendDetails.value =
                activity.resources.getString(R.string.not_subscribed)
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