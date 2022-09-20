package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.security.auth.login.FailedLoginException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class LogInTest {

    private UserService userService;
    System.Logger logger;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, losen", "berit, 123456", "kalle, password"})
    public void logInTest(String username, String password) {
        // Given

        // When
        try {
            String token = userService.logIn(username, password);
            byte[] tokenAsBase64Bytes = token.getBytes();
            byte[] tokenAsBytes = Base64.getDecoder().decode(tokenAsBase64Bytes);
            String decodedToken = new String(tokenAsBytes);
            assertEquals(username, decodedToken);
        } catch (FailedLoginException err) {
            logger.log(System.Logger.Level.ERROR, err);
            throw err;
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, losen_bad", "berit, 123456_bad", "kalle, password_bad"})
    public void logInTest_withBadCredentials_shouldReturnFalse(String username, String password) {
        // Given

        // When, Then
        try {
            String token = userService.logIn(username, password);
            byte[] tokenAsBase64Bytes = token.getBytes();
            byte[] tokenAsBytes = Base64.getDecoder().decode(tokenAsBase64Bytes);
            String decodedToken = new String(tokenAsBytes);
            assertNotEquals(username, decodedToken);
        } catch (FailedLoginException err) {
            logger.log(System.Logger.Level.ERROR, err);
            throw err;
        }
    }

    @Test
    public void tokenTest() {
        // testar om token genererar ett användarnamn som finns i listan av användarnamn
        // Given
        String token = "fdasfsafa"; // sadfsafda

        // When
        boolean isToken = userService.verifyToken(token);

        // Then
        assertTrue(isToken);
    }

    public void tokenTest_withBadToken_shouldReturnFalse() {
        // Given
        String token = "YW5uYQ=="; // Base64 för "anna"

        // When
        boolean isToken = userService.verifyToken(token);

        // Then
        assertFalse(isToken);
    }

}
