package com.infinite.explode.particle

import android.graphics.Bitmap

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
interface IParticleFactory {

    fun createParticles(bitmap: Bitmap,count:Int):MutableList<AbsParticle>
}