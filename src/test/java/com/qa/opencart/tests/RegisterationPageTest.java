package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegisterationPageTest extends BaseTest {
	
	@BeforeClass
	public void RegisterationPageSetup() {
		registerpage = loginPage.nevigateToregisterationPage();
	}
	
	@DataProvider
	public Object[][] getUserRegisterationTestData() {
		return new Object[][] {
			{"Shubham", "G", "9865231478", "gg@123", "no"},
			{"Narayan", "P", "9874563214", "PP@123", "yes"},
			{"Jivan", "S", "8866552233", "SS@123", "no"},
		};
	}
	
	@DataProvider  // Read the Data from Excel File
	public Object[][] getUserRegisterationTestDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserRegisterationTestData")
	public void userRegisterationTest(String firstName, String lastName, String telephone, String password, String subcrib) { // application not accept duplicate EMail id - create one String utility for generate random email id
		Assert.assertTrue(registerpage.userRegisteration(firstName,lastName,StringUtils.getRandomEmailId(),telephone,password,subcrib));
	}
	
}
