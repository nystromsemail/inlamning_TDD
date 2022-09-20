package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Egenskap1Test {

    @ParameterizedTest
    @CsvSource(value = {"anna, losen", "berit, 123456", "kalle, password"})
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

        // Then
        assertEquals(password, foundUser.getPassword());
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, 123456", "berit, password", "kalle, losen"})
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

        // Then
        assertNotEquals(password, foundUser.getPassword());
    }

}
