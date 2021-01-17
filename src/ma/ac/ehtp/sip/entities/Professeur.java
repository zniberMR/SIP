package ma.ac.ehtp.sip.entities;

import javax.persistence.Entity;

@Entity
public class Professeur extends Utilisateur {
    private String departement;
    private String matiere;
    private String filiere;

    public Professeur(){}

    public Professeur(Utilisateur utilisateur){
        super(utilisateur);
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
}
