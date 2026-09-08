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
    }
}