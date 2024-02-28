package com.hellcorp.extentions

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View
import android.widget.TextView
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar


fun View.applyBlurEffect(radius: Float = 36f, tileMode: Shader.TileMode = Shader.TileMode.MIRROR) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val renderEffect = RenderEffect.createBlurEffect(radius, radius, tileMode)
        this.setRenderEffect(renderEffect)
    }
}

fun View.clearBlurEffect() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.setRenderEffect(null)
    }
}

/**
 * Calls this way: binding.root.showCustomSnackbar(text)
 */
fun View.showCustomSnackbar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
    val snackbar = Snackbar.make(this, text, duration)
    val snackbarView = snackbar.view
    val backgroundColor =
        MaterialColors.getColor(snackbarView, com.google.android.material.R.attr.colorOnPrimary)
    snackbarView.setBackgroundColor(backgroundColor)
    val textView =
        snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    val textColor =
        MaterialColors.getColor(textView, com.google.android.material.R.attr.colorPrimary)
    textView.setTextColor(textColor)
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackbar.show()
}