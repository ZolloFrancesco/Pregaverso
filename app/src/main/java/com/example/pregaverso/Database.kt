package com.example.pregaverso

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context : Context) : SQLiteOpenHelper(context ,NOME_DATABASE, null, VERSIONE_DATABASE){
    fun onUpgrade2(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    override fun onCreate(db: SQLiteDatabase?) {
        return
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}