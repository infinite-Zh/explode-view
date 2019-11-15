package com.infinite.explode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.infinite.explode.particle.GravityParticleFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img.setOnClickListener {
            ExplodeView.Companion.Builder()
                .attachActivity(this)
                .factory(GravityParticleFactory())
                .target(img)
                .create()
                .explode()
        }
        tv.setOnClickListener {
            ExplodeView.Companion.Builder()
                .attachActivity(this)
                .factory(GravityParticleFactory())
                .target(it)
                .create()
                .explode()
        }
    }
}
