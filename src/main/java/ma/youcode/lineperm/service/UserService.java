package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final String STORAGE_FILE = "users.txt";
    private final Map<String, User> usersByLogin = new HashMap<>();

    public UserService() {
        loadUsers();
    }

    private void loadUsers() {
        Path path = Paths.get(STORAGE_FILE);
        if (!Files.exists(path)) {
            return; // premier lancement, aucun fichier
        }
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":", 2); // login:hash
                if (parts.length == 2) {
                    String login = parts[0];
                    String hash = parts[1];
                    usersByLogin.put(login, new User(login, hash));
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des comptes : " + e.getMessage());
        }
    }
}