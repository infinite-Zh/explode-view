package com.infinite.explode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class ExplodeView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        init()
    }

    private val particles: MutableList<Particle> = mutableListOf()
    val bmp = BitmapFactory.decodeResource(resources, R.mipmap.a)

    val COUNT=1000
    private fun init() {
        particles.clear()
        var particle: Particle
        var sampleX=1
        var sampleY=1
        val w=bmp.width
        val h=bmp.height

        while (w.times(h).div(sampleX.times(sampleY))>COUNT){
            sampleX++
            sampleY++
        }


        for (i in 0 until bmp.width step sampleX) {
            for (j in 0 until bmp.height step sampleY) {
                particle = Particle(
                    bmp.getPixel(i, j), i, j,
                    (Math.random() - 0.5f).times(40), (Math.random() - 0.5f).times(60),
                    0.toDouble(), 4.8
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
//        canvas?.translate(300f,0f)
        if (!animator.isRunning) {
            canvas?.drawBitmap(bmp, 0f, 0f, paint)
        } else {
            particles.forEach {
                paint.color = it.color
//                canvas?.drawCircle(it.cx.toFloat(), it.cy.toFloat(), 12f, paint)
                canvas?.drawRect(it.cx.toFloat(), it.cy.toFloat(),
                    it.cx+12.toFloat(),it.cy+12.toFloat(), paint)
            }
        }
    }

    val animator = ValueAnimator.ofFloat(0f, 1f)

    private fun start() {
        if (animator.isRunning){
            return
        }
        init()
        animator.addUpdateListener { v ->
            particles.forEach { p ->
                val value = v.animatedValue as Float
                p.cx += p.vx.toInt()
                p.cy += p.vy.toInt()

                p.vx += p.ax
                p.vy += p.ay
                invalidate()

                Log.e("value", "${p.cx}###${p.cy}")
            }
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

    data class Particle(
        val color: Int,
        var cx: Int, var cy: Int,
        var vx: Double, var vy: Double,
        var ax: Double, var ay: Double
    )
}