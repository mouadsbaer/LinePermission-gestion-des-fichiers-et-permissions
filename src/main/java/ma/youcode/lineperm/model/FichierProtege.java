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

    // construit "rwd" ou "r--" a partir de trois booleens
    private String bloc(boolean r, boolean w, boolean d) {
        String br = r ? "r" : "-";
        String bw = w ? "w" : "-";
        String bd = d ? "d" : "-";
        return br + bw + bd;
    }

    public String getBlocProprio() {
        return bloc(rProprio, wProprio, dProprio);
    }

    public String getBlocAutres() {
        return bloc(rAutres, wAutres, dAutres);
    }

    // format d'affichage : rwd|--- proprietaire nomFichier
    public String toLsLine() {
        return getBlocProprio() + "|" + getBlocAutres() + " " + proprietaire + " " + nom;
    }

    // une ligne de persistance : nom;proprietaire;rwd;r--
    public String toStorageLine() {
        return nom + ";" + proprietaire + ";" + getBlocProprio() + ";" + getBlocAutres();
    }

    public static FichierProtege fromStorageLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 4) {
            return null;
        }
        String nom = parts[0];
        String proprietaire = parts[1];
        String blocP = parts[2];
        String blocA = parts[3];
        if (blocP.length() != 3 || blocA.length() != 3) {
            return null;
        }
        boolean rP = blocP.charAt(0) == 'r';
        boolean wP = blocP.charAt(1) == 'w';
        boolean dP = blocP.charAt(2) == 'd';
        boolean rA = blocA.charAt(0) == 'r';
        boolean wA = blocA.charAt(1) == 'w';
        boolean dA = blocA.charAt(2) == 'd';
        return new FichierProtege(nom, proprietaire, rP, wP, dP, rA, wA, dA);
    }
}
