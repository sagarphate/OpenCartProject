package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage { //page class or page library or page object 

	private WebDriver driver;
	private ElementUtil eleUtil;
	// in page class we can write the below - we not write any testNG code in these class
	//1. private By Loactors
	//2. page class - public constructor
	//3. page class - public actions / methods
	
	//1. private By Loactors
	
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.cssSelector("input.btn.btn-primary");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	
	private By register = By.linkText("Register");
	
	//2. page class - public constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. page class - public actions / methods
	@Step("Getting Login Page Title ..")
	public String getLoginPageTitle() {
		
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		//System.out.println("login page title : "+title);
		Log.info("login page title : "+title);
		return title;
		
	}
	
	@Step("Getting Login Page Url ..")
	public String getLoginPageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_URL_FRANCTION, TimeUtil.DEFAULT_MEDIUM_TIME);
		//System.out.println("login page url : "+url);
		Log.info("login page url : "+url);
		return url;
	}
	
	@Step("Getting the state of Forgot Password Link")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	@Step("Login with UserName :{0} and Password :{1}")
	public AccountsPage doLogin(String username, String pwd) {
		//System.out.println("user cred : "+username +" "+pwd);
		Log.info("User cred : "+username +" "+pwd);
		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_MEDIUM_TIME).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);		
		eleUtil.doClick(loginButton);
		
		return new AccountsPage(driver); // constructor of the account page will call and supply the driver
	}
	
	@Step("Nevigating to the registration page")
	public RegisterationPage nevigateToregisterationPage() {
		eleUtil.waitForElementVisible(register, TimeUtil.DEFAULT_LONG_TIME).click();
		return new RegisterationPage(driver);
	}
	
}
