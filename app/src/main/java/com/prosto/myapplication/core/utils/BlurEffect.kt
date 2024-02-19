package com.prosto.myapplication.core.utils

import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.prosto.myapplication.R

fun View.applyBlurEffect(radius: Float = 36f, tileMode: Shader.TileMode = Shader.TileMode.MIRROR) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val renderEffect = RenderEffect.createBlurEffect(radius, radius, tileMode)
        this.setRenderEffect(renderEffect)
    }
//    else {
//        val dimView = View(context).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            setBackgroundColor(Color.WHITE)
//            alpha = 0.1f
//        }
//        if (this.parent is ViewGroup) {
//            val parentView = this.parent as ViewGroup
//            parentView.addView(dimView)
//        }
//    }
}

fun View.clearBlurEffect() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.setRenderEffect(null)
    }
//    else {
//        val dimView = View(context).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            setBackgroundColor(Color.TRANSPARENT)
//        }
//        if (this.parent is ViewGroup) {
//            val parentView = this.parent as ViewGroup
//            parentView.addView(dimView)
//        }
//    }
}
