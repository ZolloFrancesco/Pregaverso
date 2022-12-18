package com.example.pregaverso

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.loginsacerdote.*
import kotlinx.android.synthetic.main.homesacerdote.*
import kotlinx.android.synthetic.main.popupbaiocchisacerdote.*
import kotlinx.android.synthetic.main.popupbaiocchisacerdote.view.*
import kotlinx.android.synthetic.main.popupinformativo.view.*

class LoginSacerdote : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginsacerdote)

        supportActionBar?.hide()

        val bounce = AnimationUtils.loadAnimation(applicationContext,R.anim.bounce)

        // evento alla pressione del tasto "ENTRA"
        bottoneEntra.setOnClickListener {

            bottoneEntra.startAnimation(bounce)

            // oggetto con funzioni di utilita'
            val ts = TestStringhe()
            val db = Database(this)

            // prendo username, password, conferma inseriti da tastiera
            val nome = nomeSacerdote.text.toString()
            val diocesi = diocesiSacerdote.text.toString()
            val parola = parolaSacerdote.text.toString()
            val conf = parola

            val viewPopup = layoutInflater.inflate(R.layout.popupinformativo, null)
            val btnProcedi = viewPopup.btnprocedi

            val popupBuilder: AlertDialog.Builder? = AlertDialog.Builder(this).setView(viewPopup)
            val popupReg: AlertDialog = popupBuilder!!.create()

            // inputValidi vale true se non ho alcun tipo di problema con gli input
            if (ts.testRegistrazione(nome, parola, conf)) {

                if (!db.testUtente(nome, diocesi, parola)) {
                    btnProcedi.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    btnProcedi.text = "Benvenuto, Padre."

                    popupReg.show()

                    db.aggiungiCredenziali(nome, diocesi, parola)

                    btnProcedi.setOnClickListener {
                        popupReg.dismiss()
                        startActivity(Intent(this@LoginSacerdote, HomeSacerdote::class.java))
                    }
                } else{
                    btnProcedi.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    btnProcedi.text = "Bentornato, Padre."

                    popupReg.show()

                    btnProcedi.postDelayed({
                        popupReg.dismiss()
                        startActivity(Intent(this@LoginSacerdote, HomeSacerdote::class.java))
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
                        messaggio += "- davvero non rimembra il numero?\n"
                    }

                    // se la password e' minore di 8 caratteri
                    if (ts.strCorta(parola)) {
                        messaggio += "- non può essere, troppo corta\n"
                    }
                }

                // se l'username ha una qualunque cosa che non va,
                // indago sul problema specifico.
                if (!ts.testUsername(nome)) {

                    if(messaggio != "") messaggio += "\n"

                    messaggio+="Problemi col nome:\n"

                    // se non e' stato inserito l'username
                    if (ts.strVuota(nome)) {
                        messaggio += "-come si chiama, padre?\n"
                    }

                    // se l'username ha spazi interni
                    if (ts.strSpaziInterni(nome)) {
                        messaggio += "-Onnipotenzo ha bandito i secondi nomi"
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
}