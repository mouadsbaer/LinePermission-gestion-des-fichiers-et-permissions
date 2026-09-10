package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.UserService;

import java.util.Scanner;

public class ConsoleApp {
    private final UserService userService = new UserService();
    private final FileService fileService = new FileService();
    private User currentUser = null;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Bienvenue dans LinePermission !");
        while (true) {
            String prompt = buildPrompt();
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue; // ligne vide → on ignore
            }

            String[] parts = line.split(" ", 2);
            String command = parts[0].toLowerCase();

            switch (command) {
                case "signup":
                    handleSignup(parts);
                    break;
                case "login":
                    handleLogin(parts);
                    break;
                case "logout":
                    handleLogout();
                    break;
                case "ls":
                    handleLs(parts);
                    break;
                case "touch":
                    handleTouch(parts);
                    break;
                case "cat":
                    handleCat(parts);
                    break;
                case "nano":
                    handleNano(parts);
                    break;
                case "exit":
                    System.out.println("Au revoir !");
                    return;
                default:
                    // Commande inconnue (ou commande de la partie 2)
                    System.out.println("Commande inconnue.");
                    break;
            }
        }
    }

    private String buildPrompt() {
        if (currentUser == null) {
            return "linperm> ";
        } else {
            return currentUser.getLogin() + "@linperm> ";
        }
    }

    // --- Gestionnaires de commandes ---

    private void handleSignup(String[] parts) {
        if (currentUser != null) {
            System.out.println("Vous êtes déjà connecté. Déconnectez-vous d'abord.");
            return;
        }
        if (parts.length < 2) {
            System.out.println("Usage: signup <login> <mot_de_passe>");
            return;
        }
        // On récupère le login et le mot de passe
        String[] args = parts[1].split(" ", 2);
        if (args.length < 2) {
            System.out.println("Usage: signup <login> <mot_de_passe>");
            return;
        }
        String login = args[0].trim();
        String password = args[1].trim();

        try {
            userService.signup(login, password);
            System.out.println("Compte créé avec succès !");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLogin(String[] parts) {
        if (currentUser != null) {
            System.out.println("Vous êtes déjà connecté. Déconnectez-vous d'abord.");
            return;
        }
        if (parts.length < 2) {
            System.out.println("Usage: login <login> <mot_de_passe>");
            return;
        }
        String[] args = parts[1].split(" ", 2);
        if (args.length < 2) {
            System.out.println("Usage: login <login> <mot_de_passe>");
            return;
        }
        String login = args[0].trim();
        String password = args[1].trim();

        User user = userService.login(login, password);
        if (user == null) {
            // Même message que pour login inconnu ou mauvais mot de passe
            System.out.println("Login ou mot de passe incorrect.");
        } else {
            currentUser = user;
            System.out.println("Bienvenue " + login + " !");
        }
    }

    private void handleLogout() {
        if (currentUser == null) {
            System.out.println("Personne n'est connecté.");
            return;
        }
        System.out.println("Au revoir " + currentUser.getLogin() + " !");
        currentUser = null;
    }

    private boolean needLogin() {
        if (currentUser == null) {
            System.out.println("Vous devez etre connecte.");
            return false;
        }
        return true;
    }

    private void handleLs(String[] parts) {
        if (!needLogin()) {
            return;
        }
        if (parts.length < 2 || !parts[1].equals("-l")) {
            System.out.println("Usage: ls -l");
            return;
        }
        for (FichierProtege f : fileService.lister()) {
            System.out.println(f.toLsLine());
        }
    }

    private void handleTouch(String[] parts) {
        if (!needLogin()) {
            return;
        }
        if (parts.length < 2) {
            System.out.println("Usage: touch <fichier>");
            return;
        }
        String resultat = fileService.creer(currentUser.getLogin(), parts[1].trim());
        if (resultat.equals("OK")) {
            System.out.println("Fichier cree.");
        } else {
            System.out.println(resultat);
        }
    }

    private void handleCat(String[] parts) {
        if (!needLogin()) {
            return;
        }
        if (parts.length < 2) {
            System.out.println("Usage: cat <fichier>");
            return;
        }
        String nom = parts[1].trim();
        if (!fileService.existe(nom)) {
            System.out.println("Fichier introuvable.");
            return;
        }
        String contenu = fileService.lire(currentUser, nom);
        if (contenu == null) {
            System.out.println("Permission denied.");
            return;
        }
        System.out.print(contenu);
        if (!contenu.isEmpty() && !contenu.endsWith("\n")) {
            System.out.println();
        }
    }

    private void handleNano(String[] parts) {
        if (!needLogin()) {
            return;
        }
        if (parts.length < 2) {
            System.out.println("Usage: nano <fichier>");
            return;
        }
        String nom = parts[1].trim();
        if (!fileService.existe(nom)) {
            System.out.println("Fichier introuvable.");
            return;
        }
        // on verifie w avant de faire saisir le texte
        if (!fileService.peutEcrire(currentUser, nom)) {
            System.out.println("Permission denied.");
            return;
        }
        if (fileService.masquerContenu(currentUser, nom)) {
            System.out.println("Edition a l'aveugle (pas de droit r). Tapez EOF pour terminer.");
        } else {
            System.out.println("Saisie (une ligne EOF pour terminer) :");
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            String ligne = scanner.nextLine();
            if (ligne.equals("EOF")) {
                break;
            }
            sb.append(ligne).append("\n");
        }
        String resultat = fileService.ecrire(currentUser, nom, sb.toString());
        if (resultat.equals("DENIED")) {
            System.out.println("Permission denied.");
        } else if (resultat.equals("OK")) {
            System.out.println("Fichier enregistre.");
        } else {
            System.out.println(resultat);
        }
    }
}