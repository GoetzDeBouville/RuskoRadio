package com.hellcorp.presentation.playback_button

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.graphics.drawable.toBitmap
import com.hellcorp.presentation.R
import kotlin.math.min

class PlaybackButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes deffstyleAttr: Int = 0,
    @StyleRes deffstyleRes: Int = 0
) : View(
    context,
    attrs,
    deffstyleAttr,
    deffstyleRes
) {
    // minViewSize must be more on equal to view sizes on layout,
    // because of all images is converting to raster (bitmap picture)
    // on initialization with a particular size
    private val minViewSize: Int = resources.getDimension(R.dimen.minViewSize).toInt()

    private var imageBitmap: Bitmap?
    private var playBitmap: Bitmap?
    private var pauseBitmap: Bitmap?
    private var imageRect = RectF(0f, 0f, 0f, 0f)
    private var isPlaying = false

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.PlaybackButtonView, deffstyleAttr, deffstyleRes
        ).apply {
            try {
                playBitmap = getDrawable(R.styleable.PlaybackButtonView_playButtonId)?.toBitmap(
                    minViewSize,
                    minViewSize
                )
                pauseBitmap = getDrawable(R.styleable.PlaybackButtonView_pauseButtonId)?.toBitmap(
                    minViewSize,
                    minViewSize
                )
                isPlaying = getBoolean(R.styleable.PlaybackButtonView_isPlaying, false)
                imageBitmap = if (isPlaying) pauseBitmap else playBitmap
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val contentWidth = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> minViewSize
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> widthSize
            else -> error("Unexpected widthMode")
        }
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val contentHeight = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> minViewSize
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> heightSize
            else -> error("Unexpected widthMode")
        }

        val size = min(contentHeight, contentWidth)
        setMeasuredDimension(size, size)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        imageBitmap?.let {
            canvas.drawBitmap(it, null, imageRect, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isClickable) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> return true
                MotionEvent.ACTION_UP -> {
                    updatePlaybackState()
                    performClick()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updatePlaybackState() {
        isPlaying = !isPlaying
        imageBitmap = if (isPlaying) pauseBitmap else playBitmap
        invalidate()
    }

    fun setStatusPlay() {
        isPlaying = true
        imageBitmap = if (isPlaying) pauseBitmap else playBitmap
        invalidate()
    }

    fun setStatusPause() {
        isPlaying = false
        imageBitmap = if (isPlaying) pauseBitmap else playBitmap
        invalidate()
    }
}