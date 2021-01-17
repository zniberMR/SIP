package ma.ac.ehtp.sip.entities;

import javax.persistence.Entity;

@Entity
public class Administrateur extends Utilisateur {
    private String poste;

    public Administrateur(){}

    public Administrateur(Utilisateur utilisateur){
        super(utilisateur);
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
}
