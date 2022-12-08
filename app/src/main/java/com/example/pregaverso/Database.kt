package com.example.pregaverso

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/*
            +--------------------------------------------------+
            |               $NOME_TABELLA_PLEBEI               |
            +--------------+----------------+------------------+
            | $PLEBEI_NOME | $PLEBEI_CASATA | $PLEBEI_BAIOCCHI |
            +--------------+----------------+------------------+
            |              |                |                  |
            +--------------+----------------+------------------+
            |              |                |                  |
            +--------------+----------------+------------------+
            |              |                |                  |
            +--------------+----------------+------------------+
 */

/*
            +--------------------------------------+
            |        $NOME_TABELLA_SACERDOTI       |
            +-----------------+--------------------+
            | $SACERDOTI_NOME | $SACERDOTI_DIOCESI |
            +-----------------+--------------------+
            |                 |                    |
            +-----------------+--------------------+
            |                 |                    |
            +-----------------+--------------------+
            |                 |                    |
            +-----------------+--------------------+
 */

/*
            +---------------------------------------------------------------+
            |                     $NOME_TABELLA_MIRACOLI                    |
            +-----------------------+---------------------+-----------------+
            | $MIRACOLI_DESCRIZIONE | $MIRACOLI_NOMESANTO | $MIRACOLI_COSTO |
            +-----------------------+---------------------+-----------------+
            |                       |                     |                 |
            +-----------------------+---------------------+-----------------+
            |                       |                     |                 |
            +-----------------------+---------------------+-----------------+
            |                       |                     |                 |
            +-----------------------+---------------------+-----------------+
 */

/*
            +------------------------------------------------------------------------------------------+
            |                              $NOME_TABELLA_COMMENTIMIRACOLI                              |
            +-------------------------------+-----------------------------+----------------------------+
            | $COMMENTIMIRACOLI_DESCRIZIONE | $COMMENTIMIRACOLI_NOMESANTO | $COMMENTIMIRACOLI_COMMENTO |
            +-------------------------------+-----------------------------+----------------------------+
            |                               |                             |                            |
            +-------------------------------+-----------------------------+----------------------------+
            |                               |                             |                            |
            +-------------------------------+-----------------------------+----------------------------+
            |                               |                             |                            |
            +-------------------------------+-----------------------------+----------------------------+
 */

/*
            +-----------------------------------------------------------+
            |                    $NOME_TABELLA_LOGIN                    |
            +-------------+----------------------+----------------------+
            | $LOGIN_NOME | $LOGIN_CASATADIOCESI | $LOGIN_PAROLADORDINE |
            +-------------+----------------------+----------------------+
            |             |                      |                      |
            +-------------+----------------------+----------------------+
            |             |                      |                      |
            +-------------+----------------------+----------------------+
            |             |                      |                      |
            +-------------+----------------------+----------------------+
 */


class Database(context : Context) : SQLiteOpenHelper(context ,NOME_DATABASE, null, VERSIONE_DATABASE){

    override fun onCreate(db: SQLiteDatabase?) {

        var comando = "CREATE TABLE $NOME_TABELLA_PLEBEI ($PLEBEI_NOME TEXT , $PLEBEI_CASATA TEXT ,  $PLEBEI_BAIOCCHI INTEGER, PRIMARY KEY($PLEBEI_NOME,$PLEBEI_CASATA))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA PLEBEI","SUCCESSO")

        comando = "CREATE TABLE $NOME_TABELLA_SACERDOTI ($SACERDOTI_NOME TEXT, $SACERDOTI_DIOCESI TEXT, PRIMARY KEY($SACERDOTI_NOME,$SACERDOTI_DIOCESI))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA SACERDOTI","SUCCESSO")

        comando = "CREATE TABLE $NOME_TABELLA_MIRACOLI ($MIRACOLI_DESCRIZIONE TEXT, $MIRACOLI_NOMESANTO TEXT ,  $MIRACOLI_COSTO INTEGER, PRIMARY KEY($MIRACOLI_DESCRIZIONE,$MIRACOLI_NOMESANTO))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA MIRACOLI","SUCCESSO")

        comando = "CREATE TABLE $NOME_TABELLA_COMMENTIMIRACOLI ($COMMENTIMIRACOLI_DESCRIZIONE TEXT, $COMMENTIMIRACOLI_NOMESANTO TEXT,  $COMMENTIMIRACOLI_COMMENTO TEXT)"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA COMMENTIMIRACOLI","SUCCESSO")

        comando = "CREATE TABLE $NOME_TABELLA_LOGIN ($LOGIN_NOME TEXT, $LOGIN_CASATADIOCESI TEXT,  $LOGIN_PAROLADORDINE , PRIMARY KEY($LOGIN_NOME,$LOGIN_CASATADIOCESI,$LOGIN_PAROLADORDINE))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA LOGIN","SUCCESSO")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $NOME_TABELLA_PLEBEI")
        Log.d("ELIMINAZIONE TABELLA LOGIN","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $NOME_TABELLA_SACERDOTI")
        Log.d("ELIMINAZIONE TABELLA SACERDOTI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $NOME_TABELLA_MIRACOLI")
        Log.d("ELIMINAZIONE TABELLA MIRACOLI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $NOME_TABELLA_COMMENTIMIRACOLI")
        Log.d("ELIMINAZIONE TABELLA COMMENTIMIRACOLI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $NOME_TABELLA_LOGIN")
        Log.d("ELIMINAZIONE TABELLA LOGIN","SUCCESSO")

        onCreate(db)
        Log.d("CREAZIONE DATABASE","SUCCESSO")
    }

    fun prendiPlebei() : ArrayList<Plebeo>{
        val db : SQLiteDatabase = readableDatabase
        val listaDaRestituire : ArrayList<Plebeo> = ArrayList()
        val comando = "SELECT * FROM $NOME_TABELLA_PLEBEI"
        val cursore : Cursor = db.rawQuery(comando,null)
        if(cursore.moveToFirst()){
            do {
                val plebeoPrelevato = Plebeo()
                plebeoPrelevato.nome = cursore.getString(0)
                plebeoPrelevato.casata = cursore.getString(1)
                plebeoPrelevato.baiocchi = cursore.getInt(2)
                listaDaRestituire.add(plebeoPrelevato)
            } while(cursore.moveToNext())
        }
        cursore.close()
        return listaDaRestituire
    }

}