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
            System.out.print("linperm> ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ", 2);
            String command = parts[0].toLowerCase();

            switch (command) {
                case "exit":
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Commande inconnue.");
                    break;
            }
        }
    }
}