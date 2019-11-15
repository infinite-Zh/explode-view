package com.infinite.explode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ExplodeView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )


    private val particles: MutableList<AbsParticle> = mutableListOf()
    val bmp = BitmapFactory.decodeResource(resources, R.mipmap.th)

    val COUNT = 1000
    private fun init() {
        particles.clear()
        var particle: AbsParticle
        var sampleX = 1
        var sampleY = 1
        val w = bmp.width
        val h = bmp.height

        while (w.times(h).div(sampleX.times(sampleY)) > COUNT) {
            sampleX++
            sampleY++
        }


        for (i in 0 until bmp.width step sampleX) {
            for (j in 0 until bmp.height step sampleY) {
                particle = GravityParticle(
                    bmp.getPixel(i, j), i, j
                )
                particles.add(particle)
            }
        }
    }

    private val paint: Paint by lazy {
        Paint()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!animator.isRunning) {
            canvas?.drawBitmap(bmp, 0f, 0f, paint)
        } else {
            particles.forEach {
                it.move(canvas!!)
            }
        }
    }

    val animator = ValueAnimator.ofFloat(0f, 1f)

    private fun start() {
        if (animator.isRunning) {
            return
        }
        init()
        animator.removeAllUpdateListeners()
        animator.addUpdateListener { v ->
            particles.forEach { p ->
                val value = v.animatedValue as Float
                p.updatePosition(value)
            }
            invalidate()

        }
        animator.duration = 2000
        animator.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            start()
        }
        return super.onTouchEvent(event)
    }

}