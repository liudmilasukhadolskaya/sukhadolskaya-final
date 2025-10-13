package by.yr.ui;

import by.yr.ui.pages.CartPage;
import by.yr.ui.pages.SearchPage;
import by.yr.utils.ScreenshotOnFailureWatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ScreenshotOnFailureWatcher.class)
public class CartTest extends BaseTest {
    private SearchPage searchPage;

    @BeforeEach
    public void runSearchByCategory() {
        searchPage = new SearchPage();
        searchPage.pickRandomCategory();
    }

    @Test
    @DisplayName("Verify dialog pop up is opened after clicking Add To Cart")
    public void dialogPopupOpened() {
        String expectedProductName = searchPage.getProductNameByPosition(1);
        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartAndGo(1);

        Assertions.assertAll(
                "Dialog pop up validations",
                () -> Assertions.assertEquals(expectedProductName, cartPage.getProductNameFromDialog(), "Product name mismatch"),
                () -> Assertions.assertEquals("ПЕРЕЙТИ В КОРЗИНУ", cartPage.getGoToCartText(), "Button not found ot text changed")
        );
    }

    @Test
    @DisplayName("Verify that Cart is opened after adding the product")
    public void cartIsOpened() {
        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartAndGo(1);

        Assertions.assertAll(
                "Cart validations",
                () -> Assertions.assertEquals("Корзина", cartPage.getTitleCart(), "Cart title mismatch"),
                () -> Assertions.assertEquals(1, cartPage.getProductQuantityFromCartIcon(), "Cart quantity mismatch")
        );
    }

    @Test
    @DisplayName("Verify that the price, name, q-ty are correct in the Cart")
    public void infoInCart() {

        String expectedProduct = searchPage.getProductNameByPosition(1).toLowerCase();
        String expectedPrice = searchPage.getPriceForProductByPosition(1) + " Br";

        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartAndGo(1);

        Assertions.assertAll(
                "Product validation in the Cart",
                () -> Assertions.assertEquals(expectedPrice, cartPage.getPriceForProductInCartByPosition(1), "Product price mismatch"),
                () -> Assertions.assertEquals(expectedProduct, cartPage.getProductNameFromCartByPosition(1).toLowerCase(), "Product name mismatch"),
                () -> Assertions.assertEquals("1", cartPage.getQuantityForProductByPosition(1), "Product q-ty mismatch")
        );
    }

    @Test
    @DisplayName("Verify that the final price is sum of total and delivery")
    public void finalPriceCheck() {
        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartAndGo(1);

        Assertions.assertEquals(cartPage.getSumPrice() + cartPage.getDeliveryPrice(), cartPage.getTotalPrice());
    }

    @Test
    @DisplayName("Verify empty Cart after removing product")
    public void emptyCart() {

        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartAndGo(1);
        cartPage.clickRemoveItemFromCart(1);

        Assertions.assertEquals("Ваша корзина пуста", cartPage.getEmptyCartText());
    }

    @Test
    @DisplayName("Verify that the price, name, q-ty are correct for 2 products")
    public void cartForTwoProducts() {

        String expectedProduct1 = searchPage.getProductNameByPosition(1).toLowerCase();
        String expectedPrice1 = searchPage.getPriceForProductByPosition(1) + " Br";
        String expectedProduct2 = searchPage.getProductNameByPosition(2).toLowerCase();
        String expectedPrice2 = searchPage.getPriceForProductByPosition(2) + " Br";

        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartItem(1);
        cartPage.closeDialogPopUp();
        cartPage.clickAddToCartAndGo(2);

        Assertions.assertAll(
                "Products validation in the Cart",
                () -> Assertions.assertEquals(expectedPrice1, cartPage.getPriceForProductInCartByPosition(2), "Product price mismatch"),
                () -> Assertions.assertEquals(expectedProduct1, cartPage.getProductNameFromCartByPosition(2).toLowerCase(), "Product name mismatch"),
                () -> Assertions.assertEquals("1", cartPage.getQuantityForProductByPosition(2), "Product q-ty mismatch"),
                () -> Assertions.assertEquals(expectedPrice2, cartPage.getPriceForProductInCartByPosition(1), "Product price mismatch"),
                () -> Assertions.assertEquals(expectedProduct2, cartPage.getProductNameFromCartByPosition(1).toLowerCase(), "Product name mismatch"),
                () -> Assertions.assertEquals("1", cartPage.getQuantityForProductByPosition(1), "Product q-ty mismatch")
        );
    }

    @Test
    @DisplayName("Verify 1 product left after removing another one")
    public void removeOneProductFromCart() {

        String expectedProduct1 = searchPage.getProductNameByPosition(1).toLowerCase();
        String expectedPrice1 = searchPage.getPriceForProductByPosition(1) + " Br";

        CartPage cartPage = new CartPage();
        cartPage.clickAddToCartItem(1);
        cartPage.closeDialogPopUp();

        cartPage.clickAddToCartAndGo(2);
        cartPage.clickRemoveItemFromCart(1);

        Assertions.assertAll(
                "Product validation in the Cart",
                () -> Assertions.assertEquals(expectedPrice1, cartPage.getPriceForProductInCartByPosition(1), "Product price mismatch"),
                () -> Assertions.assertEquals(expectedProduct1, cartPage.getProductNameFromCartByPosition(1).toLowerCase(), "Product name mismatch"),
                () -> Assertions.assertEquals("1", cartPage.getQuantityForProductByPosition(1), "Product q-ty mismatch")
        );
    }
}
