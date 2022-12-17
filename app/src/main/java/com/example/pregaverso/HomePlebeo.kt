package com.example.pregaverso

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardviewmiracoliplebeo.*
import kotlinx.android.synthetic.main.home_sacerdote.*
import kotlinx.android.synthetic.main.homeplebeo.*
import kotlinx.android.synthetic.main.popupbaiocchisacerdote.*
import kotlinx.android.synthetic.main.popupbaiocchisacerdote.view.*
import kotlinx.android.synthetic.main.popupinserimentomiracolo.view.*

var plebeoCorrente = Plebeo()

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
        recyclerView.adapter = adapter

        // dichiaro un LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        btnCompra.setOnClickListener {

            val bounce = AnimationUtils.loadAnimation(applicationContext,R.anim.bounce)
            btnCompra.startAnimation(bounce)

            if(floatBaiocchi.text.toString().toInt() > baiocchiNecessari.text.toString().toInt()){
                floatBaiocchi.startAnimation(bounce)
                floatBaiocchi.text = (floatBaiocchi.text.toString().toInt() - baiocchiNecessari.text.toString().toInt()).toString()
                //adapter.cancellaByPosition(adapter.adapterPosition)
            }
        }

    }
}