package org.example;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import javax.security.auth.login.FailedLoginException;

import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class LogInTest {

    private UserService userService;

    @Mock
    UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, losen", "berit, 123456", "kalle, password"})
    public void logInTest(String username, String password) {
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
    @CsvSource(value = {"anna, losen_bad", "berit, 123456_bad", "kalle, password_bad"})
    public void logInTest_withBadCredentials_shouldThrow(String username, String password) {
        // Given

        // When, Then
        FailedLoginException err = assertThrows(FailedLoginException.class, () -> userService.logIn(username, password));
        assertEquals("Incorrect combination of username and password!", err.getMessage());
    }

    @Test
    public void tokenTest() {
        // testar om token genererar ett användarnamn som finns i listan av användarnamn
        // Given
        String token = "YW5uYQ=="; // Base64 för "anna"

        // When
        boolean isToken = userService.verify64Token(token);

        // Then
        assertTrue(isToken);
    }

    @Test
    public void tokenTest_withBadToken_shouldReturnFalse() {
        // Given
        String token = "fdasfsafafsafdsa"; // sadfsafda

        // When
        boolean isToken = userService.verify64Token(token);

        // Then
        assertFalse(isToken);
    }

    @ParameterizedTest
    @CsvSource(value = {"anna, STUDENT", "berit, TEACHER", "kalle, ADMIN"})
    public void jwtTokenTest(String username, String role) {
        // Given
        Key key = Keys.hmacShaKeyFor("BrackeliKrankelFnatt".getBytes());
        when(userRepo.findAllUsers()).thenReturn(List.of(
                new AppUser("anna", "losen", "STUDENT"),
                new AppUser("berit", "123456", "TEACHER"),
                new AppUser("kalle", "password", "ADMIN")
        ));

        // When
        String signedJwtToken = userService.generateToken(username);
        String decryptedRole = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(signedJwtToken)
                .getBody()
                .get("Role", String.class);

        // Then
        assertEquals(role, decryptedRole);
    }
}
