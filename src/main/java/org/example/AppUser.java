package org.example;

import java.util.Objects;

public class AppUser {

    String username;
    String password;
    String role;

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public String getRole() {return role;}
}
