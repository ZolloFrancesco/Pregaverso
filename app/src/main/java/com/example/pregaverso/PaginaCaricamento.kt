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

        barraCaricamento.postDelayed({
            barraCaricamento.visibility = INVISIBLE
        },2000)

        bottonePrincipale.setOnClickListener{
            startActivity(Intent(this@PaginaCaricamento, LoginSacerdote::class.java))
        }

    }

}