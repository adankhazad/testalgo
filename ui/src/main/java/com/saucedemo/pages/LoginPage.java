package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.config.TestConfig;

public class LoginPage extends BasePage {
    private final Locator username = locator("#user-name");
    private final Locator password = locator("#password");
    private final Locator loginBtn = locator("#login-button");
    private final Locator error    = locator("[data-test=error]");

    public LoginPage(Page page) {
        super(page);
    }

    public LoginPage open() {
        go(TestConfig.BASE_URL);
        return this;
    }
    public void login(String user, String pass) {
        fill(username, user);
        fill(password, pass);
        click(loginBtn);
    }

    public String getError() {
        return text(error);
    }

    public boolean hasError() {
        return isVisible(error);
    }
}
