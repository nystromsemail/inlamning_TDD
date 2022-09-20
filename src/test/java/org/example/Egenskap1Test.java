package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Egenskap1Test {

    @ParameterizedTest
    @CsvSource(value = {"anna, losen_bad", "berit, 123456_bad", "kalle, password_bad"})
    public void logIn(String username, String password) {
        // Given
        List<AppUser> allUsers = List.of(
                new AppUser("anna", "losen"),
                new AppUser("anton", "qwerty"),
                new AppUser("berit", "123456"),
                new AppUser("bert", "hejsan"),
                new AppUser("kalle", "password"),
                new AppUser("karin", "abc123")
        );

        // When
        AppUser foundUser = allUsers.stream()
                .filter(appUser -> appUser.getUsername().equals(username))
                .findAny()
                .orElseThrow();
        boolean isSame = password.equals(foundUser.getPassword());

        // Then
        assertTrue(isSame);
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, 123456_bad", "berit, password_bad", "kalle, losen_bad"})
    public void logIn_withBadCredentials_shouldReturnFalse(String username, String password) {
        // Given
        List<AppUser> allUsers = List.of(
                new AppUser("anna", "losen"),
                new AppUser("anton", "qwerty"),
                new AppUser("berit", "123456"),
                new AppUser("bert", "hejsan"),
                new AppUser("kalle", "password"),
                new AppUser("karin", "abc123")
        );

        // When
        AppUser foundUser = allUsers.stream()
                .filter(appUser -> appUser.getUsername().equals(username))
                .findAny()
                .orElseThrow();
        boolean isSame = password.equals(foundUser.getPassword());

        // Then
        assertFalse(isSame);
    }

}
