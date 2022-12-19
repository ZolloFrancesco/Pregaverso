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

    fun cancellaByPosition(pos: Int) {
        val db = Database(context)
        db.eliminaMiracolo(miracoli[pos].descr,miracoli[pos].nomeSanto)
        miracoli.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {
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

        fun bindView(miracolo: Miracolo) {

            val db = Database(context)

            testo.text = miracolo.testo
            costo.text = miracolo.costo.toString()
            nomeSanto.text = miracolo.nomeSanto
            descrMiracolo.text = miracolo.descr

            if (testo.text.toString() == ""){
                frecciaDescr.visibility = View.GONE
                spiegMiracolo.visibility = View.GONE
                testo.visibility = View.GONE
            }

            val commDb = db.prendiCommento(miracolo.descr,miracolo.nomeSanto)

            if(commDb.size == 0){
                frecciaComm.visibility = View.GONE
                titcom.visibility = View.GONE
                commenti.visibility = View.GONE
            } else{
                var risultato = ""

                for(i in 0 until commDb.size){
                    risultato += "$i - ${commDb[i]}\n"
                }

                commenti.text = risultato
            }

            bottoneCompra.setOnClickListener {
                if(plebeoCorrente.baiocchi > costo.text.toString().toInt()){
                    db.eliminaMiracolo(descrMiracolo.text.toString(),nomeSanto.text.toString())
                    plebeoCorrente.togliBaiocchi(costo.text.toString().toInt(),context)
                    cancellaByPosition(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

    }
}