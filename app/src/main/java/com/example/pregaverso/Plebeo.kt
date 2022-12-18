package com.example.pregaverso

import android.content.Context

class Plebeo {
    var nome = ""
    var casata = ""
    var baiocchi = 0

    fun daiBaiocchi(nBaiocchi : Int,context: Context){
        val db = Database(context)
        baiocchi+=nBaiocchi
        db.aggiungiBaiocchiAPlebeo(nome,casata,nBaiocchi)
    }

    fun togliBaiocchi(nBaiocchi: Int, context: Context) : Boolean{
        if(baiocchi >= nBaiocchi ){
            val db = Database(context)
            baiocchi -= nBaiocchi
            db.togliBaiocchiAPlebeo(nome,casata,nBaiocchi)
            return true
        } else {
            baiocchi = 0
            return false
        }
    }

    fun compraMiracolo(descrizione : String, santo : String,context : Context){
    }

    fun commentaMiracolo(descr: String, santo : String, commento : String,context: Context){
        val db = Database(context)
        db.aggiungiCommento(descr,santo,commento)
    }
}