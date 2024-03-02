package com.prosto.presentation.playback_button

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.prosto.presentation.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

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
    private val minViewSize = resources.getDimensionPixelSize(R.dimen.minViewSize)

    private var isPlaying = false
    private var strokeWidthPx = 0f
    private var innerPaddingPx = 0f

    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f

    private var circlePainter = Paint()
    private var rectPainter = Paint()

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.PlaybackButtonView, deffstyleAttr, deffstyleRes
        ).apply {
            try {
                isPlaying = getBoolean(R.styleable.PlaybackButtonView_isPlaying, false)

                val defaultStrokeWidth =
                    resources.getDimensionPixelSize(R.dimen.minStrokeWidth).toFloat()
                strokeWidthPx = getDimension(
                    R.styleable.PlaybackButtonView_circleStrokeWidth,
                    defaultStrokeWidth
                ).dpToFloat()
                innerPaddingPx = getDimension(
                    R.styleable.PlaybackButtonView_innerPadding,
                    defaultStrokeWidth
                ).dpToFloat()
                applyPainter(getResourceId(R.styleable.PlaybackButtonView_btnColor, 0))
            } finally {
                recycle()
            }
        }
    }

    private fun Float.dpToFloat(): Float {
        val scale = resources.displayMetrics.density
        return (this * scale)
    }

    private fun applyPainter(colorId: Int) {
        circlePainter = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = ContextCompat.getColor(context, colorId)
            strokeWidth = strokeWidthPx
        }

        rectPainter = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, colorId)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val contentWith = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> minViewSize
            MeasureSpec.AT_MOST -> widthSize
            MeasureSpec.EXACTLY -> widthSize
            else -> error("Unexpected widthMode")
        }

        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val contentHeight = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> minViewSize
            MeasureSpec.AT_MOST -> heightSize
            MeasureSpec.EXACTLY -> heightSize
            else -> error("Unexpected size heightMode")
        }

        val size = min(contentWith, contentHeight)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = measuredWidth / 2f
        centerY = measuredHeight / 2f
    }

    override fun onDraw(canvas: Canvas) {
        radius = (measuredWidth - strokeWidthPx) / 2f - innerPaddingPx
        canvas.run { drawOuterCircle(centerX, centerY, radius) }
        if (isPlaying) {
            canvas.drawPause(centerX, centerY, radius)
        } else {
            canvas.drawPlay(centerX, centerY, radius - strokeWidthPx)
        }
    }

    private fun Canvas.drawOuterCircle(centerX: Float, centerY: Float, radius: Float) {
        this.drawCircle(centerX, centerY, radius, circlePainter)
    }

    private fun Canvas.drawPlay(centerX: Float, centerY: Float, radius: Float) {
        val sideLength = sqrt(3f) * radius
        val angle30 = Math.toRadians(60.0)
        val x1 = centerX + radius
        val y1 = centerY
        val x2 = centerX - sideLength / 2f * cos(angle30).toFloat()
        val y2 = centerY + sideLength / 2f * sin(angle30).toFloat()
        val x3 = centerX - sideLength / 2f * cos(angle30).toFloat()
        val y3 = centerY - sideLength / 2f * sin(angle30).toFloat()

        val path = Path()
        path.moveTo(x1, y1)
        path.lineTo(x2, y2)
        path.lineTo(x3, y3)
        path.close()

        this.drawPath(path, rectPainter)
    }

    private fun Canvas.drawPause(centerX: Float, centerY: Float, radius: Float) {
        val firstLeftX = centerX - radius / 3
        val firstLeftY = centerY - radius / 2

        val firstRightX = centerY - (radius / 3 - strokeWidthPx)
        val firstRightY = centerY + radius / 2

        this.drawRect(firstLeftX, firstLeftY, firstRightX, firstRightY, rectPainter)

        val secondLeftX = centerX + radius / 3
        val secondLeftY = centerY - radius / 2

        val secondRightX = centerY + (radius / 3 - strokeWidthPx)
        val secondRightY = centerY + radius / 2

        this.drawRect(secondLeftX, secondLeftY, secondRightX, secondRightY, rectPainter)
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
        invalidate()
    }

    fun setStatusPlay() {
        isPlaying = true
        invalidate()
    }

    fun setStatusPause() {
        isPlaying = false
        invalidate()
    }
}