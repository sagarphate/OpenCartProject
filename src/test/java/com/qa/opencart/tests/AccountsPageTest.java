package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	// before come to Account page the login is precondition 
	// without login how we can redirect to Account page
	// Account page Reference we can get from login page
	// After click on login page it redirect to Account page
	// then its method responsibility to retun the next page landing class object
	
	@BeforeClass
	public void accSetup() { 
		accountpage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password")); // inherite reference from base test .. easy to write TC 
	}
	
	
	@Test(priority = 2)
	public void accPageTitleTest() {
		String actTitle = accountpage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test(priority = 3)
	public void accPageURLTest() {
		String actUrl = accountpage.getAccPageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNTS_PAGE_URL_FRANCTION));
	}
	
	@Test(priority = 4)
	public void myAccountLinkExistTest() {
		Assert.assertTrue(accountpage.myAccountLinkExist());
	}
	
	@Test(priority = 1)
	public void isEditAccountLinkExistTest() {
		boolean flag = accountpage.isEditAccountLinkExist();
		Assert.assertTrue(flag);
	}
	
	@Test(priority = 5)
	public void accountsPageHeadersListTest() {
		List<String> actHeaderList = accountpage.getAccountsPageHeadersList();
		System.out.println(actHeaderList);
		Assert.assertTrue(actHeaderList.contains(AppConstants.ACCOUNTS_PAGE_TITLE));
	}
	
	@DataProvider
	public Object[][] getdoSearchTestData() {
		return new Object[][] {
			{"macbook"},
		};
	}
	
	@Test(priority = 6, dataProvider = "getdoSearchTestData")
	public void doSearchTest(String searchKey) {
		accountpage.doSearch(searchKey);
	}
}
