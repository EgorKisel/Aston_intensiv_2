package com.example.aston_intensiv_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

class WheelView : View {
    private val colors = listOf(
        Color.RED,
        Color.rgb(255, 165, 0),
        Color.YELLOW,
        Color.GREEN,
        Color.BLUE,
        Color.rgb(0, 191, 255),
        Color.MAGENTA
    )
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 40f
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
    }

    private var wheelSize = 200 // Default size
    private var currentAngle = 0f
    private var spinning = false
    private var currentColor = Color.BLACK

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val halfWidth = width / 2f
        val halfHeight = height / 2f

        // Draw the wheel
        val wheelPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        wheelPaint.color = currentColor
        canvas.drawCircle(halfWidth, halfHeight, wheelSize.toFloat(), wheelPaint)

        if (spinning) {
            // Rotate the canvas if spinning
            canvas.rotate(currentAngle, halfWidth, halfHeight)
            postInvalidateOnAnimation()
        } else {
            // Draw text or image based on color
            if (currentColor == Color.RED || currentColor == Color.YELLOW || currentColor == Color.rgb(
                    0,
                    191,
                    255
                )
            ) {
                // Draw text
                canvas.drawText("Text", halfWidth, halfHeight, textPaint)
            } else {
                // Draw image
                val imageUrl =
                    "https://loremipsum.io/ru/21-of-the-best-placeholder-image-generators/"
                loadImage(imageUrl, canvas, halfWidth, halfHeight)
            }
        }
    }

    private fun loadImage(url: String, canvas: Canvas, x: Float, y: Float) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(RequestOptions().centerCrop())
                    .submit()
                    .get()

                withContext(Dispatchers.Main) {
                    // Draw the loaded image on the canvas
                    canvas.drawBitmap(bitmap, x - bitmap.width / 2f, y - bitmap.height / 2f, null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun spin() {
        if (!spinning) {
            spinning = true
            animateWheel()
        }
    }

    private fun animateWheel() {
        val random = Random()
        val newAngle = random.nextInt(360).toFloat()
        currentColor = colors[random.nextInt(colors.size)]

        animate().rotationBy(newAngle).setDuration(2000)
            .withEndAction {
                currentAngle += newAngle
                spinning = false
                invalidate()
                // TODO: Handle the result based on the currentColor (show text or image)
            }
    }

    fun reset() {
        currentAngle = 0f
        spinning = false
        currentColor = Color.BLACK
        invalidate()
    }

    fun setSize(size: Int) {
        wheelSize = size
        invalidate()
    }
}