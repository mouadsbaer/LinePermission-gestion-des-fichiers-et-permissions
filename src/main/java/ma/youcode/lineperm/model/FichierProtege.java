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

    // constructeur court : fichier neuf = rwd|---
    public FichierProtege(String nom, String proprietaire) {
        this(nom, proprietaire, true, true, true, false, false, false);
    }

    // constructeur long : utile au chargement depuis le fichier de droits
    public FichierProtege(String nom, String proprietaire,
                          boolean rProprio, boolean wProprio, boolean dProprio,
                          boolean rAutres, boolean wAutres, boolean dAutres) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.rProprio = rProprio;
        this.wProprio = wProprio;
        this.dProprio = dProprio;
        this.rAutres = rAutres;
        this.wAutres = wAutres;
        this.dAutres = dAutres;
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
