package com.prosto.myapplication.main

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import com.prosto.myapplication.core.ui.BaseActivity
import com.prosto.myapplication.core.utils.ConfigTool
import com.prosto.myapplication.databinding.ActivityMainBinding
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private var adUnitId = ""
    private lateinit var service: NotificationService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initViews() {
        service = NotificationService(applicationContext)
        ConfigTool.init(this)
        adUnitId = ConfigTool.getAppConfig().adUnitId
        initBannerAdView()
        setStatusbarTextColor()
        initMobileAds()
    }

    private fun setStatusbarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }


    private fun initMobileAds() {
        MobileAds.initialize(this) {}
    }

    private fun initBannerAdView() = with(binding) {
        bannerAdView.setAdUnitId(adUnitId)
        bannerAdView.setAdSize(
            BannerAdSize.stickySize(
                this@MainActivity,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        val adRequest: AdRequest = AdRequest.Builder().build()
        bannerAdView.setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {}
            override fun onAdFailedToLoad(p0: AdRequestError) {}
            override fun onAdClicked() {}
            override fun onLeftApplication() {}
            override fun onReturnedToApplication() {}
            override fun onImpression(p0: ImpressionData?) {}
        })
        bannerAdView.loadAd(adRequest)
    }
}
