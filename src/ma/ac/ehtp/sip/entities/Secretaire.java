package ma.ac.ehtp.sip.entities;

import javax.persistence.Entity;

@Entity
public class Secretaire extends Utilisateur {
    private String departement;
    private String bureau;

    public Secretaire(){}

    public Secretaire(Utilisateur utilisateur){
        super(utilisateur);
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getBureau() {
        return bureau;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
    }
}
