package com.example.pregaverso

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login_sacerdote.*
import kotlinx.android.synthetic.main.popupregsacerdote.view.*

class LoginPlebeo : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sacerdote)

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
            val diocesi = diocesiSacerdote.text.toString()
            val parola = parolaSacerdote.text.toString()
            val conf = parola

            val viewPopup = layoutInflater.inflate(R.layout.popupregsacerdote, null)
            val btnProcedi = viewPopup.btnprocedi

            val popupBuilder: AlertDialog.Builder? = AlertDialog.Builder(this).setView(viewPopup)
            val popupReg: AlertDialog = popupBuilder!!.create()

            // inputValidi vale true se non ho alcun tipo di problema con gli input
            if (ts.testRegistrazione(nome, parola, conf)) {

                if (!db.testUtente(nome, diocesi, parola)) {

                    btnProcedi.setText("Registrazione \n Completata")

                    popupReg.show()

                    db.aggiungiCredenziali(nome, diocesi, parola)

                    btnProcedi.setOnClickListener {
                        startActivity(Intent(this@LoginPlebeo, HomePlebeo::class.java))
                    }

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
                btnProcedi.setText(messaggio)

                popupReg.show()

            }
        }
    }
}
