package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	//  page object
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productMap = new HashMap<String, String>();
	
	
	// public page class constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	// Private By Locators
	private By productHeader = By.tagName("h1");
	private By images = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private By productQuantity = By.id("input-quantity");
	private By addToCart = By.id("button-cart");
	private By successMessage = By.xpath("//div[contains(@class,'alert')]");
	private By soppingCart = By.xpath("//div[contains(@class,'alert')]/a[text()='shopping cart']");
	
	// validate the Total Number of images for product
	// validate the product header
	// validate the metadate - product details
	
	public String getProductHeader() {
		String header = eleUtil.getElementText(productHeader);
		System.out.println("Product Header :" +header);
		return  header;
	}
	
	public int getProductImagesCount() {
		int totalImages = eleUtil.waitForElementsVisible(images, 10).size();
		System.out.println("Images Count for " + getProductHeader() + ":" +totalImages);
		return totalImages;
	}
	
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
// DATA IN KEY VALUE PAIR
	public void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e : metaList) {
			String text = e.getText();
			String metaKey = text.split(":")[0].trim();
			String metaValue = text.split(":")[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}

	
//	$2,000.00
//	Ex Tax: $2,000.00
	public void getProductPriceData() {
		List<WebElement> metaPrice = eleUtil.getElements(productPriceData); 
		String price = metaPrice.get(0).getText();
		String exTaxPrice = metaPrice.get(1).getText().split(":")[1].trim();
		productMap.put("productPrice", price);
		productMap.put("productExTaxPrice", exTaxPrice);
	}
	
	
	public Map<String, String> getProductDetailsMap() {
		productMap.put("productHeader", getProductHeader());
		productMap.put("imageCount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		System.out.println("Product Details is :" +productMap);
		return productMap;
	}
	
	public void enterProductQuantity(String quantity) {
		eleUtil.doSendKeys(productQuantity, quantity);
	}
	
	public void getSuccessMessage() {
		String message = eleUtil.waitForElementVisible(successMessage, 10).getText();
		System.out.println(message);
	}
	
	public ShoppingCartPage productAddToCart() {
		enterProductQuantity("2");
		eleUtil.doClick(addToCart);
		getSuccessMessage();
		eleUtil.waitForElementVisible(soppingCart, 10).click();
		return new ShoppingCartPage(driver);
	}
}
