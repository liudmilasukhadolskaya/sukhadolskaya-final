package by.yr.ui.pages
        ;

import by.yr.ui.utils.DriverUtils;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartPage {
    private final String BUTTON_ADD_TO_BASKET = "(//button[@class='basket-btn eye-button unselectable'])[%d]";
    private final String LINK_GO_TO_BASKET = "//a[@class='basket-btn unselectable eye-button']";
    private final String TITLE_PRODUCT = "(//div[@class='name unselectable']/a)[%d]";
    private final String TITLE_CART = "//app-basket//div[contains(@class,'chosen-products')]/span";
    private final String PRICE_FOR_PRODUCT_IN_CART = "(//div[@class='final-product-price'])[%d]";
    private final String SUM_IN_CART = "//div[@class='price']//dd[1]";
    private final String PRICE_DELIVERY = "//aside//dl/div[3]/dd";
    private final String TOTAL_PRICE = "//aside//dl/div[4]/dd";
    private final String CART_ICON_PRODUCT_QUANTITY = "//div[contains(@class,'product-quantity') and contains(@class,'eye-black-bg')]";
    private final String BUTTON_REMOVE_FROM_CART = "(//div[@class='delete-btn-and-price']/button)[%d]";
    private final String TITLE_CART_IS_EMPTY = "//div[@class='empty-basket ng-star-inserted']/h3";
    private final String TITLE_PRODUCT_QUANTITY = "(//div[@class='quantity']//div[@class='unselectable']/span[2])[%d]";
    private final String TITLE_PRODUCT_NAME_IN_CART_DIALOG = "//h3[@class='product-title']";
    private final String BUTTTON_CLOSE_DIALOG = "//button[@class='nsm-dialog-btn-close ng-star-inserted']";
    private static final Logger logger = LogManager.getLogger(CartPage.class);


    @Step("Click Add to Cart button")
    public void clickAddToCartItem(int index) {
        String xpath = String.format(BUTTON_ADD_TO_BASKET, index);
        logger.info("Clicking Add to Cart for item at index {}", index);
        DriverUtils.clickElement(xpath);
    }
@Step("Click Add to Cart button and Click Go To Basket")
    public void clickAddToCartAndGo(int index) {
    logger.info("Adding item at index {} to cart and going to basket", index);
        clickAddToCartItem(index);
        DriverUtils.clickElement(LINK_GO_TO_BASKET);
    }

    public String getProductNameFromCartByPosition(int index) {
        String productName=DriverUtils.getTextFromElement(String.format(TITLE_PRODUCT, index));
        logger.info("Product name at position {} is '{}'", index, productName);
        return productName;
    }

    public String getTitleCart() {
        return DriverUtils.getTextFromElement(TITLE_CART);
    }

    public String getPriceForProductInCartByPosition(int index) {
        return DriverUtils.getTextFromElement(String.format(PRICE_FOR_PRODUCT_IN_CART, index));
    }

    public double getSumPrice() {
        return DriverUtils.getTextAsDouble(SUM_IN_CART);
    }

    public double getDeliveryPrice() {
        return DriverUtils.getTextAsDouble(PRICE_DELIVERY);
    }

    public double getTotalPrice() {
        return DriverUtils.getTextAsDouble(TOTAL_PRICE);
    }

    public int getProductQuantityFromCartIcon() {
        return DriverUtils.getTextAsInt(CART_ICON_PRODUCT_QUANTITY);
    }

    @Step("Click Remove product from Cart")
    public void clickRemoveItemFromCart(int index) {
     String locator=String.format(BUTTON_REMOVE_FROM_CART,index);
        DriverUtils.clickElement(locator);
        logger.info("Attempting to remove product at index {} from cart", index);
        DriverUtils.sleep(10);
    }

    public String getEmptyCartText() {
        return DriverUtils.getTextFromElement(TITLE_CART_IS_EMPTY);
    }

    public String getQuantityForProductByPosition(int index) {
        return DriverUtils.getTextFromElement(String.format(TITLE_PRODUCT_QUANTITY, index));
    }

    public String getProductNameFromDialog() {
        return DriverUtils.getTextFromElement(TITLE_PRODUCT_NAME_IN_CART_DIALOG);
    }

    public String getGoToCartText() {
        return DriverUtils.getTextFromElement(LINK_GO_TO_BASKET);
    }
@Step("Close dialog pop up")
    public void closeDialogPopUp() {
        DriverUtils.clickElement(BUTTTON_CLOSE_DIALOG);
    }
}
