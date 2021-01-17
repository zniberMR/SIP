package ma.ac.ehtp.sip.entities;

import ma.ac.ehtp.sip.tools.JodaDateTimeConverter;

import org.joda.time.DateTime;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
public class Demande {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column( columnDefinition = "TIMESTAMP" )
    @Converter( name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class )
    @Convert( "dateTimeConverter" )
    @NotNull(message = "Date invalide")
    private DateTime date;
    @NotNull(message = "CIN invalides")
    private String cin;
    @NotNull(message = "Nom invalides")
    private String nom;
    @NotNull(message = "Prénom invalides")
    private String prenom;
    @Email
    private String email;
    @NotNull(message = "Numéro de téléphone invalides")
    private String numeroTel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }
}
