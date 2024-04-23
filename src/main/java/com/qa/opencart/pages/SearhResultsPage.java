package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class SearhResultsPage {
	
	// page class / page lobarary / page object
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	// Private By Locators
	By searchProducts = By.cssSelector("div.product-thumb");	
	
	// public page class Constructor
	public SearhResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getProductCount() {
		return eleUtil.waitForElementsVisible(searchProducts, 10).size();
	}

	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Searching For Product : "+productName);
		eleUtil.waitForElementVisible(By.linkText(productName), 10).click();
		return new ProductInfoPage(driver);
	} 


}
