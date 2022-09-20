package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Egenskap1Test {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, 123456", "berit, password", "kalle, losen"})
    public void logInTest(String username, String password) {
        // Given

        // When
        boolean isCorrectPassword = userService.logIn(username, password);

        // Then
        assertTrue(isCorrectPassword);
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, losen", "berit, 123456", "kalle, password"})
    public void logInTest_withBadCredentials_shouldReturnFalse(String username, String password) {
        // Given

        // When
        boolean isCorrectPassword = userService.logIn(username, password);

        // Then
        assertFalse(isCorrectPassword);
    }

}
