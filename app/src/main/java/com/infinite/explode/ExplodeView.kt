package com.infinite.explode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.infinite.explode.particle.AbsParticle
import com.infinite.explode.particle.GravityParticleFactory
import com.infinite.explode.particle.IParticleFactory

class ExplodeView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )


    private val particles: MutableList<AbsParticle> = mutableListOf()
    private lateinit var mFactory: IParticleFactory
    val bmp = BitmapFactory.decodeResource(resources, R.mipmap.th)

    private fun init() {
        mFactory=GravityParticleFactory()
        particles.clear()
        particles.addAll(mFactory.createParticles(bmp,1000))
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

    private val animator = ValueAnimator.ofFloat(0f, 1f)

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

    fun explode(){
        start()
    }

}