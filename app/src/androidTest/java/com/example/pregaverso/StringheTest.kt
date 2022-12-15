package com.example.pregaverso

import org.junit.Assert
import org.junit.Test

class StringheTest {

    val ts = TestStringhe()

    // usr vuoto
    @Test
    fun validate_test_username_vuoto() {

        var test = ts.testUsername("")
        Assert.assertFalse(test)

        test = ts.testUsername("Francesco")
        Assert.assertTrue(test)
    }

    // usr contiene spazi interni
    @Test
    fun validate_test_username_senza_spazi_interni() {

        var test = ts.testUsername("France sco")
        Assert.assertFalse(test)

        test = ts.testUsername("Francesco")
        Assert.assertTrue(test)
    }

    // pss vuota
    @Test
    fun validate_test_password_vuota() {

        var test = ts.testPassword("", "Password1!")
        Assert.assertFalse(test)

        test = ts.testPassword("Password1!", "Password1!")
        Assert.assertTrue(test)
    }

    // conf vuoto
    @Test
    fun validate_test_password_conf_vuota() {

        var test = ts.testPassword("Password1!", "")
        Assert.assertFalse(test)

        test = ts.testPassword("Password1!", "Password1!")
        Assert.assertTrue(test)
    }

    // pss e conf non corrispondono
    @Test
    fun validate_test_password_conf_corr() {

        var test = ts.testPassword("Password1!", "")
        Assert.assertFalse(test)

        test = ts.testPassword("Password1!", "Password1!")
        Assert.assertTrue(test)
    }

    // pss con meno di 8 caratteri
    @Test
    fun validate_test_password_corta() {

        var test = ts.testPassword("Password1!", "Password1!")
        Assert.assertTrue(test)

        test = ts.testPassword("Pass1!", "Pass1!")
        Assert.assertFalse(test)
    }

    // pss non contiene almeno una maiuscola
    @Test
    fun validate_test_password_maiuscola() {

        var test = ts.testPassword("pasSword1!", "pasSword1!")
        Assert.assertTrue(test)

        test = ts.testPassword("password1!", "password1!")
        Assert.assertFalse(test)
    }


    // pss non contiene almeno una minuscola
    @Test
    fun validate_test_password_minuscola() {

        var test = ts.testPassword("PASsWORD1!", "PASsWORD1!")
        Assert.assertTrue(test)

        test = ts.testPassword("PASSWORD1!", "PASSWORD1!")
        Assert.assertFalse(test)
    }

    // pss non contiene almeno un numero
    @Test
    fun validate_test_password_num() {
        var test = ts.testPassword("Password1!", "Password1!")
        Assert.assertTrue(test)

        test = ts.testPassword("Password!", "Password!")
        Assert.assertFalse(test)
    }

    /* test delle funzioni di utilita' */
    @Test
    fun validate_str_vuota() {

        var test = ts.strVuota("")
        Assert.assertTrue(test)

        test = ts.strVuota("piena")
        Assert.assertFalse(test)
    }

    @Test
    fun validate_conf_pass_uguali() {

        var test = ts.confPassUguali("ugua le", "ugua le")
        Assert.assertTrue(test)

        test = ts.confPassUguali("uguale", "diverso")
        Assert.assertFalse(test)
    }

    @Test
    fun validate_str_spazi_interni() {

        var test = ts.strSpaziInterni("spazio in tern o")
        Assert.assertTrue(test)

        test = ts.strSpaziInterni("senzaspazi")
        Assert.assertFalse(test)
    }

    @Test
    fun validate_str_corta() {

        var test = ts.strCorta("io")
        Assert.assertTrue(test)

        test = ts.strCorta("magnificentissimevolmente")
        Assert.assertFalse(test)
    }

    @Test
    fun validate_str_con_maiuscola() {

        var test = ts.strConMaiuscola("maiUscola")
        Assert.assertTrue(test)

        test = ts.strConMaiuscola("senza maiuscola")
        Assert.assertFalse(test)
    }

    @Test
    fun validate_str_con_minuscola() {

        var test = ts.strConMinuscola("MINuSCOLA")
        Assert.assertTrue(test)

        test = ts.strConMinuscola("MINUSCOLA")
        Assert.assertFalse(test)
    }

    @Test
    fun validate_str_con_num() {

        var test = ts.strConNum("con num3ri")
        Assert.assertTrue(test)

        test = ts.strConNum("senza numeri")
        Assert.assertFalse(test)
    }
}