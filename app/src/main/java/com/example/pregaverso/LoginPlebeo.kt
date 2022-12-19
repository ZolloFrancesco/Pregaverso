package com.example.pregaverso

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.loginsacerdote.*
import kotlinx.android.synthetic.main.popupinformativo.view.*

var plebeoCorrente = Plebeo()


class LoginPlebeo : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginplebeo)

        supportActionBar?.hide()

        val bounce = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)

        // evento alla pressione del tasto "ENTRA"
        bottoneEntra.setOnClickListener {

            bottoneEntra.startAnimation(bounce)

            // oggetto con funzioni di utilita'
            val ts = TestStringhe()
            val db = Database(this)

            // prendo username, password, conferma inseriti da tastiera
            val nome = nomeSacerdote.text.toString()
            val casata = diocesiSacerdote.text.toString()
            val parola = parolaSacerdote.text.toString()
            val conf = parola

            val viewPopup = layoutInflater.inflate(R.layout.popupinformativo, null)
            val btnProcedi = viewPopup.btnprocedi

            val popupBuilder: AlertDialog.Builder? = AlertDialog.Builder(this).setView(viewPopup)
            val popupReg: AlertDialog = popupBuilder!!.create()

            // inputValidi vale true se non ho alcun tipo di problema con gli input
            if (ts.testRegistrazione(nome, parola, conf)) {

                if (!db.testUtente(nome, casata, parola)) {
                    btnProcedi.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    btnProcedi.text = "Benvenuto a un nuovo pezzente."

                    popupReg.show()

                    db.aggiungiCredenziali(nome, casata, parola)

                    plebeoCorrente.nome = nome
                    plebeoCorrente.casata = casata

                    btnProcedi.setOnClickListener {
                        startActivity(Intent(this@LoginPlebeo, HomePlebeo::class.java)
                            .putExtra("baiocchiPassati",intent.getIntExtra("baiocchiPassati",0)))
                    }

                } else{
                    btnProcedi.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    btnProcedi.text = "Bentornato, pezzente."
                    popupReg.show()

                    btnProcedi.postDelayed({
                        popupReg.dismiss()
                        startActivity(Intent(this@LoginPlebeo, HomePlebeo::class.java))
                    },1000)
                }
            }
            // altrimenti ho avuto qualche problema in input,
            // quindi cerco il problema e modifico il messaggio
            else {

                var messaggio = ""

                // se la password una qualunque cosa che non va,
                // indago sul problema specifico.
                if (!ts.testPassword(parola, conf)) {

                    messaggio += "Problemi con la parola d'ordine:\n"
                    // se la password non e' stata inserita
                    if (ts.strVuota(parola)) {
                        messaggio += "- alzare la voce\n"
                    }

                    // se la password non contiene almeno una minuscola
                    if (!ts.strConMinuscola(parola)) {
                        messaggio += "- ci vuole meno enfasi su almeno una lettera\n"
                    }

                    // se la password non contiene almeno una maiuscola
                    if (!ts.strConMaiuscola(parola)) {
                        messaggio += "- ci vuole più enfasi su almeno una lettera\n"
                    }

                    // se la password non contiene almeno un numero
                    if (!ts.strConNum(parola)) {
                        messaggio += "- davvero non ti ricordi il numero?\n"
                    }

                    // se la password e' minore di 8 caratteri
                    if (ts.strCorta(parola)) {
                        messaggio += "- troppo corta, pezzente.\n"
                    }
                }

                // se l'username ha una qualunque cosa che non va,
                // indago sul problema specifico.
                if (!ts.testUsername(nome)) {

                    if (messaggio != "") messaggio += "\n"

                    messaggio += "Problemi col nome:\n"

                    // se non e' stato inserito l'username
                    if (ts.strVuota(nome)) {
                        messaggio += "- come ti chiami, padre?\n"
                    }

                    // se l'username ha spazi interni
                    if (ts.strSpaziInterni(nome)) {
                        messaggio += "- Onnipotenzo ha bandito i secondi nomi"
                    }
                }
                btnProcedi.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                btnProcedi.text = messaggio

                popupReg.show()

                btnProcedi.setOnClickListener {
                    popupReg.dismiss()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.d("NON SI PUO'","FREGATO")
    }
}
