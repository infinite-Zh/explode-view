package com.infinite.explode.particle

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
class RandomParticle(color: Int, cx: Int, cy: Int, private var ax: Double, private var ay: Double) :
    AbsParticle(color, cx, cy) {
    private var vx = 0.toDouble()
    private var vy = 0.toDouble()


    init {
        vx = (Math.random() - 0.5f).times(200)
        vy = (Math.random() - 0.5).times(200)
    }

    private val paint = Paint().apply {
        this.color = color
    }

    override fun move(canvas: Canvas) {
        paint.color = color
        canvas.drawRect(
            cx.toFloat(), cy.toFloat(),
            cx + 12.toFloat(), cy + 12.toFloat(), paint
        )
    }

    override fun updatePosition(factor: Float) {
        cx += vx.toInt()
        cy += vy.toInt()
        vx += ax
        vy += ay
    }

    override fun outOfZone(rect: Rect): Boolean {
        return !rect.contains(cx, cy)
    }
}