package com.example.socialintegration.customLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.socialintegration.R
import com.example.socialintegration.databinding.ActivityFacebookCustomLoginBinding

class FacebookCustomLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFacebookCustomLoginBinding
    private lateinit var viewModel: CustomLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_custom_login)

        val factory = CustomLoginViewModelFactory(savedInstanceState, this)
        viewModel = ViewModelProvider(this, factory).get(CustomLoginViewModel::class.java)

        binding.customLoginBinding = viewModel
        binding.lifecycleOwner = this

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