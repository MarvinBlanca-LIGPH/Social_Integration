package com.example.socialintegration.friendInfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.socialintegration.R
import com.example.socialintegration.databinding.ActivityFacebookFriendInfoBinding

class FacebookFriendInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFacebookFriendInfoBinding
    private lateinit var viewModel: FriendInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_friend_info)

        val factory = FriendInfoViewModelFactory(savedInstanceState, this)
        viewModel = ViewModelProvider(this, factory).get(FriendInfoViewModel::class.java)

        binding.friendInfoBinding = viewModel
        binding.lifecycleOwner = this
        binding.facebookLoginButton.setReadPermissions(
            arrayListOf(
                "public_profile",
                "email"
            )
        )

        observers()
    }

    private fun observers() {
        viewModel.isOpened.observe(this, { isOpened ->
            if (isOpened) {
                Toast.makeText(this, R.string.session_started, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.session_closed, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.userPhoto.observe(this, { photo ->
            if (photo.isNotEmpty()) {
                binding.imageView.visibility = View.VISIBLE
                Glide.with(this)
                    .load(viewModel.userPhoto.value)
                    .into(binding.imageView)
            } else {
                binding.imageView.visibility = View.INVISIBLE
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.savedInstance(outState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.activityResult(requestCode, resultCode, data)
    }
}