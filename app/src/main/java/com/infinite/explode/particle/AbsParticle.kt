package com.infinite.explode.particle

import android.graphics.Canvas
import android.graphics.Rect

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
abstract class AbsParticle(val color: Int, var cx: Int, var cy: Int) {

    abstract fun move(canvas: Canvas)

    abstract fun updatePosition(factor: Float)

    abstract fun outOfZone(rect: Rect):Boolean
}