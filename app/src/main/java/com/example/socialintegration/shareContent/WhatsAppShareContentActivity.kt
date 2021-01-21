package com.example.socialintegration.shareContent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialintegration.R
import com.example.socialintegration.databinding.ActivityWhatsappShareContentBinding

class WhatsAppShareContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWhatsappShareContentBinding
    private lateinit var viewModel: ShareContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_whatsapp_share_content)

        val factory = ShareContentViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(ShareContentViewModel::class.java)

        binding.shareContentBinding = viewModel
        binding.lifecycleOwner = this

        observers()
    }

    private fun observers() {
        viewModel.isAppAvailable.observe(this, { isAvailable ->
            if (!isAvailable) {
                Toast.makeText(this, R.string.app_not_installed, Toast.LENGTH_SHORT).show()
            }
        })
    }
}