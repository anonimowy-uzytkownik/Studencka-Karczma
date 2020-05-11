package com.example.projekt;

public class Przepis {
    private String obrazek;
    private String autor;
    private String ocena;
    private String dataDodania;
    public Przepis(String obrazek, String autor, String ocena, String dataDodania)
    {
        this.obrazek=obrazek;
        this.autor=autor;
        this.ocena=ocena;
        this.dataDodania=dataDodania;

    }

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
