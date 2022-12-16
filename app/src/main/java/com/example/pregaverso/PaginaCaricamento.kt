package com.example.pregaverso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.primapagina.*

class PaginaCaricamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.primapagina)

        supportActionBar?.hide()

        val bounce = AnimationUtils.loadAnimation(applicationContext,R.anim.bounce)

        barraCaricamento.postDelayed({
            barraCaricamento.visibility = INVISIBLE
        },2000)

        bottonePrincipale.setOnClickListener{
            bottonePrincipale.startAnimation(bounce)
            startActivity(Intent(this@PaginaCaricamento, LoginSacerdote::class.java))
        }

    }

}