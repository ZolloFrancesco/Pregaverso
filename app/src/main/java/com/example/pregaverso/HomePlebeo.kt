package com.example.pregaverso

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cardviewmiracoloplebeo.*
import kotlinx.android.synthetic.main.homesacerdote.*
import kotlinx.android.synthetic.main.homeplebeo.*
import kotlinx.android.synthetic.main.popupinserimentomiracolo.view.*

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
    }
}