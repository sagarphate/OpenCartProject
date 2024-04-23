package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	// page class / page lobarary / page object
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// Private By Locators
	By myAccountLink = By.linkText("My Account");
	By editAccount = By.xpath("//a[text()='Edit Account']");
	By headerLinks = By.xpath("//div[@id='content']/h2");
	By serach = By.xpath("//input[@name='search']");
	By serachIcon = By.xpath("//div[@id='search']//button");
	
	// public page class Constructor
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 10);
		System.out.println("Account Page Title is : " +title); 
		return title;
	}
	
	 public String getAccPageURL() {
		 String url = eleUtil.waitForUrlContains(AppConstants.ACCOUNTS_PAGE_URL_FRANCTION, 10);
		 System.out.println("Account Page URL is : " +url);
		 return url;
	 }
	 
	public boolean myAccountLinkExist() {
		return eleUtil.waitForElementVisible(myAccountLink, 10).isDisplayed();
	}
	 
	 public boolean isEditAccountLinkExist() {
		 return eleUtil.waitForElementVisible(editAccount, 10).isDisplayed();
	 }
	 
	 public List<String> getAccountsPageHeadersList() {
 		List<WebElement> List = eleUtil.getElements(headerLinks);
 		List<String> headerList = new ArrayList<String>();	
 		for (WebElement e : List) {
			String header = e.getText();
			headerList.add(header);
 		}
 		return headerList;
	 }
	 
	 // in these method on click on serach icon the another page will open
	 // these method will give the next page reference
	 public SearhResultsPage doSearch(String SearchKey) { 
		 eleUtil.doSendKeys(serach, SearchKey);
		 eleUtil.doClick(serachIcon);
		 
		 return new SearhResultsPage(driver); // 
	 } 
	 
}
