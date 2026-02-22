package com.saucedemo.tests;

import com.saucedemo.config.TestConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    @Test
    void successfulLogin_standardUser() {
        loginPage.navigate();
        loginPage.login(TestConfig.STANDARD_USER, TestConfig.PASSWORD);
        assertTrue(inventoryPage.getTitle().contains("Products"));
    }

    @Test
    void failedLogin_lockedOutUser() {
        loginPage.navigate();
        loginPage.login(TestConfig.LOCKED_USER, TestConfig.PASSWORD);
        assertTrue(loginPage.hasError());
        assertTrue(loginPage.getError().contains("locked out"));
    }
}
