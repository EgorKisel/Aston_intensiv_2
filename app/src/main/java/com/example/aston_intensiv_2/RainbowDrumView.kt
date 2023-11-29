package com.example.aston_intensiv_2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.Random

class RainbowDrumView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    val colors = listOf(
        Color.RED,
        Color.rgb(255, 140, 0),
        Color.YELLOW,
        Color.GREEN,
        Color.rgb(0, 191, 255),
        Color.BLUE,
        Color.rgb(138, 43, 226)
    )

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
    }

    private val paint = Paint().apply {
        color = Color.BLACK
        textSize = 100f
    }

    //val animator = ValueAnimator.ofInt()

    private var currentColorIndex = 0
    private var textSize = 0f
    private var spinning = false

    var drumSize: Float = 400f
        set(value) {
            field = value
            textSize = value/10
            invalidate()
        }

    init {
        drumSize = context.resources.displayMetrics.widthPixels.toFloat() * 0.6f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = drumSize / 2

        colors.forEachIndexed { index, color ->
            val startAngle = 360f / colors.size * index
            textPaint.color = color
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle,
                360f / colors.size,
                true,
                textPaint
            )
        }
        canvas.drawText("AAAAAAAAAAAAAAAAAAAA", 500f, 200f, textPaint)
    }

    fun spin() {
        //currentColorIndex = (colors.indices).random()
        //invalidate()
        if (!spinning) {
            spinning = true
            animateWheel()
        }
    }

    private fun animateWheel() {
        val random = Random()
        val newAngle = random.nextInt(360).toFloat()

        animate().rotationBy(newAngle).setDuration(2000)
            .withEndAction {
                val normalizedAngle = (rotation % 360 + 360) % 360
                val sweepAngle = 360f / colors.size
                val colorIndex = (normalizedAngle / (360f / colors.size)).toInt()

                val currentColor = colors[colorIndex]

                when (currentColor) {
                    Color.RED, Color.YELLOW, Color.BLUE, Color.rgb(138, 43, 226) -> {
                        textField.visibility = View.VISIBLE
                        imageField.visibility = View.GONE

                        val contentText = when (currentColor) {
                            Color.RED -> "Красный текст"
                            Color.YELLOW -> "Жёлтый текст"
                            Color.BLUE -> "Голубой текст"
                            else -> "Фиолетовый текст"
                        }

                        textField.text = contentText
                        textField.setTextColor(currentColor)
                    }
                    else -> {
                        textField.visibility = View.GONE
                        imageField.visibility = View.VISIBLE

                        val imageUrl = "https://loremflickr.com/640/360"
                        loadImageContent(imageUrl)
                    }
                }
                spinning = false
                invalidate()
            }
    }

    fun reset() {
        textField.visibility = View.GONE
        imageField.visibility = View.GONE
    }

    private lateinit var textField: TextView
    private lateinit var imageField: ImageView

    fun setTextField(textView: TextView) {
        this.textField = textView
    }

    fun setImageField(imageView: ImageView) {
        this.imageField = imageView
    }

    private fun loadImageContent(url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageField)

        textField.visibility = View.GONE
        imageField.visibility = View.VISIBLE
    }
}

