package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class RegisterationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// public contructor 
	public RegisterationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email"); // randaomally generated
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value='1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value='0']");
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	private By sucessMess = By.xpath("//div[@id='content']/h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	@Step("User Registration")
	public boolean userRegisteration(String firstName, String lastName,String emailId, String telephone, String password, String subcrib) {
		eleUtil.waitForElementVisible(this.firstName, 10).sendKeys(firstName);;
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.emailId, emailId);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subcrib.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);	 
		
		String regSuccessMessage = eleUtil.waitForElementVisible(sucessMess, 10).getText();
		System.out.println(regSuccessMessage);
		
		if(regSuccessMessage.equals(AppConstants.USER_REG_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		else {
			return false;
		}
	
		
	}
}
