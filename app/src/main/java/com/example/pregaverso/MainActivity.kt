package com.example.pregaverso

import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fadeIn = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_out)
        val bounce = AnimationUtils.loadAnimation(applicationContext,R.anim.bounce)

        // service
        startService(Intent(this,ServizioSottofondo::class.java))

        supportActionBar?.hide()

        var tempoInizio : Long = 4000

        val testi : ArrayList<String> = ArrayList()
        testi.add("Mezzautunno 1347")
        testi.add("in un mondo colmo di presagi e ingiustizie")
        testi.add("in cui ogni vicolo puo' essere l'ultimo")
        testi.add("ogni volto un assassino")
        testi.add("ogni malattia una prematura dipartita")


        for(i in 0 until testi.size){
            testoStoria.postDelayed({
                testoStoria.startAnimation(fadeIn)
                if(i == 1) testoStoria.visibility = VISIBLE
                if(i != 0) testoStoria.text = testi[i]
                                    } , tempoInizio)

            tempoInizio += 3000

            testoStoria.postDelayed({
                testoStoria.startAnimation(fadeOut)
            } , tempoInizio)

            tempoInizio += 4000
        }

        val tempoChiusura = intent.getIntExtra("After", tempoInizio.toInt())

        Handler(Looper.getMainLooper()).postDelayed({
            val dialogIntent = Intent(this, PaginaCaricamento::class.java)
            val sharedPreferences = getSharedPreferences("USERDATA", MODE_PRIVATE)

            dialogIntent.putExtra("screen", sharedPreferences.getString("screen", "ios"))
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            this.startActivity(dialogIntent)
        }, tempoChiusura.toLong())

        /*
        da chiedere perche' non funziona
        testoStoria.postDelayed({
            startActivity(Intent(this@MainActivity, PaginaPrincipale::class.java))
        }, tempoInizio + 2000)
         */

        btnSkip.setOnClickListener {
            btnSkip.startAnimation(bounce)
            startActivity(Intent(this@MainActivity, PaginaCaricamento::class.java))
        }
    }

}
