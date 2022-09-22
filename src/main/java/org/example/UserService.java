package org.example;

import javax.security.auth.login.FailedLoginException;
import java.util.Base64;
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

    public String logIn(String username, String password) throws FailedLoginException {
        AppUser foundUser = allUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new FailedLoginException("Incorrect combination of username and password!"));
        String foundPassword = foundUser.getPassword();
        if (foundPassword.equals(password)) {
            byte[] foundUserAsBytes = foundUser.getUsername().getBytes();
            byte[] foundUserAsBase64 = Base64.getEncoder().encode(foundUserAsBytes);
            String foundUserAs64String = new String(foundUserAsBase64);
            return foundUserAs64String;
        } else {
            throw new FailedLoginException("Incorrect combination of username and password!");
        }
    }

    public boolean verify64Token(String token) {
        byte[] tokenAsBase64 = token.getBytes();
        byte[] tokenAsBytes = Base64.getDecoder().decode(tokenAsBase64);
        String decodedToken = new String(tokenAsBytes);
        boolean isToken = allUsers.stream()
                .anyMatch(u -> u.getUsername().equals(decodedToken));
        return isToken;
    }
}
