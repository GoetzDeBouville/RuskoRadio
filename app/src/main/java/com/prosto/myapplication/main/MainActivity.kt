package com.prosto.myapplication.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import com.prosto.myapplication.R
import com.prosto.myapplication.core.ui.BaseActivity
import com.prosto.myapplication.core.utils.applyBlurEffect
import com.prosto.myapplication.core.utils.clearBlurEffect
import com.prosto.myapplication.databinding.ActivityMainBinding
import com.prosto.myapplication.databinding.FragmentRadioBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initViews() {
        setStatusbarTextColor()
    }

    fun applyBlurEffect() {
        binding.bannerAdView.applyBlurEffect()
    }

    fun clearBlurEffect() {
        binding.bannerAdView.clearBlurEffect()
    }

    fun setStatusbarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}