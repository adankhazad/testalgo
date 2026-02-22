package com.saucedemo.fixtures;
import com.microsoft.playwright.*;
import com.saucedemo.config.TestConfig;

import java.nio.file.Paths;


public class TestFixtures implements AutoCloseable {

    private final Playwright playwright;
    private final Browser browser;
    private BrowserContext context;
    private Page page;

    public TestFixtures() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(TestConfig.HEADLESS)
                        .setSlowMo((float) TestConfig.SLOW_MO));
    }

    public void createContext() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get(TestConfig.SCREENSHOT_DIR))
                .setRecordVideoSize(1280, 720));

        page = context.newPage();
        page.setDefaultTimeout(TestConfig.TIMEOUT);
    }

    public Page getPage() {
        return page;
    }

    public void screenshot(String name) {
        if (page != null) {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(TestConfig.SCREENSHOT_DIR, name + ".png")));
        }
    }

    public void destroyContext() {
        if (page != null) page.close();
        if (context != null) context.close();
        page = null;
        context = null;
    }

    @Override
    public void close() {
        destroyContext();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
