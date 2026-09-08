package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final String STORAGE_FILE = "users.txt";
    private final Map<String, User> usersByLogin = new HashMap<>();

    public UserService() {
    }
}