package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage extends BasePage {
    private final Locator checkoutBtn = locator("#checkout");

    public CartPage(Page page) {
        super(page);
    }

    public void proceedToCheckout() {
        click(checkoutBtn);
    }
}
