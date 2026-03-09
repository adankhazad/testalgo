import { test, expect } from '../fixtures'; // Importing from our custom fixtures file

test.describe('Authentication Scenarios', () => {

  test('Successful login with standard_user', async ({ loggedInUser }) => {
    // The 'loggedInUser' fixture already handled the login process.
    // We just verify the result.
    await expect(loggedInUser.title).toHaveText('Products');
  });

  test('Failed login with locked_out_user', async ({ loginPage }) => {
    await loginPage.goto();
    await loginPage.login('locked_out_user', 'secret_sauce');

    await expect(loginPage.errorMessage).toContainText('locked out');
  });
});

test.describe('Shopping Flow', () => {

  test('Add product to cart and start checkout', async ({ loggedInUser, inventoryPage, cartPage, page }) => {
    // 1. Add item to cart
    // Note: loggedInUser fixture puts us on the Inventory Page automatically
    await inventoryPage.addBackpackToCart();
    await expect(inventoryPage.cartBadge).toHaveText('1');

    // 2. Navigate to Cart
    await inventoryPage.goToCart();

    // 3. Proceed to Checkout
    await cartPage.startCheckout();

    // 4. Verify URL
    await expect(page).toHaveURL(/.*checkout-step-one.html/);
  });
});