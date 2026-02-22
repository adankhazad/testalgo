package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage extends BasePage {

    private final Locator firstName    = locator("#first-name");
    private final Locator lastName     = locator("#last-name");
    private final Locator postalCode   = locator("#postal-code");
    private final Locator continueBtn  = locator("#continue");
    private final Locator finishBtn    = locator("#finish");
    private final Locator overviewTitle = locator(".title");

    public CheckoutPage(Page page) {
        super(page);
    }

    // Step 1: Your Information
    public void fillInformation(String first, String last, String zip) {
        fill(firstName, first);
        fill(lastName, last);
        fill(postalCode, zip);
        click(continueBtn);
    }

    // Step 2: Overview
    public String getOverviewTitle() {
        return text(overviewTitle);
    }

    public void finishOrder() {
        click(finishBtn);
    }
}