package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class InventoryPage extends BasePage {
    private final Locator title     = locator(".title");
    private final Locator cartBadge = locator(".shopping_cart_badge");
    private final Locator cartIcon  = locator(".shopping_cart_link");
    private final Locator addBtns   = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart"));

    public InventoryPage(Page page) {
        super(page);
    }

    public String getTitle() {
        return text(title);
    }

    public void addFirstItem() {
        click(addBtns.first());
    }

    public String cartCount() {
        return text(cartBadge);
    }

    public void goToCart() {
        click(cartIcon);
    }
}
