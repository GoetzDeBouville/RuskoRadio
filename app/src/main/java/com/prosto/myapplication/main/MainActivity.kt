package com.prosto.myapplication.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    fun applyBlurEffect() {
        binding.bannerAdView.applyBlurEffect()
    }

    fun clearBlurEffect() {
        binding.bannerAdView.clearBlurEffect()
    }
}