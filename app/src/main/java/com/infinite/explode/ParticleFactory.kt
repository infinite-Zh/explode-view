package com.infinite.explode

import android.graphics.Bitmap

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
interface ParticleFactory {

    fun createParticles(bitmap: Bitmap,count:Int):MutableList<AbsParticle>
}