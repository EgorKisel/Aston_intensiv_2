package com.example.aston_intensiv_2.presentation.custom_view

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import com.example.aston_intensiv_2.data.DrumSectorData

class RainbowDrumView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var drumSectorData: DrumSectorData? = null
    private val oval = RectF()
    private var initialHeight: Int? = null
    private val spinningAnimator = ValueAnimator.ofFloat()
    private val animateSpinning = AnimatorSet()
    private val scalingAnimator = ValueAnimator.ofFloat()
    private val animateScaling = AnimatorSet()
    private var rotationAngle = 0f
    private var scale = 0.5f
    private var animationResultAngle = 0f
    private var doOnAnimationEnd: (winner: String) -> Unit = {}
    private val ANIMATION_DURATION = 3000L

    init {
        setupSpinningAnimation()
        setupScalingAnimation()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (initialHeight == null) {
            initialHeight = layoutParams.height
        }
        layoutParams.height = (initialHeight!! * scale).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.rotate(rotationAngle, width / 2f, height / 2f)
        drumSectorData?.drumSector?.let { sector ->
            sector.forEach {
                canvas.drawArc(
                    oval,
                    it.value.startAngle,
                    it.value.sweepAngle,
                    true,
                    it.value.paint
                )
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        oval.top = 0f
        oval.bottom = layoutParams.height.toFloat()
        oval.left = (width / 2) - (layoutParams.height / 2).toFloat()
        oval.right = (width / 2) + (layoutParams.height / 2).toFloat()
    }

    fun setData(drumSectorData: DrumSectorData) {
        this.drumSectorData = drumSectorData
        setPieSliceDimensions()
        invalidate()
    }

    private fun setPieSliceDimensions() {
        var lastAngle = 0f
        drumSectorData?.drumSector?.let { data ->
            data.forEach {
                it.value.startAngle = lastAngle
                it.value.sweepAngle = drumSectorData!!.sweepAngle
                lastAngle += it.value.sweepAngle
            }
        }
    }

    fun animateSpinning() {
        animationResultAngle = getRandomSpinningResult()
        spinningAnimator.setFloatValues(0f, animationResultAngle)
        animateSpinning.start()
    }

    fun animateScaling(newScale: Float) {
        scalingAnimator.setFloatValues(scale, newScale)
        animateScaling.start()
        scale = newScale
    }

    fun setDoOnAnimationEnd(doOnAnimationEnd: (winner: String) -> Unit) {
        this.doOnAnimationEnd = doOnAnimationEnd
    }

    private fun getRandomSpinningResult() = kotlin.random.Random.nextFloat() * 3600f

    private fun setupSpinningAnimation() {
        spinningAnimator.duration = ANIMATION_DURATION
        spinningAnimator.interpolator = AccelerateDecelerateInterpolator()
        spinningAnimator.addUpdateListener {
            rotationAngle = (it.animatedValue as Float)
            requestLayout()
            invalidate()
        }
        spinningAnimator.doOnEnd {
            updateDrumData()
            rotationAngle = 0f
            invalidate()
            calculateWinner()
        }
        animateSpinning.play(spinningAnimator)
    }

    private fun setupScalingAnimation() {
        scalingAnimator.duration = 0L
        scalingAnimator.interpolator = LinearInterpolator()
        scalingAnimator.addUpdateListener {
            initialHeight?.let { height ->
                layoutParams.height = (height * (it.animatedValue as Float)).toInt()
            }
            requestLayout()
            invalidate()
        }
        animateScaling.play(scalingAnimator)
    }

    private fun calculateWinner() {
        drumSectorData?.drumSector?.forEach {
            if (it.value.startAngle <= 90 && it.value.startAngle + it.value.sweepAngle > 90) {
                doOnAnimationEnd(it.value.name)
            }
        }
    }

    private fun updateDrumData() {
        drumSectorData?.drumSector?.let { data ->
            data.forEach {
                it.value.startAngle = (it.value.startAngle + animationResultAngle) % 360
            }
        }
    }
}

