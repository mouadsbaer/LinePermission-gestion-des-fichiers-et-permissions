package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.FichierProtege;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String DROITS_FILE = "fichiers.txt";
    private static final String DATA_DIR = "data";

    private final List<FichierProtege> fichiers = new ArrayList<>();

    public FileService() {
        loadFichiers();
    }

    private void loadFichiers() {
        Path path = Paths.get(DROITS_FILE);
        if (!Files.exists(path)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                FichierProtege f = FichierProtege.fromStorageLine(line);
                if (f != null) {
                    fichiers.add(f);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des fichiers : " + e.getMessage());
        }
    }

    // prive : personne ne doit ecrire le fichier de droits sans passer par le service
    private void saveFichiers() {
        Path path = Paths.get(DROITS_FILE);
        StringBuilder sb = new StringBuilder();
        for (FichierProtege f : fichiers) {
            sb.append(f.toStorageLine()).append("\n");
        }
        try {
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des droits : " + e.getMessage());
        }
    }

    public List<FichierProtege> lister() {
        return fichiers;
    }

    private FichierProtege trouver(String nom) {
        for (FichierProtege f : fichiers) {
            if (f.getNom().equals(nom)) {
                return f;
            }
        }
        return null;
    }
}
