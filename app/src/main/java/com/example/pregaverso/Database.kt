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
            +---------------------------------------------------------------------------------
            |                     $NOME_TABELLA_MIRACOLI                    |                |
            +-----------------------+---------------------+-----------------+-----------------
            | $MIRACOLI_DESCRIZIONE | $MIRACOLI_NOMESANTO | $MIRACOLI_COSTO | $MIRACOLI_TESTO|
            +-----------------------+---------------------+-----------------+-----------------
            |                       |                     |                 |                |
            +-----------------------+---------------------+-----------------+-----------------
            |                       |                     |                 |                |
            +-----------------------+---------------------+-----------------+-----------------
            |                       |                     |                 |                |
            +-----------------------+---------------------+-----------------+-----------------
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

        var comando = "CREATE TABLE $N_T_P ($P_NOME TEXT , $P_CASATA TEXT ,  $P_BAIOCCHI INTEGER, PRIMARY KEY($P_NOME,$P_CASATA))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA PLEBEI","SUCCESSO")

        comando = "CREATE TABLE $N_T_S ($S_NOME TEXT, $S_DIOCESI TEXT, PRIMARY KEY($S_NOME,$S_DIOCESI))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA SACERDOTI","SUCCESSO")

        comando = "CREATE TABLE $N_T_M ($M_DESC TEXT, $M_NOMESANTO TEXT, $M_COSTO INTEGER, $M_TESTO  TEXT, PRIMARY KEY($M_DESC,$M_NOMESANTO))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA MIRACOLI","SUCCESSO")

        comando = "CREATE TABLE $N_T_C ($C_DESC TEXT, $C_NOMESANTO TEXT,  $C_COMM TEXT, FOREIGN KEY($C_DESC) REFERENCES $N_T_M($M_DESC), FOREIGN KEY($C_NOMESANTO) REFERENCES $N_T_M($M_NOMESANTO))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA COMMENTIMIRACOLI","SUCCESSO")

        comando = "CREATE TABLE $N_T_L ($L_NOME TEXT, $L_CASADIO TEXT,  $L_PORDINE , PRIMARY KEY($L_NOME,$L_CASADIO,$L_PORDINE))"
        db?.execSQL(comando)
        Log.d("CREATA TABELLA LOGIN","SUCCESSO")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $N_T_P")
        Log.d("ELIMINAZIONE TABELLA LOGIN","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_S")
        Log.d("ELIMINAZIONE TABELLA SACERDOTI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_M")
        Log.d("ELIMINAZIONE TABELLA MIRACOLI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_C")
        Log.d("ELIMINAZIONE TABELLA COMMENTIMIRACOLI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_L")
        Log.d("ELIMINAZIONE TABELLA LOGIN","SUCCESSO")

        onCreate(db)
        Log.d("CREAZIONE DATABASE","SUCCESSO")
    }

    // Restituisce la lista di tutti i Plebei presenti al momento della chiamata all'interno del Database.
    // Se non ci sono Plebei, restituisce una lista di Plebei vuota.
    fun prendiPlebei() : ArrayList<Plebeo>{

        val db : SQLiteDatabase = readableDatabase

        val listaDaRestituire : ArrayList<Plebeo> = ArrayList()

        val comando = "SELECT * FROM $N_T_P"
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

        val comando = "SELECT * FROM $N_T_S"
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

    // Restituisce la lista di tutti i Miracoli presenti al momento della chiamata all'interno del Database.
    // Se non ci sono Miracoli, restituisce una lista di Miracoli vuota.
    // Lorenzo Borgia
    fun prendiMiracoli() : ArrayList<Miracolo> {

        val db : SQLiteDatabase = readableDatabase

        val listaDaRestituire : ArrayList<Miracolo> = ArrayList()

        val comando = "SELECT * FROM $N_T_M"
        val cursore : Cursor = db.rawQuery(comando, null)

        if (cursore.moveToFirst()) {
            do {
                val miracoloPrelevato = Miracolo()

                miracoloPrelevato.descr = cursore.getString(0)
                miracoloPrelevato.nomeSanto = cursore.getString(1)
                miracoloPrelevato.costo = cursore.getInt(2)
                miracoloPrelevato.testo = cursore.getString(3)

                listaDaRestituire.add(miracoloPrelevato)

            } while (cursore.moveToNext())
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

        modificato.put(P_NOME,nomePlebeo)
        modificato.put(P_CASATA,casata)
        modificato.put(P_BAIOCCHI,baiocchiVecchi + nBaiocchi)

        val nModificati = db.update(N_T_P, modificato, "$P_NOME=? AND $P_CASATA=?", arrayOf(nomePlebeo,casata))

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

    // toglie nBaiocchi al Plebeo univocamente identificato dalla coppia {nomePlebeo,casata} all'interno del Database.
    // restituisce false se non ha modificato nessun Plebeo, se ha modificato troppi Plebei o se c'e' stato un errore sconosciuto.
    // restituisce true in caso di corretta modifica.
    // non controlla se il Plebeo ha abbastanza baiocchi. Nel caso ne avra' negativi.
    fun togliBaiocchiAPlebeo(nomePlebeo : String, casata : String, nBaiocchi : Int) : Boolean{

        if(nBaiocchi < 0 || nomePlebeo == "" || casata == ""){
            Log.d("TOLGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata","INPUT NON VALIDI")
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

        modificato.put(P_NOME,nomePlebeo)
        modificato.put(P_CASATA,casata)
        modificato.put(P_BAIOCCHI,baiocchiVecchi - nBaiocchi)

        val nModificati = db.update(N_T_P, modificato, "$P_NOME=? AND $P_CASATA=?", arrayOf(nomePlebeo,casata))

        if (nModificati == 1){
            Log.d("TOLGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "SUCCESSO")
            return true
        }

        if(nModificati == 0){
            Log.d("TOLGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "NESSUN PLEBEO MODIFICATO")
            return false
        }

        if(nModificati > 1){
            Log.d("TOLGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "TROPPI PLEBEI MODIFICATI!")
            return false
        }
        Log.d("TOLGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata", "ERRORE SCONOSCIUTO")
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
        daAggiungere.put(P_NOME,nome)
        daAggiungere.put(P_CASATA,casata)
        daAggiungere.put(P_BAIOCCHI,nBaiocchi)

        val test = db?.insert(N_T_P, null, daAggiungere)

        if(test?.toInt() == -1){
            Log.d("INSERIMENTO DI $nome DI CASA $casata","FALLITO")
            return false
        }

        Log.d("INSERIMENTO DI $nome DI CASA $casata","SUCCESSO")
        return true
    }

    // Inserisce un nuovo utente (Plebeo o Sacerdote) con nome, casata/diocesi e Parola d'ordine.
    // Restituisce FALSE se l'input è vuoto oppure già presente.
    // Restituisce TRUE in caso di corretto inserimento.
    // Lorenzo Borgia
    fun aggiungiCredenziali (nome : String, casataDiocesi : String, parolaDOrdine : String) : Boolean {

        val nomeUtente = nome.trim()
        val casataDiocesiUtente = casataDiocesi.trim()

        if (nomeUtente.isEmpty() || casataDiocesiUtente.isEmpty()) {

            Log.d("INSERIMENTO DI $nomeUtente DI CASA $casataDiocesiUtente","NOME E CASATA/DIOCESI VUOTI")
            return false
        }

        val listaPlebeo = prendiPlebei()
        val listaSacerdote = prendiSacerdoti()

        for (contatore in 0 until listaPlebeo.size) {

            if (listaPlebeo[contatore].nome == nomeUtente && listaPlebeo[contatore].casata == casataDiocesiUtente) {

                Log.d("INSERIMENTO DI $nomeUtente DI CASA $casataDiocesiUtente","NOME E CASATA GIA' PRESENTI")
                return false
            }
        }

        for (contatore in 0 until listaSacerdote.size) {

            if (listaSacerdote[contatore].nome == nomeUtente && listaSacerdote[contatore].diocesi == casataDiocesiUtente) {

                Log.d("INSERIMENTO DI $nomeUtente DI DIOCESI $casataDiocesiUtente","NOME E DIOCESI GIA' PRESENTI")
                return false
            }
        }

        val db = writableDatabase

        val daAggiungere = ContentValues()
        daAggiungere.put(L_NOME, nomeUtente)
        daAggiungere.put(L_CASADIO, casataDiocesiUtente)
        daAggiungere.put(L_PORDINE, parolaDOrdine)

        db?.insert(N_T_L, null, daAggiungere)

        Log.d("INSERIMENTO DI $nomeUtente DI CASA/DIOCESI $casataDiocesiUtente E PAROLA D'ORDINE $parolaDOrdine","SUCCESSO")
        return true
    }

    // Inserisce un nuovo commento ad un miracolo con descrizione miracolo, santo del miracolo ed il commento testuale.
    // Restituisce FALSE se l'input del commento è vuoto o in caso di errore generico sull'inserimento.
    // Restituisce TRUE in caso di corretto inserimento.
    // Lorenzo Borgia
    fun aggiungiCommento (descrMiracolo: String, santoMiracolo : String, commento : String) : Boolean {

        val db = writableDatabase
        val commentoMiracolo = commento.trim()

        if (commentoMiracolo.isEmpty()) {

            Log.d("FALLIMENTO","INSERIMENTO DI $commentoMiracolo SU MIRACOLO $descrMiracolo DI $santoMiracolo, COMMENTO MIRACOLO VUOTO")
            return false
        }

        val listaMiracoli = prendiMiracoli()

        for (contatore in 0 until listaMiracoli.size) {

            if (listaMiracoli[contatore].descr == descrMiracolo && listaMiracoli[contatore].nomeSanto == santoMiracolo) {

                val daAggiungere = ContentValues()
                daAggiungere.put(C_DESC, descrMiracolo)
                daAggiungere.put(C_NOMESANTO, santoMiracolo)
                daAggiungere.put(C_COMM, commentoMiracolo)

                db?.insert(N_T_C, null, daAggiungere)

                Log.d("SUCCESSO","INSERIMENTO DI $commentoMiracolo SU MIRACOLO $descrMiracolo DI $santoMiracolo")
                return true
            }
        }

        Log.d("FALLIMENTO","INSERIMENTO DI $commentoMiracolo SU MIRACOLO $descrMiracolo DI $santoMiracolo")
        return false
    }

    // elimina un Plebeo con nome nome e casata casata dal Database.
    // restituisce false se gli input non sono validi, se non ha eliminato niente o se ha eliminato piu' di una riga.
    // restituisce true in caso di eliminazione di un Plebeo (non assicura la correttezza della scelta dell'eliminato).
    fun eliminaPlebeo(nome : String, casata : String) : Boolean{
        if(nome == "" || casata == "") return false

        val db = writableDatabase

        val nRigheEliminate = db.delete(N_T_P,"$P_NOME=? AND $P_CASATA=?", arrayOf(nome,casata))

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
        val cur = db.rawQuery("SELECT * FROM $N_T_C", null)
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
        val cur = db.rawQuery("SELECT * FROM $N_T_L", null)
        if (cur.moveToFirst()){
            do {
                if (cur.getString(0).equals(nome) && cur.getString(1).equals(casataDiocesi)) {
                    return true
                }

            } while (cur.moveToNext())
        }
        return false
    }


    // svuota completamente tutte le tabelle del database. Utile nelle funzioni di Test.
    fun svuotaDatabase(){

        val db = writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS $N_T_P")
        Log.d("ELIMINAZIONE TABELLA LOGIN","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_S")
        Log.d("ELIMINAZIONE TABELLA SACERDOTI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_M")
        Log.d("ELIMINAZIONE TABELLA MIRACOLI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_C")
        Log.d("ELIMINAZIONE TABELLA COMMENTIMIRACOLI","SUCCESSO")

        db?.execSQL("DROP TABLE IF EXISTS $N_T_L")
        Log.d("ELIMINAZIONE TABELLA LOGIN","SUCCESSO")

        onCreate(db)
        Log.d("CREAZIONE DATABASE","SUCCESSO")
    }

    // elimina un Sacerdote con nome nome e diocesi diocesi dal Database.
    // restituisce false se gli input non sono validi, se non ha eliminato niente o se ha eliminato piu' di una riga.
    // restituisce true in caso di eliminazione di un Sacerdote (non assicura la correttezza della scelta dell'eliminato).
    fun eliminaSacerdote(nome : String, diocesi : String): Boolean {
        if(nome == "" || diocesi == "") return false

        val db = writableDatabase

        val nRigheEliminate = db.delete(N_T_S,"$S_NOME=? AND $S_DIOCESI=?", arrayOf(nome,diocesi))

        if(nRigheEliminate == 0){
            Log.d("ELIMINO $nome SACERDOTE DI $diocesi","NESSUN SACERDOTE ELIMINATO")
            return false
        }

        if(nRigheEliminate>1){
            Log.d("ELIMINO $nome SACERDOTE DI $diocesi","ELIMINATE PIU' PLEBEI!")
            return false
        }

        Log.d("ELIMINO $nome SACERDOTE DI $diocesi","SUCCESSO")
        return true
    }

    //
    //
    // Lorenzo Borgia
    fun eliminaMiracolo (descr: String, nomeSanto : String) : Boolean {

        if (descr == "" || nomeSanto == "") {

            Log.d("FALLIMENTO","NESSUN SACERDOTE ELIMINATO, DESCRIZIONE O NOME SANTO VUOTE")
            return false
        }

        val db = writableDatabase

        val nRigheEliminate = db.delete(N_T_M,"$M_DESC=? AND $M_NOMESANTO=?", arrayOf(descr,nomeSanto))

        if(nRigheEliminate == 0){

            return false
        }

        if(nRigheEliminate>1){

            return false
        }


        return true

    }

    fun aggiungiMiracoli(descr: String, nomesanto: String, costo: Int, testo : String): Boolean {
        if(costo < 0 || descr == "" || nomesanto == ""){
            Log.d("Inserimento Miracoli","INPUT NON VALIDI")
            return false
        }
        val db = writableDatabase
        val daAggiungere = ContentValues()
        daAggiungere.put(M_DESC, descr)
        daAggiungere.put(M_NOMESANTO, nomesanto)
        daAggiungere.put(M_COSTO, costo)
        daAggiungere.put(M_TESTO, testo)
        val risultato = db?.insert(N_T_M,null,daAggiungere)

        if(risultato == (-1).toLong()) return false
        return true
    }

}

