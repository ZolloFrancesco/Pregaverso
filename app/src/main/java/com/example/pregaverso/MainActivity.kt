package com.example.pregaverso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fadeIn = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)

        val fadeOut = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_out)

        supportActionBar?.hide()

        var tempoInizio : Long = 4000

        val testi : ArrayList<String> = ArrayList()

        testi.add("Mezzautunno 1347")
        testi.add("in un mondo colmo di presagi e ingiustizie")
        testi.add("in cui ogni vicolo puo' essere l'ultimo")
        testi.add("ogni volto un assassino")
        testi.add("ogni malattia una morte")

        val x = db.prendiPlebei()

        Log.d("QUALCOSA","${x[0].nome} ${x[0].casata} ${x[0].baiocchi}")
        Log.d("QUALCOSA","${x[1].nome} ${x[1].casata} ${x[1].baiocchi}")

    }
}