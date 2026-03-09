import { test as base } from '@playwright/test';
import { LoginPage } from './pages/LoginPage';
import { InventoryPage } from './pages/InventoryPage';
import { CartPage } from './pages/CartPage';

// Define types for our custom fixtures
type MyFixtures = {
  loginPage: LoginPage;
  inventoryPage: InventoryPage;
  cartPage: CartPage;
  loggedInUser: InventoryPage; // Special fixture that provides an authenticated state
};

export const test = base.extend<MyFixtures>({
  // Standard Page Objects (lazy loading)
  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },
  inventoryPage: async ({ page }, use) => {
    await use(new InventoryPage(page));
  },
  cartPage: async ({ page }, use) => {
    await use(new CartPage(page));
  },

  // Logic fixture - logs the user in automatically and provides the InventoryPage
  loggedInUser: async ({ page, loginPage, inventoryPage }, use) => {
      const username = process.env.STANDARD_USER || 'standard_user';
      const password = process.env.PASSWORD || 'secret_sauce';

    await loginPage.goto();
    await loginPage.login(username, password);

    // Wait for the URL to change to verify successful login
    await page.waitForURL(/.*inventory.html/);

    await use(inventoryPage);

  },
});

export { expect } from '@playwright/test';