package com.example.pregaverso

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.homeplebeo.*
import kotlinx.android.synthetic.main.popupinserimentomiracolo.view.*
import kotlinx.android.synthetic.main.popuptrappola.*
import kotlinx.android.synthetic.main.popuptrappola.view.*

class HomePlebeo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeplebeo)

        // nascondo la ActionBar per estetica
        supportActionBar?.hide()

        // dichiaro un handler per database per eseguire le operazioni messe a disposizione
        val db = Database(this)

        // dichiaro un array di task
        val listaMiracoli = db.prendiMiracoli()

        // dichiaro un adapter che gestisce la visualizzazione di todoListItem
        val adapter = AdattatoreMiracoliPlebeo(listaMiracoli, this)
        recyclerViewPlebeo.adapter = adapter

        // dichiaro un LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)
        recyclerViewPlebeo.layoutManager = layoutManager

        val dialogBuilder: AlertDialog.Builder?
        val dialog: AlertDialog?
        val view = LayoutInflater.from(this).inflate(R.layout.popuptrappola, null, false)
        val btnGiura = view.btnGiura
        val inseritiTrappola = view.bugiaTrappola
        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog.show()
        btnGiura.setOnClickListener {
                dialog!!.dismiss()
                if(inseritiTrappola.text.toString().toInt() != intent.getIntExtra("baiocchiPassati",0)) {
                    startActivity(Intent(this@HomePlebeo, Trappola::class.java))
                }
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.d("NON SI PUO'","FREGATO")
    }
}