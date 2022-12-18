package com.example.pregaverso

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdattatoreMiracoliSacerdote(var miracoli : ArrayList<Miracolo>, private val context : Context) : RecyclerView.Adapter<AdattatoreMiracoliSacerdote.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardviewmiracolosacerdote,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(miracoli[position])
    }

    override fun getItemCount(): Int {
        return miracoli.size
    }

    fun cancellaByPosition(pos: Int) {
        val db = Database(context)
        db.eliminaMiracolo(miracoli[pos].descr,miracoli[pos].nomeSanto)
        miracoli.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {
        var descrMiracolo = itemView.findViewById(R.id.descrMiracoloSacerdote) as TextView
        var nomeSanto = itemView.findViewById(R.id.nomeSantoSacerdote) as TextView
        var costo = itemView.findViewById(R.id.nBaiocchiSacerdote) as TextView
        var testo = itemView.findViewById(R.id.testoMiracoloSacerdote) as TextView
        var commenti = itemView.findViewById(R.id.commentiMiracoloSacerdote) as TextView
        var titcom =  itemView.findViewById(R.id.intCommSacerdote) as TextView
        var spiegMiracolo = itemView.findViewById(R.id.intDescrizioneSacerdote) as TextView

        var frecciaDescr = itemView.findViewById(R.id.frecciaDescrizioneSacerdote) as ImageView
        var frecciaComm = itemView.findViewById(R.id.frecciaCommentiSacerdote) as ImageView



        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        fun bindView(miracolo: Miracolo) {
            val db = Database(context)

            testo.text = miracolo.testo
            costo.text = miracolo.costo.toString()
            nomeSanto.text = miracolo.nomeSanto
            descrMiracolo.text = miracolo.descr

            if (testo.text.toString() == ""){
                frecciaDescr.visibility = GONE
                spiegMiracolo.visibility = GONE
                testo.visibility = GONE
            }


            val commDb = db.prendiCommento(miracolo.descr,miracolo.nomeSanto)

            if(commDb.size == 0){
                frecciaComm.visibility = GONE
                titcom.visibility = GONE
                commenti.visibility = GONE
            } else{
                var risultato = "0 - commento di test\n"

                for(i in 0 until commDb.size){
                    risultato += "$i - ${commDb[i]}\n"
                }

                commenti.text = risultato
            }
        }

    }

}