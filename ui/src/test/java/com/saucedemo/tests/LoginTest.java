package com.saucedemo.tests;

import com.saucedemo.config.TestConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    @Tag("regression")
    @Test
    void failedLogin_lockedOutUser() {
        // We bypass storageState for this test by clearing cookies or navigating to login
        loginPage.navigate();
        loginPage.login(TestConfig.LOCKED_USER, TestConfig.PASSWORD);
        assertTrue(loginPage.hasError());
        assertTrue(loginPage.getError().contains("locked out"));
    }
}
