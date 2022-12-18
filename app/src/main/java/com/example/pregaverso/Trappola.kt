package com.example.pregaverso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.popuptrappola.*

class Trappola : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trappola)

        // nascondo la ActionBar per estetica
        supportActionBar?.hide()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.d("NON SI PUO'","FREGATO")
    }
}