package com.example.projekt;

public class Przepis {
    private String obrazek;
    private String autor;
    private String ocena;
    private String dataDodania;
    private String skladniki;
    private String nazwa;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Przepis(String obrazek, String autor, String ocena, String dataDodania, String skladniki, String sposobPrzygotowania, String nazwa) {
        this.obrazek = obrazek;
        this.autor = autor;
        this.ocena = ocena;
        this.dataDodania = dataDodania;
        this.skladniki = skladniki;
        this.sposobPrzygotowania = sposobPrzygotowania;
        this.nazwa=nazwa;
    }

    public Przepis( String autor, String ocena, String dataDodania, String skladniki, String sposobPrzygotowania, String nazwa) {
        this.obrazek = obrazek;
        this.autor = autor;
        this.ocena = ocena;
        this.dataDodania = dataDodania;
        this.skladniki = skladniki;
        this.sposobPrzygotowania = sposobPrzygotowania;
        this.nazwa=nazwa;
    }

    public Przepis(String obrazek, String autor, String ocena, String dataDodania) {
        this.obrazek = obrazek;
        this.autor = autor;
        this.ocena = ocena;
        this.dataDodania = dataDodania;
    }

    public Przepis(String obrazek, String autor, String ocena, String dataDodania,String nazwa) {
        this.obrazek = obrazek;
        this.autor = autor;
        this.ocena = ocena;
        this.dataDodania = dataDodania;
        this.nazwa = nazwa;
    }

    public String getSkladniki() {
        return skladniki;
    }

    public void setSkladniki(String skladniki) {
        this.skladniki = skladniki;
    }

    public String getSposobPrzygotowania() {
        return sposobPrzygotowania;
    }

    public void setSposobPrzygotowania(String sposobPrzygotowania) {
        this.sposobPrzygotowania = sposobPrzygotowania;
    }

    private String sposobPrzygotowania;


    public String getObrazek() {
        return obrazek;
    }

    public void setObrazek(String obrazek) {
        this.obrazek = obrazek;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }

    public String getDataDodania() {
        return dataDodania;
    }

    public void setDataDodania(String dataDodania) {
        this.dataDodania = dataDodania;
    }
}
