package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.config.TestConfig;
import com.saucedemo.fixtures.TestFixtures;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Paths;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected static Playwright playwright;
    protected BrowserContext context;
    protected static Browser browser;
    protected TestFixtures fixture;
    protected Page page;

    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    @BeforeAll
    static void init() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(TestConfig.HEADLESS)
                .setSlowMo((float) TestConfig.SLOW_MO));
        // Global Setup: Authenticate once and save state
        if (!Files.exists(TestConfig.STORAGE_STATE_PATH)) {
            BrowserContext tempContext = browser.newContext();
            Page tempPage = tempContext.newPage();
            tempPage.navigate(TestConfig.BASE_URL);
            tempPage.fill("#user-name", TestConfig.STANDARD_USER);
            tempPage.fill("#password", TestConfig.PASSWORD);
            tempPage.click("#login-button");
            tempContext.storageState(new BrowserContext.StorageStateOptions()
                    .setPath(TestConfig.STORAGE_STATE_PATH));
            tempContext.close();
        }
    }

    @BeforeEach
    void setup() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setStorageStatePath(TestConfig.STORAGE_STATE_PATH)
                .setRecordVideoDir(Paths.get(TestConfig.SCREENSHOT_DIR))
                .setRecordVideoSize(1280, 720));

        page = context.newPage();
        page.setDefaultTimeout(TestConfig.TIMEOUT);

        loginPage      = new LoginPage(page);
        inventoryPage  = new InventoryPage(page);
        cartPage       = new CartPage(page);
        checkoutPage   = new CheckoutPage(page);
    }

    @AfterEach
    void teardown() {
        if (page != null) page.close();
        if (context != null) context.close();
    }

    @AfterAll
    void shutdown() {
        if (fixture != null) fixture.close();
    }
}
