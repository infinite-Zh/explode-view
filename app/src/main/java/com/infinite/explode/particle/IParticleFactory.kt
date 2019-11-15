package com.infinite.explode.particle

import android.graphics.Bitmap
import android.graphics.Rect

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
interface IParticleFactory {

    fun createParticles(bitmap: Bitmap,rect: Rect,count:Int):MutableList<AbsParticle>
}