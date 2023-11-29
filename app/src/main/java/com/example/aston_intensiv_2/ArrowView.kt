package com.example.aston_intensiv_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ArrowView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    private val arrowPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        //arrowPath.reset()
        arrowPath.moveTo(centerX - 20f, centerY)
        arrowPath.lineTo(centerX + 20f, centerY)
        arrowPath.lineTo(centerX, centerY - 80f)
        //arrowPath.close()

        canvas.drawPath(arrowPath, arrowPaint)
    }
}