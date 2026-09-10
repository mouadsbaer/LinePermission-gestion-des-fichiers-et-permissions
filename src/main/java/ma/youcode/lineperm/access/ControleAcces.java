package ma.youcode.lineperm.access;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

public class ControleAcces {

    // pas d'etat : on n'a pas besoin de new ControleAcces()
    public static boolean estProprietaire(User user, FichierProtege fichier) {
        if (user == null || fichier == null) {
            return false;
        }
        return user.getLogin().equals(fichier.getProprietaire());
    }

    // une seule categorie : proprietaire -> bloc gauche, sinon -> bloc droite
    public static boolean estAutorise(User user, FichierProtege fichier, char droit) {
        if (user == null || fichier == null) {
            return false;
        }
        boolean proprio = estProprietaire(user, fichier);
        if (droit == 'r') {
            return proprio ? fichier.isrProprio() : fichier.isrAutres();
        }
        if (droit == 'w') {
            return proprio ? fichier.iswProprio() : fichier.iswAutres();
        }
        if (droit == 'd') {
            return proprio ? fichier.isdProprio() : fichier.isdAutres();
        }
        return false;
    }
}
