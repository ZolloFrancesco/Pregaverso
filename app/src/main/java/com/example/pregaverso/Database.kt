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

    // Restituisce la lista di tutti i Plebei presenti al momento della chiamata all'interno del Database.
    // Se non ci sono Plebei, restituisce una lista di Plebei vuota.
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

    // Restituisce la lista di tutti i Sacerdoti presenti al momento della chiamata all'interno del Database.
    // Se non ci sono Sacerdoti, restituisce una lista di Sacerdoti vuota.
    fun prendiSacerdoti() : ArrayList<Sacerdote>{

        val db : SQLiteDatabase = readableDatabase

        val listaDaRestituire : ArrayList<Sacerdote> = ArrayList()

        val comando = "SELECT * FROM $NOME_TABELLA_SACERDOTI"
        val cursore : Cursor = db.rawQuery(comando,null)

        if(cursore.moveToFirst()){
            do {
                val sacerdotePrelevato = Sacerdote()

                sacerdotePrelevato.nome = cursore.getString(0)
                sacerdotePrelevato.diocesi = cursore.getString(1)

                listaDaRestituire.add(sacerdotePrelevato)
            } while(cursore.moveToNext())
        }

        cursore.close()

        return listaDaRestituire
    }

    // aggiunge nBaiocchi al Plebeo univocamente identificato dalla coppia {nomePlebeo,casata} all'interno del Database.
    // restituisce false se non ha modificato nessun Plebeo, se ha modificato troppi Plebei o se c'e' stato un errore sconosciuto.
    // restituisce true in caso di corretta modifica.
    fun aggiungiBaiocchiAPlebeo(nomePlebeo : String, casata : String, nBaiocchi : Int) : Boolean{

        if(nBaiocchi < 0 || nomePlebeo == "" || casata == ""){
            Log.d("AGGIUNGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata","INPUT NON VALIDI")
            return false
        }

        val db : SQLiteDatabase = writableDatabase

        val listaPlebei = prendiPlebei()

        var baiocchiVecchi = 0

        for(i in 0 until listaPlebei.size){

            if( ( listaPlebei[i].nome == nomePlebeo ) && ( listaPlebei[i].casata == casata ) ){

                baiocchiVecchi = listaPlebei[i].baiocchi
            }
        }

        val modificato = ContentValues()

        modificato.put(PLEBEI_NOME,nomePlebeo)
        modificato.put(PLEBEI_CASATA,casata)
        modificato.put(PLEBEI_BAIOCCHI,baiocchiVecchi + nBaiocchi)

        val nModificati = db.update(NOME_TABELLA_PLEBEI, modificato, "$PLEBEI_NOME=? AND $PLEBEI_CASATA=?", arrayOf(nomePlebeo,casata))

        if (nModificati == 1){
            Log.d("AGGIUNGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "SUCCESSO")
            return true
        }

        if(nModificati == 0){
            Log.d("AGGIUNGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "NESSUN PLEBEO MODIFICATO")
            return false
        }

        if(nModificati > 1){
            Log.d("AGGIUNGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "TROPPI PLEBEI MODIFICATI!")
            return false
        }
        Log.d("AGGIUNGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "ERRORE SCONOSCIUTO")
        return false
    }

    // inserisce un nuovo Plebeo con nome nome, casata casata e con un numero di Baiocchi pari a nBaiocchi.
    // restituisce false non sono stati passati input validi.
    // restituisce true in caso di corretto inserimento.
    fun aggiungiPlebeo(nome : String, casata : String, nBaiocchi : Int) : Boolean{

        if(nBaiocchi < 0 || nome == "" || casata == ""){
            Log.d("INSERIMENTO DI $nome DI CASA $casata","INPUT NON VALIDI")
            return false
        }

        val db = writableDatabase

        val daAggiungere = ContentValues()
        daAggiungere.put(PLEBEI_NOME,nome)
        daAggiungere.put(PLEBEI_CASATA,casata)
        daAggiungere.put(PLEBEI_BAIOCCHI,nBaiocchi)

        val test = db?.insert(NOME_TABELLA_PLEBEI, null, daAggiungere)

        if(test?.toInt() == -1){
            Log.d("INSERIMENTO DI $nome DI CASA $casata","FALLITO")
            return false
        }

        Log.d("INSERIMENTO DI $nome DI CASA $casata","SUCCESSO")
        return true
    }

    // elimina un Plebeo con nome nome e casata casata dal Database.
    // restituisce false se gli input non sono validi, se non ha eliminato niente o se ha eliminato piu' di una riga.
    // restituisce true in caso di eliminazione di un Plebeo (non assicura la correttezza della scelta dell'eliminato).
    fun eliminaPlebeo(nome : String, casata : String) : Boolean{
        if(nome == "" || casata == "") return false

        val db = writableDatabase

        val nRigheEliminate = db.delete(NOME_TABELLA_PLEBEI,"$PLEBEI_NOME=? AND $PLEBEI_CASATA=?", arrayOf(nome,casata))

        if(nRigheEliminate == 0){
            Log.d("ELIMINO $nome DI CASA $casata","NESSUNA RIGA ELIMINATA")
            return false
        }

        if(nRigheEliminate>1){
            Log.d("ELIMINO $nome DI CASA $casata","ELIMINATE PIU' PLEBEI!")
            return false
        }

        Log.d("ELIMINO $nome DI CASA $casata","SUCCESSO")
        return true
    }

    // restituisce una lista contenente tutti i commenti di un miracolo specifico
    // in caso non ci siano commenti restituisce una lista vuota
    fun prendiCommento(descr: String, nomeSanto: String) : ArrayList<String>{

        val db = readableDatabase
        val commenti : ArrayList<Commento> = ArrayList()
        val cur = db.rawQuery("SELECT * FROM $NOME_TABELLA_COMMENTIMIRACOLI", null)
        if (cur.moveToFirst()){
            do{
                val commentoNuovo = Commento()
                commentoNuovo.descrMiracolo = cur.getString(0)
                commentoNuovo.nomeSanto = cur.getString(1)
                commentoNuovo.commento = cur.getString(2)
                commenti.add(commentoNuovo)
            } while(cur.moveToNext())
        }

        val listaDaRestituire : ArrayList<String> = ArrayList()

        for (i in 0 until commenti.size){
            if(commenti[i].descrMiracolo.equals(descr) && commenti[i].nomeSanto.equals(nomeSanto)){
                listaDaRestituire.add(commenti[i].commento)
            }
        }

        return listaDaRestituire
    }

    // verifica se un utente specifico è presente nel database
    // in caso di riscontro positivo restituisce true, altrimenti false
    fun testUtente(nome: String, casataDiocesi: String, parolaDOrdine: String) : Boolean{

        val db = readableDatabase
        val cur = db.rawQuery("SELECT * FROM $NOME_TABELLA_LOGIN", null)
        if (cur.moveToFirst()){

            do {

                if (cur.getString(0).equals(nome) && cur.getString(1).equals(casataDiocesi) && cur.getString(2).equals(parolaDOrdine)) {
                    return true
                }

            } while (cur.moveToNext())

        }
        return false
    }

}