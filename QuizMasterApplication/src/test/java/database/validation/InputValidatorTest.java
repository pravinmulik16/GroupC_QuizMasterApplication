package database.validation;

import com.database.validation.InputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidatorTest {

    @Test
    @DisplayName("Should accept valid email formats and reject invalid ones")
    void testEmailValidation() {
        assertTrue(InputValidator.isValidEmail("student@test.com"));
        assertFalse(InputValidator.isValidEmail("plain-text-email"));
        assertFalse(InputValidator.isValidEmail("missing-domain@.com"));
    }

    @Test
    @DisplayName("Should enforce minimum password length constraint of 6 characters")
    void testPasswordValidation() {
        assertTrue(InputValidator.isValidPassword("secret123"));
        assertFalse(InputValidator.isValidPassword("12345"));
    }

    @Test
    @DisplayName("Should validate strict 10-digit mobile constraints")
    void testMobileValidation() {
        assertTrue(InputValidator.isValidMobile("7507071576"));
        assertFalse(InputValidator.isValidMobile("12345"));
        assertFalse(InputValidator.isValidMobile("123456789012"));
        assertFalse(InputValidator.isValidMobile("abc1234567"));
    }
}
