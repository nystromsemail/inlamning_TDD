package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import javax.security.auth.login.FailedLoginException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class LogInTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, losen_bad", "berit, 123456_bad", "kalle, password_bad"})
    public void logInTest(String username, String password) throws FailedLoginException {
        // Given

        // When, Then
        try {
            String token = userService.logIn(username, password);
            assertInstanceOf(String.class, token);
            assertNotEquals("", token);
        } catch (FailedLoginException err) {
            assertDoesNotThrow(() -> userService.logIn(username, password));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, losen", "berit, 123456", "kalle, password"})
    public void logInTest_withBadCredentials_shouldThrow(String username, String password) throws FailedLoginException {
        // Given

        // When, Then
        FailedLoginException err = assertThrows(FailedLoginException.class, () -> userService.logIn(username, password));
        assertEquals("Incorrect combination of username and password!!", err.getMessage());
    }

    @Test
    public void tokenTest() {
        // testar om token genererar ett användarnamn som finns i listan av användarnamn
        // Given
        String token = "fdasfsafafsafdsa"; // sadfsafda

        // When
        boolean isToken = userService.verifyToken(token);

        // Then
        assertTrue(isToken);
    }

    @Test
    public void tokenTest_withBadToken_shouldReturnFalse() {
        // Given
        String token = "YW5uYQ=="; // Base64 för "anna"

        // When
        boolean isToken = userService.verifyToken(token);

        // Then
        assertFalse(isToken);
    }
}
