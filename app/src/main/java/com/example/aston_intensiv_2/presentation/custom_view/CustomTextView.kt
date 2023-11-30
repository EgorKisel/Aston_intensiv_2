package com.example.aston_intensiv_2.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import com.example.aston_intensiv_2.R

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var text = ""
    private lateinit var paint: Paint
    private var fontSize: Float = 0f

    fun setText(text: String, @ColorRes color: Int = R.color.black, fontSize: Float) {
        this.text = text
        this.fontSize = fontSize
        this.paint = Paint().apply {
            setColor(resources.getColor(color, null))
            style = Paint.Style.FILL_AND_STROKE
            textSize = fontSize
            textAlign = Paint.Align.CENTER
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX  = (width / 2).toFloat()
        val centerY  = (height / 2 - (paint.descent() + paint.ascent()) / 2)
        canvas.drawText(text, centerX , centerY , paint)
    }
}