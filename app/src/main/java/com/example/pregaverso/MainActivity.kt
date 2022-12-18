package com.example.pregaverso

import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activitymain.*
import java.util.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)

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
        testi.add("in cui ogni vicolo può essere l'ultimo")
        testi.add("ogni volto un assassino")
        testi.add("ogni malattia una prematura dipartita")


        for(i in 0 until testi.size){
            testoStoria.postDelayed({
                testoStoria.startAnimation(fadeIn)
                if(i != 0) testoStoria.text = testi[i]
                                    } , tempoInizio)

            tempoInizio += 3000

            testoStoria.postDelayed({
                testoStoria.startAnimation(fadeOut)
            } , tempoInizio)

            tempoInizio += 4000
        }

        bottoneEntra.postDelayed( {
            bottoneEntra.startAnimation(fadeIn)
            bottoneEntra.visibility = VISIBLE
        }, tempoInizio)

        alette.postDelayed( {
            alette.startAnimation(fadeIn)
            alette.visibility = VISIBLE
        }, tempoInizio)

        titolo.postDelayed( {
            titolo.startAnimation(fadeIn)
            titolo.visibility = VISIBLE
        }, tempoInizio)

        bottoneEntra.setOnClickListener {
            bottoneEntra.startAnimation(bounce)
            startActivity(Intent(this@MainActivity, LoginSacerdote::class.java))
        }

        /*
        da chiedere perche' non funziona
        testoStoria.postDelayed({
            startActivity(Intent(this@MainActivity, PaginaPrincipale::class.java))
        }, tempoInizio + 2000)
         */

        btnSkip.setOnClickListener {
            btnSkip.startAnimation(bounce)
            testoStoria.startAnimation(fadeOut)
            testoStoria.setTextColor(resources.getColor(R.color.black,this.theme))
            bottoneEntra.visibility = VISIBLE
            alette.visibility = VISIBLE
            titolo.visibility = VISIBLE
        }
    }

}
