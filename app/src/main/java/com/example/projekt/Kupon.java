package com.example.projekt;

public class Kupon {
    String NazwaRestauracji;
    String KodKuponu;
    String OpisKuponu;

    public String getNazwaRestauracji() {
        return NazwaRestauracji;
    }

    public void setNazwaRestauracji(String nazwaRestauracji) {
        NazwaRestauracji = nazwaRestauracji;
    }

    public String getKodKuponu() {
        return KodKuponu;
    }

    public void setKodKuponu(String kodKuponu) {
        KodKuponu = kodKuponu;
    }

    public String getOpisKuponu() {
        return OpisKuponu;
    }

    public void setOpisKuponu(String opisKuponu) {
        OpisKuponu = opisKuponu;
    }

    public String getNazwaKuponu() {
        return NazwaKuponu;
    }

    public void setNazwaKuponu(String nazwaKuponu) {
        NazwaKuponu = nazwaKuponu;
    }

    public Kupon(String nazwaRestauracji, String kodKuponu, String opisKuponu, String nazwaKuponu) {
        NazwaRestauracji = nazwaRestauracji;
        KodKuponu = kodKuponu;
        OpisKuponu = opisKuponu;
        NazwaKuponu = nazwaKuponu;
    }

    String NazwaKuponu;
}
