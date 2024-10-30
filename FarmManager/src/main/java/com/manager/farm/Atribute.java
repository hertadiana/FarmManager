package com.manager.farm;

import java.util.Date;

public class Atribute {

    public Atribute(int id_vaca) {
        if (id_vaca <= 0) {
            throw new IllegalArgumentException("id_vaca must be a positive integer");
        }
        this.id_vaca = id_vaca;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getId_vaca() {
        return id_vaca;
    }

    public void setId_vaca(int id_vaca) {
        this.id_vaca = id_vaca;
    }

    private int  id_vaca;
    private String monta;

    public String getMonta() {
        return monta;
    }

    public void setMonta(String monta) {
        this.monta = monta;
    }

    public String getFatari() {
        return fatari;
    }

    public void setFatari(String fatari) {
        this.fatari = fatari;
    }

    public String getMentiuni() {
        return mentiuni;
    }

    public void setMentiuni(String mentiuni) {
        this.mentiuni = mentiuni;
    }

    private String fatari;
    private String mentiuni;
}
