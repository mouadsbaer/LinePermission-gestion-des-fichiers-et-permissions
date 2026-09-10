package ma.youcode.lineperm.model;

public class FichierProtege {
    private final String nom;
    private final String proprietaire;

    // droits du proprietaire (bloc de gauche)
    private boolean rProprio;
    private boolean wProprio;
    private boolean dProprio;
    // droits des autres (bloc de droite)
    private boolean rAutres;
    private boolean wAutres;
    private boolean dAutres;

    public FichierProtege(String nom, String proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.rProprio = true;
        this.wProprio = true;
        this.dProprio = true;
        this.rAutres = false;
        this.wAutres = false;
        this.dAutres = false;
    }

    public String getNom() {
        return nom;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public boolean isrProprio() {
        return rProprio;
    }

    public boolean iswProprio() {
        return wProprio;
    }

    public boolean isdProprio() {
        return dProprio;
    }

    public boolean isrAutres() {
        return rAutres;
    }

    public boolean iswAutres() {
        return wAutres;
    }

    public boolean isdAutres() {
        return dAutres;
    }

    public void setrAutres(boolean rAutres) {
        this.rAutres = rAutres;
    }

    public void setwAutres(boolean wAutres) {
        this.wAutres = wAutres;
    }

    public void setdAutres(boolean dAutres) {
        this.dAutres = dAutres;
    }
}
