package com.prosto.ruskoradio.main

import android.app.AlarmManager
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.prosto.presentation.BaseActivity
import com.prosto.ruskoradio.R
import com.prosto.ruskoradio.databinding.ActivityMainBinding
import com.prosto.ruskoradio.core.utils.ConfigTool
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
        setStatusBar()
        initMobileAds()
        permissionsSchedule()
        permissionsNotification()
    }

    @Suppress("DEPRECATION")
    private fun setStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background_color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isColorDark(ContextCompat.getColor(this, R.color.background_color))) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility = 0
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    }

    private fun isColorDark(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
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

    private fun permissionsSchedule() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
        }
    }

    private fun permissionsNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
        }
    }
}
