package org.example;

import java.util.List;

public class UserService {
    List<AppUser> allUsers = List.of(
            new AppUser("anna", "losen"),
            new AppUser("anton", "qwerty"),
            new AppUser("berit", "123456"),
            new AppUser("bert", "hejsan"),
            new AppUser("kalle", "password"),
            new AppUser("karin", "abc123")
    );

    public boolean logIn(String username, String password) {
        AppUser foundUser = allUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow();
        return password.equals(foundUser.getPassword());
    }
}
