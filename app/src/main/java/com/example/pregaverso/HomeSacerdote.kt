package com.example.pregaverso

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.homesacerdote.*
import kotlinx.android.synthetic.main.popupbaiocchisacerdote.*
import kotlinx.android.synthetic.main.popupbaiocchisacerdote.view.*
import kotlinx.android.synthetic.main.popupinserimentomiracolo.view.*

class HomeSacerdote : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.homesacerdote)

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        // nascondo la ActionBar per estetica
        supportActionBar?.hide()

        // dichiaro un handler per database per eseguire le operazioni messe a disposizione
        val db = Database(this)

        // dichiaro un array di task
        val listaMiracoli = db.prendiMiracoli()

        // dichiaro un adapter che gestisce la visualizzazione di todoListItem
        val adapter = AdattatoreMiracoliSacerdote(listaMiracoli, this)
        recyclerViewSacerdote.adapter = adapter

        // dichiaro un LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)
        recyclerViewSacerdote.layoutManager = layoutManager

        // helper che permette di intercettare uno swipe
        ItemTouchHelper(

            // oggetto che intercetta sia gli swipe verso destra che quelli verso sinistra
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                // non sovrascrivo la funzione onMove()
                override fun onMove(
                    rw: RecyclerView,
                    vh: RecyclerView.ViewHolder,
                    trg: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {

                    // se c'?? stato uno swipe verso sinistra
                    if (dir == ItemTouchHelper.LEFT) {

                        // chiamo il metodo che prende come argomento l'indice dell'elemento
                        // che verr?? eliminato dal viewRecycler (aggiorna anche il database)
                        adapter.cancellaByPosition(vh.adapterPosition)

                    }
                }
            }

            // il touchHelper lavora sulla recyclerView
        ).attachToRecyclerView(recyclerViewSacerdote)


        // listener per il pulsante flottante
        btnAggiungiMiracolo.setOnClickListener {

            val dialogBuilder: AlertDialog.Builder?
            val dialog: AlertDialog?
            val view = LayoutInflater.from(this).inflate(R.layout.popupinserimentomiracolo, null, false)
            val btnInserisci = view.btnInserisci
            dialogBuilder = AlertDialog.Builder(this).setView(view)
            dialog = dialogBuilder!!.create()
            dialog.show()
            btnInserisci.setOnClickListener {
                if (view.descrInserita.text.toString() != "" || view.nomesanto.text.toString() != "" || view.costoInserito.text.toString() != "") {
                    if( db.aggiungiMiracoli(    view.descrInserita.text.toString(),
                                                view.nomesanto.text.toString(),
                                                view.costoInserito.text.toString().toInt(),
                                                view.testoInserito.text.toString()
                                            )){
                        val nuovo = Miracolo()
                        nuovo.descr = view.descrInserita.text.toString()
                        nuovo.nomeSanto = view.nomesanto.text.toString()
                        nuovo.costo = view.costoInserito.text.toString().toInt()
                        nuovo.testo = view.testoInserito.text.toString()
                        listaMiracoli.add(nuovo)
                        adapter.notifyItemInserted(adapter.miracoli.size)
                    }
                    dialog!!.dismiss()
                }
            }

            btnLoginPlebeo.setOnClickListener{
                val viewPopup = layoutInflater.inflate(R.layout.popupbaiocchisacerdote, null)

                val popupBuilder: AlertDialog.Builder? = AlertDialog.Builder(this).setView(viewPopup)
                val popup: AlertDialog = popupBuilder!!.create()

                popup.show()

                val btnConferma = viewPopup.btnConferma
                val inseriti = viewPopup.nBaiocchiInseriti

                btnConferma.setOnClickListener {
                    popup.dismiss()
                    /*
                    if(inseriti.text.toString().toInt() < 0){
                        btnConferma.text = ""
                        btnConferma.hint = "calmo, padre."
                    } else if(inseriti.text.toString().toInt() > 80){

                        btnConferma.text = ""
                        btnConferma.hint = "forse intendete ${(inseriti.text.toString().toInt()%10)}?"
                    } else

                     */
                    //baiocchiPassati = inseriti.text.toString().toInt()
                    startActivity(Intent(this@HomeSacerdote, LoginPlebeo::class.java).putExtra("baiocchiPassati",inseriti.text.toString().toInt()))
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.d("NON SI PUO'","FREGATO")
    }
}