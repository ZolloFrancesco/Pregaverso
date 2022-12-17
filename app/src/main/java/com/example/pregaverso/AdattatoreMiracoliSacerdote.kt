package com.example.pregaverso

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdattatoreMiracoliSacerdote(var miracoli : ArrayList<Miracolo>, private val context : Context) : RecyclerView.Adapter<AdattatoreMiracoliSacerdote.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardviewmiracolosacerdote2,parent, false)
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
        var descrMiracolo = itemView.findViewById(R.id.descrMiracolo) as TextView
        var nomeSanto = itemView.findViewById(R.id.nomeSanto) as TextView
        var costo = itemView.findViewById(R.id.nBaiocchi) as TextView
        var testo = itemView.findViewById(R.id.testoMiracolo) as TextView

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        fun bindView(miracolo: Miracolo) {
            testo.text = miracolo.testo
            costo.text = miracolo.costo.toString()
            nomeSanto.text = miracolo.nomeSanto
            descrMiracolo.text = miracolo.descr
        }

    }

}