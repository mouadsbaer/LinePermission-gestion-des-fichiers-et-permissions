package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;
import org.mindrot.jbcrypt.BCrypt;

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

    private void saveUsers() {
        Path path = Paths.get(STORAGE_FILE);
        StringBuilder sb = new StringBuilder();
        for (User user : usersByLogin.values()) {
            sb.append(user.getLogin()).append(":").append(user.getPasswordHash()).append("\n");
        }
        try {
            Files.write(path, sb.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // --- Methodes metier ---

    /**
     * Cree un nouveau compte.
     * Leve une IllegalArgumentException si le login est invalide, deja pris,
     * ou si le mot de passe est vide.
     */
    public void signup(String login, String password) {
        // Verifications
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Le login ne peut pas etre vide.");
        }
        if (login.contains(" ") || login.contains(":")) {
            throw new IllegalArgumentException("Le login ne peut pas contenir d'espace ni de ':'.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas etre vide.");
        }
        if (usersByLogin.containsKey(login)) {
            throw new IllegalArgumentException("Ce login est deja pris.");
        }

        // Hachage du mot de passe
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password, salt);

        User newUser = new User(login, hash);
        usersByLogin.put(login, newUser);
        saveUsers();
    }

    /**
     * Tente de connecter un utilisateur.
     * Retourne l'utilisateur si les identifiants sont valides, sinon null.
     * (meme message pour login inconnu et mauvais mot de passe)
     */
    public User login(String login, String password) {
        if (login == null || password == null) {
            return null;
        }
        User user = usersByLogin.get(login);
        if (user == null) {
            return null; // login inconnu
        }
        // Verification du mot de passe
        boolean match = BCrypt.checkpw(password, user.getPasswordHash());
        return match ? user : null;
    }

    // Pour verifier l'existence d'un login (utile dans ConsoleApp)
    public boolean userExists(String login) {
        return usersByLogin.containsKey(login);
    }
}