package com.infinite.explode.particle

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
class TearDownParticle(color: Int, cx: Int, cy: Int, centerTarget: Int) :
    AbsParticle(color, cx, cy) {
    private var vx = 0.toDouble()
    private var vy = 0.toDouble()

    companion object {
        const val A_Y = 9.8
    }

    init {
        vx = if (cx < centerTarget) {
            (Math.random() - 1).times(30)
        } else {
            (Math.random()).times(30)
        }
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
        vy += A_Y
    }

    override fun outOfZone(rect: Rect): Boolean {
        return !rect.contains(cx, cy)
    }
}