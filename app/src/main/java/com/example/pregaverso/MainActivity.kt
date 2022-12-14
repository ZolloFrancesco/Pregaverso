package com.example.pregaverso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fadeIn = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)

        val fadeOut = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_out)

        supportActionBar?.hide()

        var tempoInizio : Long = 4000

        val db = Database(this)

        db.aggiungiPlebeo("marco","imbecilli",30)
        db.aggiungiPlebeo("lorenzo","forti",100)

        val x = db.prendiPlebei()

        Log.d("QUALCOSA","${x[0].nome} ${x[0].casata} ${x[0].baiocchi}")
        Log.d("QUALCOSA","${x[1].nome} ${x[1].casata} ${x[1].baiocchi}")

    }
}