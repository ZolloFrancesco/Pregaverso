package com.example.pregaverso

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdattatoreMiracoliPlebeo(var miracoli : ArrayList<Miracolo>, private val context : Context) : RecyclerView.Adapter<AdattatoreMiracoliPlebeo.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardviewmiracoloplebeo,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(miracoli[position])
    }

    override fun getItemCount(): Int {
        return miracoli.size
    }

    // cancella l'elemento di posizione i nella lista del recyclerView
    fun cancellaByPosition(pos: Int) {

        val db = Database(context)

        // elimino il miracolo dal databse
        db.eliminaMiracolo(miracoli[pos].descr,miracoli[pos].nomeSanto)

        // rimuovo il miracolo dalla lista del recyclerView
        miracoli.removeAt(pos)

        // notifico il cambiamento al recycler
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {

        // istanzio variabili riferite all'UI
        var descrMiracolo = itemView.findViewById(R.id.descrMiracoloPlebeo) as TextView
        var nomeSanto = itemView.findViewById(R.id.nomeSantoPlebeo) as TextView
        var costo = itemView.findViewById(R.id.nBaiocchiPlebeo) as TextView
        var testo = itemView.findViewById(R.id.testoMiracoloPlebeo) as TextView
        var titcom =  itemView.findViewById(R.id.intCommPlebeo) as TextView
        var spiegMiracolo = itemView.findViewById(R.id.intDescrizionePlebeo) as TextView
        var commenti = itemView.findViewById(R.id.commentiMiracoloPlebeo) as TextView

        var frecciaDescr = itemView.findViewById(R.id.frecciaDescrizionePlebeo) as ImageView
        var frecciaComm = itemView.findViewById(R.id.frecciaCommentiPlebeo) as ImageView

        var bottoneCompra = itemView.findViewById(R.id.btnCompraPlebeo) as TextView


        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        // adatta la card al recyclerView
        fun bindView(miracolo: Miracolo) {

            val db = Database(context)

            // inserisco i valori degli attributi di miracolo nei corrispondenti
            // elementi grafici
            testo.text = miracolo.testo
            costo.text = miracolo.costo.toString()
            nomeSanto.text = miracolo.nomeSanto
            descrMiracolo.text = miracolo.descr

            // se il testo e' vuoto
            if (testo.text.toString() == ""){

                // faccio scomparire l'intera sezione della descrizione
                frecciaDescr.visibility = View.GONE
                spiegMiracolo.visibility = View.GONE
                testo.visibility = View.GONE
            }

            // commDb contiene tutti i commenti del miracolo
            val commDb = db.prendiCommento(miracolo.descr,miracolo.nomeSanto)

            // se commDb e' vuoto
            if(commDb.size == 0){

                // faccio scomparire l'intera sezione dei commenti
                frecciaComm.visibility = View.GONE
                titcom.visibility = View.GONE
                commenti.visibility = View.GONE
            }
            // altrimenti
            else{

                // variabile di utilita'
                var risultato = ""

                // per ogni elemento di commDb
                for(i in 0 until commDb.size){

                    // concateno il commento
                    risultato += "$i - ${commDb[i]}\n"
                }

                // inserisco i commenti appena trovati nell'UI
                commenti.text = risultato
            }

            // listener del bottone che permette di comprare
            bottoneCompra.setOnClickListener {

                // se il plebeo che ha fatto accesso puo' permettersi il miracolo
                if(plebeoCorrente.baiocchi > costo.text.toString().toInt()){

                    // elimino il miracolo
                    db.eliminaMiracolo(descrMiracolo.text.toString(),nomeSanto.text.toString())

                    // tolgo i baiocchi al plebeo (nel database)
                    plebeoCorrente.togliBaiocchi(costo.text.toString().toInt(),context)

                    // cancello il miracolo nel recycler
                    cancellaByPosition(adapterPosition)

                    // notifico l'eliminazione del miracolo
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

    }
}