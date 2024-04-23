package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Open Cart Login Page")
@Story("User Story: 101 Design Login Page Feature for Open Card appliation")
@Feature("Feature 201: Adding Login Feature")
public class LoginPageTest extends BaseTest{
	
	@Description("Checking the Login Page Title")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		 String actTitle = loginPage.getLoginPageTitle();
		 Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Description("Checking the Login Page URL")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 2)
	public void loginPageUrlTest() {
		 String actURL = loginPage.getLoginPageUrl();
		 Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRANCTION), AppError.URL_NOT_FOUND);
	}
	
	@Description("Checking the Forgot Password link on the Page")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		boolean flag = loginPage.isForgotPwdLinkExist(); 
		Assert.assertTrue(flag);		
	}
	
	@Description("Checking User is able to Login OR not")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4)
	public void loginTest() {
		accountpage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accountpage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
}
