package com.example.socialintegration.userInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.socialintegration.R
import com.example.socialintegration.databinding.ActivityFacebookUserInfoBinding

class FacebookUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFacebookUserInfoBinding
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_user_info)

        val factory = UserInfoViewModelFactory(savedInstanceState, this)
        viewModel = ViewModelProvider(this, factory).get(UserInfoViewModel::class.java)

        binding.userInfo = viewModel
        binding.lifecycleOwner = this

        binding.facebookLoginButton.setReadPermissions(
            arrayListOf(
                "public_profile",
                "email",
            )
        )

        observers()
    }

    private fun observers() {
        viewModel.isOpened.observe(this, { isOpened ->
            if (isOpened) {
                Toast.makeText(this, "Session started", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Session closed", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.userPhoto.observe(this, { photo ->
            if (photo.isNotEmpty()) {
                Glide.with(binding.imageView)
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