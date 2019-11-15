package com.infinite.explode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.infinite.explode.particle.AbsParticle
import com.infinite.explode.particle.IParticleFactory

class ExplodeView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private val animator = ValueAnimator.ofFloat(0f, 1f)

    private val particles: MutableList<AbsParticle> = mutableListOf()
    private lateinit var mFactory: IParticleFactory
    private lateinit var _mActivity: AppCompatActivity

    private lateinit var mBitmap: Bitmap
    private lateinit var targetView: View
    private var targetLeft = 0
    private var targetTop = 0

    private lateinit var mWindowRect: Rect

    private var mOnEndListener: OnEndListener? = null

    interface OnEndListener {
        fun onEnd(view: View)
    }

    private fun init() {
        targetLeft = targetView.left
        targetTop = targetView.top
        mBitmap = Bitmap.createBitmap(
            targetView.measuredWidth,
            targetView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(mBitmap)
        targetView.draw(canvas)
        particles.clear()
        particles.addAll(
            mFactory.createParticles(
                mBitmap,
                Rect(targetLeft, targetTop, targetView.measuredWidth, targetView.measuredHeight),
                1000
            )
        )
    }

    fun setOnEndListener(l: OnEndListener?) {
        mOnEndListener = l
    }

    fun explode() {
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        _mActivity.window.addContentView(this, lp)
        start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWindowRect = Rect(0, 0, w, h)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var count = 0
        particles.forEach {
            if (!it.outOfZone(mWindowRect)) {
                count++
                it.move(canvas!!)
            }
        }
        checkShouldStop(count)
    }

    private fun checkShouldStop(count: Int) {
        if (count == 0) {
            animator.cancel()
            if (mOnEndListener != null) {
                mOnEndListener!!.onEnd(targetView)
            } else {
                targetView.visibility = VISIBLE
            }
        }
    }


    private fun start() {
        if (animator.isRunning) {
            return
        }
        targetView.visibility = INVISIBLE
        init()
        animator.removeAllUpdateListeners()
        animator.addUpdateListener { v ->
            particles.forEach { p ->
                val value = v.animatedValue as Float
                p.updatePosition(value)
            }
            invalidate()
            if (v.animatedValue == 1f) {
                checkShouldStop(0)
            }
        }
        animator.duration = 2000
        animator.start()
    }


    companion object {
        class Builder {
            var _mActivity: AppCompatActivity? = null
            var mView: View? = null
            var listener: OnEndListener? = null
            var factory: IParticleFactory? = null
            fun attachActivity(activity: AppCompatActivity): Builder {
                _mActivity = activity
                return this
            }

            fun target(view: View): Builder {
                this.mView = view
                return this
            }

            fun listen(onEndListener: OnEndListener): Builder {
                listener = onEndListener
                return this
            }


            fun factory(factory: IParticleFactory): Builder {
                this.factory = factory
                return this
            }


            fun create(): ExplodeView {
                require(_mActivity != null) { "must attach an activity instance" }
                require(factory != null) { "factory must not be null" }
                require(mView != null) { "targetView must not be null" }
                val zone = ExplodeView(_mActivity!!)

                zone.mFactory = factory!!
                zone._mActivity = _mActivity!!
                zone.targetView = mView!!
                zone.setOnEndListener(listener)
                return zone
            }

        }
    }


}