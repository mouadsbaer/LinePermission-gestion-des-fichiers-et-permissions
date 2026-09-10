package ma.youcode.lineperm.model;

public class FichierProtege {
    private final String nom;
    private final String proprietaire;

    public FichierProtege(String nom, String proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
    }

    public String getNom() {
        return nom;
    }

    public String getProprietaire() {
        return proprietaire;
    }
}
