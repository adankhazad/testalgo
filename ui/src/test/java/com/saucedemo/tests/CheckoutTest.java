package com.saucedemo.tests;

import com.saucedemo.config.TestConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutTest extends BaseTest {

    @Tag("smoke")
    @Tag("regression")
    @Test
    void addProductAndStartCheckout() {
        loginPage.navigate();
        loginPage.login(TestConfig.STANDARD_USER, TestConfig.PASSWORD);

        inventoryPage.addFirstItem();
        assertEquals("1", inventoryPage.cartCount());

        inventoryPage.goToCart();

        cartPage.proceedToCheckout();

        checkoutPage.fillInformation("John", "Doe", "12345");

        assertTrue(checkoutPage.getOverviewTitle().contains("Overview"));
    }
}
