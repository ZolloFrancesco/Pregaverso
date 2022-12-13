package com.example.pregaverso
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DatabaseTest{

    val db = Database(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun qualsiasi(){

    }

    @Test
    fun testAggiungiPlebeo(){

        db.svuotaDatabase()

        assertFalse( db.aggiungiPlebeo("","casata",10) )

        assertFalse( db.aggiungiPlebeo("nome","",10) )

        assertFalse( db.aggiungiPlebeo("nome","casata",-5) )

        assertTrue( db.aggiungiPlebeo("nome","casata",10) )

    }

    @Test
    fun testPrendiPlebei(){

        db.svuotaDatabase()

        val plebeiInseriti : ArrayList<Plebeo> = ArrayList()

        for(i in 0 until 10){
            db.aggiungiPlebeo("nome $i","casata $i",10)

            val nuovoPlebeo = Plebeo()
            nuovoPlebeo.nome = "nome $i"
            nuovoPlebeo.casata = "casata $i"
            nuovoPlebeo.baiocchi = 10
            plebeiInseriti.add(nuovoPlebeo)
        }

        val plebeiPresi = db.prendiPlebei()

        assertTrue( plebeiInseriti.size == plebeiPresi.size)

        for(i in 0 until plebeiInseriti.size){
            assertEquals(plebeiPresi[i].nome, plebeiPresi[i].nome)
            assertEquals(plebeiPresi[i].casata, plebeiInseriti[i].casata)
            assertEquals(plebeiPresi[i].baiocchi, plebeiInseriti[i].baiocchi)
        }

    }

    @Test
    fun testPrendiSacerdoti(){

        db.svuotaDatabase()

        val sacerdotiInseriti : ArrayList<Sacerdote> = ArrayList()

        for(i in 0 until 10){
            db.aggiungiSacerdote("nome $i","diocesi $i")

            val nuovoSacerdote = Sacerdote()
            nuovoSacerdote.nome = "nome $i"
            nuovoSacerdote.diocesi = "diocesi $i"
            sacerotiInseriti.add(nuovoSacerdote)
        }

        val sacerdotiPresi = db.prendiSacerdoti()

        assertTrue( sacerdotiInseriti.size == sacerdotiPresi.size)

        for(i in 0 until sacerdotiInseriti.size){
            assertEquals(sacerdotiPresi[i].nome, sacerdotiPresi[i].nome)
            assertEquals(sacerdotiPresi[i].diocesi, sacerdotiInseriti[i].diocesi)
        }

    }

    @Test
    fun testAggiungiBaiocchiAPlebeo(){

        db.svuotaDatabase()

        db.aggiungiPlebeo("nome","casata",10)

        assertFalse( db.aggiungiBaiocchiAPlebeo("sbagliato","casata",10) )

        assertTrue( db.aggiungiBaiocchiAPlebeo("nome","casata",10) )

        val listaPlebei = db.prendiPlebei()

        assertTrue(listaPlebei[0].baiocchi == 20)
    }

    @Test
    fun testEliminaPlebeo(){

        db.svuotaDatabase()

        for(i in 0 until 10){
            db.aggiungiPlebeo("nome $i","casata $i", 10)
        }

        for(i in 0 until 10){
            assertTrue( db.eliminaPlebeo("nome $i","casata $i") )
        }

        assertFalse( db.eliminaPlebeo("nome", "casata") )

        assertFalse( db.eliminaPlebeo("", "casata") )

        assertFalse( db.eliminaPlebeo("nome", "") )

        for(i in 0 until 10){
            db.aggiungiPlebeo("nome $i","casata $i", 10)
        }

        assertFalse( db.eliminaPlebeo("nome 11", "casata 2") )

        assertFalse( db.eliminaPlebeo("nome 2", "casata 11") )
    }

    @Test
    fun testEliminaSacerdote(){

        db.svuotaDatabase()

        for(i in 0 until 10){
            db.aggiungiSacerdote("nome $i","diocesi $i")
        }

        for(i in 0 until 10){
            assertTrue( db.eliminaSacerdote("nome $i","diocesi $i") )
        }

        assertFalse( db.eliminaSacerdote("nome", "diocesi") )

        assertFalse( db.eliminaSacerdote("", "diocesi") )

        assertFalse( db.eliminaSacerdote("nome", "") )

        for(i in 0 until 10){
            db.aggiungiSacerdote("nome $i","diocesi $i")
        }

        assertFalse( db.eliminaSacerdote("nome 11", "diocesi 2") )

        assertFalse( db.eliminaSacerdote("nome 2", "diocesi 11") )
    }

    // descr : String, santo : String, commento : String
    @Test
    fun testAggiungiCommento(){

        db.svuotaDatabase()

        for(i in 0 until 10){
            db.aggiungiMiracolo("descrizione $i","San Francesco",1000)
        }

        for(i in 0 until 10){
            for(j in 0 until 5){
                assertTrue( db.aggiungiCommento("descrizione $i","San Francesco","commento $j") )
            }
        }

        for(i in 0 until 10){
            val commenti = db.prendiCommenti("descrizione $i","San Francesco")

            for(j in 0 until commenti.size){
                assertTrue(commenti[i].equals("commento $j"))
            }
        }

        assertFalse( db.aggiungiCommento("descrizione 0","non esistente","commento"))

        assertFalse( db.aggiungiCommento("non esistente","San Francesco","commento"))

        assertFalse( db.aggiungiCommento("descrizione 0","SanFrancesco",""))

        assertFalse( db.aggiungiCommento("","",""))

        db.svuotaDatabase()

        assertFalse( db.aggiungiCommento("non esistente","non esistente","commento"))

        assertFalse( db.aggiungiCommento("","non esistente","commento"))

        assertFalse( db.aggiungiCommento("non esistente","","commento"))

        assertFalse( db.aggiungiCommento("non esistente","non esistente",""))

        assertFalse( db.aggiungiCommento("","",""))

    }


}