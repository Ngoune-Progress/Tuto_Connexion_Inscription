package com.example.tuto_connexion_inscription;

public class Profil {
    private String nom;
    private String motDePasse;

    public Profil() {
        //Constructeur Firebase
    }

    public Profil(String nom, String motDePasse) {
        this.nom = nom;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
