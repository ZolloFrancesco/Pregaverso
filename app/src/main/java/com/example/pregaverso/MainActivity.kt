package com.example.pregaverso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Database(this)

        // Commento di prova branch Filippo_Farnesi
        db.aggiungiPlebeo("marco","imbecilli",30)
        db.aggiungiPlebeo("lorenzo","forti",100)

        val x = db.prendiPlebei()

        Log.d("QUALCOSA","${x[0].nome} ${x[0].casata} ${x[0].baiocchi}")
        Log.d("QUALCOSA","${x[1].nome} ${x[1].casata} ${x[1].baiocchi}")

    }
}