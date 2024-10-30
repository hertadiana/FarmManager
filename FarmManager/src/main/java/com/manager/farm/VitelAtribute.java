package com.manager.farm;

public class VitelAtribute {
    private int attributeId;
    private int viteiId;
    private String parinti;
    private double greutateFatare;
    private double sporCrestere;
    private String mentiuni;

    // Default constructor
    public VitelAtribute() {
    }

    // Parameterized constructor
    public VitelAtribute(int attributeId, int viteiId, String parinti, double greutateFatare, double sporCrestere, String mentiuni) {
        this.attributeId = attributeId;
        this.viteiId = viteiId;
        this.parinti = parinti;
        this.greutateFatare = greutateFatare;
        this.sporCrestere = sporCrestere;
        this.mentiuni = mentiuni;
    }

    // Getter and Setter for attributeId
    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    // Getter and Setter for viteiId
    public int getViteiId() {
        return viteiId;
    }

    public void setViteiId(int viteiId) {
        this.viteiId = viteiId;
    }

    // Getter and Setter for parinti
    public String getParinti() {
        return parinti;
    }

    public void setParinti(String parinti) {
        this.parinti = parinti;
    }

    // Getter and Setter for greutateFatare
    public double getGreutateFatare() {
        return greutateFatare;
    }

    public void setGreutateFatare(double greutateFatare) {
        this.greutateFatare = greutateFatare;
    }

    // Getter and Setter for sporCrestere
    public double getSporCrestere() {
        return sporCrestere;
    }

    public void setSporCrestere(double sporCrestere) {
        this.sporCrestere = sporCrestere;
    }

    // Getter and Setter for mentiuni
    public String getMentiuni() {
        return mentiuni;
    }

    public void setMentiuni(String mentiuni) {
        this.mentiuni = mentiuni;
    }
}
