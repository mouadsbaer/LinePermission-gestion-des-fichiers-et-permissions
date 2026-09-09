package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

import java.util.Scanner;

public class ConsoleApp {
    private final UserService userService = new UserService();
    private User currentUser = null;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Bienvenue dans LinePermission !");
        while (true) {
            String prompt = buildPrompt();
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue; // ligne vide -> on ignore
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
        if (parts.length < 2) {
            System.out.println("Usage: signup <login> <mot_de_passe>");
            return;
        }
        String[] args = parts[1].split(" ", 2);
        if (args.length < 2) {
            System.out.println("Usage: signup <login> <mot_de_passe>");
            return;
        }
        String login = args[0].trim();
        String password = args[1].trim();

        try {
            userService.signup(login, password);
            System.out.println("Compte cree avec succes !");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLogin(String[] parts) {
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
            System.out.println("Login ou mot de passe incorrect.");
        } else {
            currentUser = user;
            System.out.println("Bienvenue " + login + " !");
        }
    }

    private void handleLogout() {
        if (currentUser == null) {
            System.out.println("Personne n'est connecte.");
            return;
        }
        System.out.println("Au revoir " + currentUser.getLogin() + " !");
        currentUser = null;
    }
}