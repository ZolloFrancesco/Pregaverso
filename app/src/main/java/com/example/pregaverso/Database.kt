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

        // creo tutte le tabelle che compongono il database
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

        // elimino tutte le tabelle del database e le ricreo chuiamando onCreate
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

        // lista che conterra' i plebei da restituire
        val listaDaRestituire : ArrayList<Plebeo> = ArrayList()

        // statement SQL che seleziona tutta la tabella Plebei
        val comando = "SELECT * FROM $N_T_P"

        // cursore che score la tabella precedente
        val cursore : Cursor = db.rawQuery(comando,null)

        // se la tabella Plebei non e' vuota
        if(cursore.moveToFirst()){
            do {
                // creo un nuovo oggetto Plebeo
                val plebeoPrelevato = Plebeo()

                // riempio gli attributi con tutti i campi della tabella Plebei
                plebeoPrelevato.nome = cursore.getString(0)
                plebeoPrelevato.casata = cursore.getString(1)
                plebeoPrelevato.baiocchi = cursore.getInt(2)

                // aggiungo il Plebeo appena creato alla lista dei Plebei da restituire
                listaDaRestituire.add(plebeoPrelevato)

                // sposta il cursore il avanti e ripete se c'e' una riga
            } while(cursore.moveToNext())
        }

        // chiusura del cursore
        cursore.close()

        // restituisco la lista dei plebei ottenuta
        return listaDaRestituire
    }

    // Restituisce la lista di tutti i Sacerdoti presenti al momento della chiamata all'interno del Database.
    // Se non ci sono Sacerdoti, restituisce una lista di Sacerdoti vuota.
    fun prendiSacerdoti() : ArrayList<Sacerdote>{

        val db : SQLiteDatabase = readableDatabase

        val listaDaRestituire : ArrayList<Sacerdote> = ArrayList()

        // statement SQL che seleziona tutta la tabella Sacerdoti
        val comando = "SELECT * FROM $N_T_S"

        // cursore che scorre la tabella Sacerdoti
        val cursore : Cursor = db.rawQuery(comando,null)

        // se esiste almeno un elemento nella tabella del cursore
        if(cursore.moveToFirst()){
            do {
                // dichiaro sacerdote
                val sacerdotePrelevato = Sacerdote()

                // riempio il sacerdote con gli elementi nella tabella
                sacerdotePrelevato.nome = cursore.getString(0)
                sacerdotePrelevato.diocesi = cursore.getString(1)

                // aggiungo alla lista
                listaDaRestituire.add(sacerdotePrelevato)
            // sposta il cursore il avanti e ripete se c'e' una riga
            } while(cursore.moveToNext())
        }

        // chiusura del cursore
        cursore.close()

        return listaDaRestituire
    }

    // Restituisce la lista di tutti i Miracoli presenti al momento della chiamata all'interno del Database.
    // Se non ci sono Miracoli, restituisce una lista di Miracoli vuota.
    // Lorenzo Borgia
    fun prendiMiracoli() : ArrayList<Miracolo> {

        val db : SQLiteDatabase = readableDatabase

        val listaDaRestituire : ArrayList<Miracolo> = ArrayList()

        // statement SQL che seleziona tutta la tabella dei Miracoli
        val comando = "SELECT * FROM $N_T_M"

        // creo un cursore che scorre la tabella dei Miracoli
        val cursore : Cursor = db.rawQuery(comando, null)

        // se esiste almeno un elemento nella tabella del cursore
        if (cursore.moveToFirst()) {
            do {
                val miracoloPrelevato = Miracolo()

                // riempio la nuova istanza di Miracolo
                miracoloPrelevato.descr = cursore.getString(0)
                miracoloPrelevato.nomeSanto = cursore.getString(1)
                miracoloPrelevato.costo = cursore.getInt(2)
                miracoloPrelevato.testo = cursore.getString(3)

                // aggiungo alla lista da restituire
                listaDaRestituire.add(miracoloPrelevato)

            // sposta il cursore il avanti e ripete se c'e' una riga
            } while (cursore.moveToNext())
        }

        // chiusura del cursore
        cursore.close()

        return listaDaRestituire
    }

    // aggiunge nBaiocchi al Plebeo univocamente identificato dalla coppia {nomePlebeo,casata} all'interno del Database.
    // restituisce false se non ha modificato nessun Plebeo, se ha modificato troppi Plebei o se c'e' stato un errore sconosciuto.
    // restituisce true in caso di corretta modifica.
    fun aggiungiBaiocchiAPlebeo(nomePlebeo : String, casata : String, nBaiocchi : Int) : Boolean{

        // test di validita' degli input
        if(nBaiocchi < 0 || nomePlebeo == "" || casata == ""){
            Log.d("AGGIUNGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata","INPUT NON VALIDI")
            return false
        }

        val db : SQLiteDatabase = writableDatabase

        // prendo tutti i Plebei presenti nel Database
        val listaPlebei = prendiPlebei()

        // variabile di utilita'
        var baiocchiVecchi = 0

        // scorro la lista appena prelevata dal Database
        for(i in 0 until listaPlebei.size){

            // se il nome e la casata corrispondono
            if( ( listaPlebei[i].nome == nomePlebeo ) && ( listaPlebei[i].casata == casata ) ){

                // salvo i baiocchi che il Plebeo gia' possedeva
                baiocchiVecchi = listaPlebei[i].baiocchi
            }
        }

        // dichiaro una riga di database
        val modificato = ContentValues()

        // riempio la riga di database
        modificato.put(P_NOME,nomePlebeo)
        modificato.put(P_CASATA,casata)
        modificato.put(P_BAIOCCHI,baiocchiVecchi + nBaiocchi)

        // inserisco la riga nel database sovrascrivendo la vecchia e salvo
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

        // controllo validita' degli input
        if(nBaiocchi < 0 || nomePlebeo == "" || casata == ""){
            Log.d("TOLGO $nBaiocchi BAIOCCHI A $nomePlebeo DI CASA $casata","INPUT NON VALIDI")
            return false
        }

        val db : SQLiteDatabase = writableDatabase

        val listaPlebei = prendiPlebei()

        // scorro la lista appena prelevata dal Database
        var baiocchiVecchi = 0

        // per ogni elemento della lista dei Plebei
        for(i in 0 until listaPlebei.size){

            // se ho trovato il Plebeo che cerco
            if( ( listaPlebei[i].nome == nomePlebeo ) && ( listaPlebei[i].casata == casata ) ){

                // salvo i vecchi baiocchi che deteneva
                baiocchiVecchi = listaPlebei[i].baiocchi
            }
        }

        // creo una riga di database
        val modificato = ContentValues()

        // riempio la riga di database
        modificato.put(P_NOME,nomePlebeo)
        modificato.put(P_CASATA,casata)
        modificato.put(P_BAIOCCHI,baiocchiVecchi - nBaiocchi)

        // inserisco la riga sovrascrivendo la vecchuia corrispondente al Plebeo
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

        // controllo gli input
        if(nBaiocchi < 0 || nome == "" || casata == ""){
            Log.d("INSERIMENTO DI $nome DI CASA $casata","INPUT NON VALIDI")
            return false
        }

        val db = writableDatabase

        // creo una riga di database
        val daAggiungere = ContentValues()

        // riempio la riga di database
        daAggiungere.put(P_NOME,nome)
        daAggiungere.put(P_CASATA,casata)
        daAggiungere.put(P_BAIOCCHI,nBaiocchi)

        // inserisco la riga alla tabella dei Plebei
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

        // elimino spazi all'inizio e alla fine
        val nomeUtente = nome.trim()
        val casataDiocesiUtente = casataDiocesi.trim()

        // se sono vuoti restituisco false
        if (nomeUtente.isEmpty() || casataDiocesiUtente.isEmpty()) {
            Log.d("INSERIMENTO DI $nomeUtente DI CASA $casataDiocesiUtente","NOME E CASATA/DIOCESI VUOTI")
            return false
        }

        // creo liste di utilita' di Plebei e Sacerdoti
        val listaPlebeo = prendiPlebei()
        val listaSacerdote = prendiSacerdoti()

        // per ogni elemento nella lista dei Plebei
        for (contatore in 0 until listaPlebeo.size) {

            // se esiste gia' nel database restituisco false
            if (listaPlebeo[contatore].nome == nomeUtente && listaPlebeo[contatore].casata == casataDiocesiUtente) {
                Log.d("INSERIMENTO DI $nomeUtente DI CASA $casataDiocesiUtente","NOME E CASATA GIA' PRESENTI")
                return false
            }
        }

        // per ogni elemento della tabella dei Sacerdoti
        for (contatore in 0 until listaSacerdote.size) {

            // se il Sacerdote esiste gia restituisco false
            if (listaSacerdote[contatore].nome == nomeUtente && listaSacerdote[contatore].diocesi == casataDiocesiUtente) {
                Log.d("INSERIMENTO DI $nomeUtente DI DIOCESI $casataDiocesiUtente","NOME E DIOCESI GIA' PRESENTI")
                return false
            }
        }

        val db = writableDatabase

        // creo riga del database
        val daAggiungere = ContentValues()

        // inserisco valori nella riga
        daAggiungere.put(L_NOME, nomeUtente)
        daAggiungere.put(L_CASADIO, casataDiocesiUtente)
        daAggiungere.put(L_PORDINE, parolaDOrdine)

        // inserisco la riga nella tabella Login
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

        // elimino spazi prima e dopo
        val commentoMiracolo = commento.trim()

        // se e' vuoto restituisco false
        if (commentoMiracolo.isEmpty()) {
            Log.d("FALLIMENTO","INSERIMENTO DI $commentoMiracolo SU MIRACOLO $descrMiracolo DI $santoMiracolo, COMMENTO MIRACOLO VUOTO")
            return false
        }

        // crea riga del database
        val daAggiungere = ContentValues()

        // riempi riga del database
        daAggiungere.put(C_DESC, descrMiracolo)
        daAggiungere.put(C_NOMESANTO, santoMiracolo)
        daAggiungere.put(C_COMM, commentoMiracolo)

        // inserisci riga nel database
        db?.insert(N_T_C, null, daAggiungere)
        return true
    }

    // elimina un Plebeo con nome nome e casata casata dal Database.
    // restituisce false se gli input non sono validi, se non ha eliminato niente o se ha eliminato piu' di una riga.
    // restituisce true in caso di eliminazione di un Plebeo (non assicura la correttezza della scelta dell'eliminato).
    fun eliminaPlebeo(nome : String, casata : String) : Boolean{

        // controllo input non validi
        if(nome == "" || casata == "") return false

        val db = writableDatabase

        // elimino il plebeo con attributi da input
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

        // creo un cursore che scorre tutta la tabella dei commenti
        val cur = db.rawQuery("SELECT * FROM $N_T_C", null)

        // se esiste almeno una riga nella tabella dei Commenti
        if (cur.moveToFirst()){
            do{

                // creo un nuovo Commento
                val commentoNuovo = Commento()

                // lo riempio dalla tabella
                commentoNuovo.descrMiracolo = cur.getString(0)
                commentoNuovo.nomeSanto = cur.getString(1)
                commentoNuovo.commento = cur.getString(2)

                // lo aggiungo alla lista da restituire
                commenti.add(commentoNuovo)
            // continuo finche' ci sono elementi nella tabella Commenti
            } while(cur.moveToNext())
        }

        val listaDaRestituire : ArrayList<String> = ArrayList()

        // per ogni elemento della lista dei commenti
        for (i in 0 until commenti.size){

            // se la descrizione e il nome del santo corrispondono
            if(commenti[i].descrMiracolo.equals(descr) && commenti[i].nomeSanto.equals(nomeSanto)){

                // aggiungo alla lista da restituire
                listaDaRestituire.add(commenti[i].commento)
            }
        }

        return listaDaRestituire
    }

    // verifica se un utente specifico è presente nel database
    // in caso di riscontro positivo restituisce true, altrimenti false
    fun testUtente(nome: String, casataDiocesi: String, parolaDOrdine: String) : Boolean{

        val db = readableDatabase

        // statement SQL che seleziona tutta la tabella Login
        val cur = db.rawQuery("SELECT * FROM $N_T_L", null)

        // se esiste almeno una riga nella tabella Login
        if (cur.moveToFirst()){
            do {

                //  se ho trovato l'utente corrispondente restituisco true
                if (cur.getString(0).equals(nome) && cur.getString(1).equals(casataDiocesi)) {
                    return true
                }

            // continuo finche' non finiscono le righe
            } while (cur.moveToNext())
        }
        return false
    }

    // svuota completamente tutte le tabelle del database. Utile nelle funzioni di Test.
    fun svuotaDatabase(){
        val db = writableDatabase
        onUpgrade(db, VERSIONE_DATABASE, VERSIONE_DATABASE)
    }

    // elimina un Sacerdote con nome nome e diocesi diocesi dal Database.
    // restituisce false se gli input non sono validi, se non ha eliminato niente o se ha eliminato piu' di una riga.
    // restituisce true in caso di eliminazione di un Sacerdote (non assicura la correttezza della scelta dell'eliminato).
    fun eliminaSacerdote(nome : String, diocesi : String): Boolean {

        // controllo gli input
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

