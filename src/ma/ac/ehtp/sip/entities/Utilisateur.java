package ma.ac.ehtp.sip.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue
    protected Long   id;
    @Email
    protected String email;
    @Username
    protected String username;
    @NotNull(message = "Mot de passe invalide.")
    protected String password;
    @NotNull(message = "Numéro de cin invalide.")
    protected String cin;
    @NotNull(message = "Nom invalide.")
    protected String nom;
    @NotNull(message = "Prénom invalide.")
    protected String prenom;
    @NotNull(message = "Numéro de téléphone invalide.")
    protected String numeroTel;

    public Utilisateur(){}

    public Utilisateur(Utilisateur utilisateur){
        this.id = utilisateur.id;
        this.email = utilisateur.email;
        this.username = utilisateur.username;
        this.password = utilisateur.password;
        this.cin = utilisateur.cin;
        this.nom = utilisateur.nom;
        this.prenom = utilisateur.prenom;
        this.numeroTel = utilisateur.numeroTel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }
}
