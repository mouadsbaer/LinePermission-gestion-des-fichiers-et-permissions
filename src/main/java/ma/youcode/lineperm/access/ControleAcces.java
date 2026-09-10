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
}
