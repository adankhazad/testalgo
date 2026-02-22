package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import com.saucedemo.config.TestConfig;

import java.nio.file.Paths;

public abstract class BasePage {
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected void go(String url) {
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
    }

    public void waitForLoad() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    protected Locator locator(String selector) {
        return page.locator(selector);
    }

    public void navigate() {
        page.navigate(TestConfig.BASE_URL);
    }

    protected void fill(Locator locator, String value) {
        locator.fill(value);
    }

    protected void click(Locator locator) {
        locator.click();
    }

    protected String text(Locator locator) {
        return locator.innerText();
    }

    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    public void screenshot(String filename) {
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(TestConfig.SCREENSHOT_DIR, filename + ".png")));
    }
}

