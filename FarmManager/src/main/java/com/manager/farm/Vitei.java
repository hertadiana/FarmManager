package com.manager.farm;

import java.util.Date;

public class Vitei {
    private int id;
    private String nume;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String sex;

    private int numar;
    private Date dataNastere;

    // Default constructor
    public Vitei() {
    }

    // Parameterized constructor
    public Vitei(int id, String nume, int numar, Date dataNastere) {
        this.id = id;
        this.nume = nume;
        this.numar = numar;
        this.dataNastere = dataNastere;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for nume
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    // Getter and Setter for numar
    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    // Getter and Setter for dataNastere
    public Date getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
    }
}
