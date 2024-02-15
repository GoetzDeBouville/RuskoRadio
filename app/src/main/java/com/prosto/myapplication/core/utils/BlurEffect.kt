package com.prosto.myapplication.core.utils

import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View
import android.view.ViewGroup

fun View.applyBlurEffect(radius: Float = 15f, tileMode: Shader.TileMode = Shader.TileMode.MIRROR) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val renderEffect = RenderEffect.createBlurEffect(radius, radius, tileMode)
        this.setRenderEffect(renderEffect)
    } else {
        val dimView = View(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
            alpha = 0.9f
        }
        if (this.parent is ViewGroup) {
            val parentView = this.parent as ViewGroup
            parentView.addView(dimView)
        }
    }
}

fun View.clearBlurEffect() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.setRenderEffect(null)
    } else {
        val dimView = View(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.TRANSPARENT)
        }
        if (this.parent is ViewGroup) {
            val parentView = this.parent as ViewGroup
            parentView.addView(dimView)
        }
    }
}
