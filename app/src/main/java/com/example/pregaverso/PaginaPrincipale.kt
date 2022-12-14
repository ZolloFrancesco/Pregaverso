package com.example.pregaverso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.primapagina.*

class PaginaPrincipale : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.primapagina)

        for (i in 0 until 100) {
            barraCaricamento.progress = i
            Thread.sleep(100)
        }
    }

}