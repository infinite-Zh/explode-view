package com.infinite.explode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.infinite.explode.particle.FireworkParticleFactory
import com.infinite.explode.particle.GravityParticleFactory
import com.infinite.explode.particle.RandomParticleFactory
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
                .listen(object : ExplodeView.OnEndListener {
                    override fun onEnd(view: View) {
                        Toast.makeText(this@MainActivity, "动画结束", Toast.LENGTH_LONG).show()
                        view.visibility = View.VISIBLE
                    }
                })
                .create()
                .explode()
        }

        firework.setOnClickListener {
            ExplodeView.Companion.Builder()
                .attachActivity(this)
                .factory(FireworkParticleFactory())
                .target(it)
                .listen(object : ExplodeView.OnEndListener {
                    override fun onEnd(view: View) {
                        Toast.makeText(this@MainActivity, "动画结束", Toast.LENGTH_LONG).show()
                        view.visibility = View.VISIBLE
                    }
                })
                .create()
                .explode()
        }

        round.setOnClickListener {
            ExplodeView.Companion.Builder()
                .attachActivity(this)
                .factory(RandomParticleFactory())
                .target(it)
                .listen(object : ExplodeView.OnEndListener {
                    override fun onEnd(view: View) {
                        Toast.makeText(this@MainActivity, "动画结束", Toast.LENGTH_LONG).show()
                        view.visibility = View.VISIBLE
                    }
                })
                .create()
                .explode()
        }
    }
}
