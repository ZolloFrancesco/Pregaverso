package com.example.pregaverso


class TestStringhe {

    // testa se l'input nell'interezza e' valido.
    // restituisce true se e' valido l'intero input.
    fun testRegistrazione(usr: String, pss: String, conf: String): Boolean {
        return (testUsername(usr) && testPassword(pss, conf))
    }

    // restituisce true se l'username e' valido
    fun testUsername(usr: String): Boolean {
        return (!(strVuota(usr)) && !strSpaziInterni(usr))
    }

    // restituisce true se la password e la conferma sono validi
    fun testPassword(pss: String, conf: String): Boolean {
        return (!(strVuota(pss)) && !(strVuota(conf)) && (confPassUguali(conf, pss)) &&
                !(strCorta(pss)) && (strConMaiuscola(pss)) && strConMinuscola(pss)
                && strConNum(pss))
    }

    // restituisce true se la stringa e' vuota
    fun strVuota(str: String): Boolean {
        if (str.trim().isBlank()) {
            return true
        }
        return false
    }

    // restituisce true se pss e conf sono uguali
    fun confPassUguali(pss: String, conf: String): Boolean {
        if (pss.trim() != conf.trim()) {
            return false
        }
        return true
    }

    // restituisce true se una stringa ha degli spazi interni (non come primo o ultimo carattere)
    fun strSpaziInterni(str: String): Boolean {
        if (str.trim().contains(' ')) {
            return true
        }
        return false
    }

    // restituisce true se la stringa ha una lunghezza minore di 8 caratteri
    // esclusi eventuali spazi in coda e in testa
    fun strCorta(str: String): Boolean {
        if (str.trim().length <= 8) {
            return true
        }
        return false
    }

    // restituisce true se la stringa comprende almeno una lettera maiuscola
    fun strConMaiuscola(str: String): Boolean {
        if (str.lowercase() == str) {
            return false
        }
        return true
    }

    // restituisce true se la stringa comprende almeno una lettera minuscola
    fun strConMinuscola(str: String): Boolean {
        if (str.uppercase() == str) {
            return false
        }
        return true
    }

    // restituisce true se la stringa comprende caratteri numerici
    fun strConNum(str: String): Boolean {
        if ((str.contains('0') || str.contains('1') || str.contains('2') ||
                    str.contains('3') || str.contains('4') || str.contains('5') ||
                    str.contains('6') || str.contains('7') || str.contains('8') ||
                    str.contains('9'))
        ) return true

        return false
    }
}