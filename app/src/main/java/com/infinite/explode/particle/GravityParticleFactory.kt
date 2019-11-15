package com.infinite.explode.particle

import android.graphics.Bitmap
import android.graphics.Rect

/**
 * @author bug小能手
 * Created on 2019/11/15.
 */
class GravityParticleFactory : IParticleFactory {
    override fun createParticles(bitmap: Bitmap, rect: Rect,count: Int): MutableList<AbsParticle> {
        val particles = mutableListOf<AbsParticle>()
        var particle: AbsParticle
        var sampleX = 1
        var sampleY = 1
        val w = bitmap.width
        val h = bitmap.height

        while (w.times(h).div(sampleX.times(sampleY)) > count) {
            sampleX++
            sampleY++
        }


        for (i in 0 until bitmap.width step sampleX) {
            for (j in 0 until bitmap.height step sampleY) {
                particle = GravityParticle(
                    bitmap.getPixel(i, j), i+rect.left, j+rect.top
                )
                particles.add(particle)
            }
        }
        return particles
    }
}