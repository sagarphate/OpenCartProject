package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class ShoppingCartPage {

	// page object
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// public class counstructor
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	// private By Locators
	private By productModel = By.xpath("((//div[@class='table-responsive']//tr)[2]/td)[3]");
	
	
	public String getproductModelDetails() {
	 	return eleUtil.getElementText(productModel);
	}
	
}
