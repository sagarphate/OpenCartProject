package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void ProductInfoSetup() {
		accountpage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductSearchData() {
		return new Object[][] {
				{"macbook","MacBook Pro"},
				{"imac","iMac"},
				{"samsung","Samsung SyncMaster 941BW"},
				{"samsung","Samsung Galaxy Tab 10.1"},
		};
	}
	
	@Test (dataProvider = "getProductSearchData")
	public void productHeaderTest(String searchKey, String productName) {
		searchresultspage =	accountpage.doSearch(searchKey);
		productinfopage = searchresultspage.selectProduct(productName);
		String actHeader = productinfopage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesCountData() {
	  return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7},
		};
	}
	
	//product
	@DataProvider
	public Object[][] getProductImagesCountDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
	}
	
	@Test(dataProvider = "getProductImagesCountDataFromExcel")
	public void productImagesCountTest(String searchKey, String productName, String imageCount) {  // images are fixed
		searchresultspage =	accountpage.doSearch(searchKey);
		productinfopage = searchresultspage.selectProduct(productName);
		Assert.assertEquals(productinfopage.getProductImagesCount(), Integer.parseInt(imageCount));
	}
	
	// Key for these map is = Brand, Product Code, Reward Points, Availability, productPrice, productExTaxPrice, productHeader, imageCount
	@Test
	public void productInfoTest() {
		searchresultspage =	accountpage.doSearch("macbook");
		productinfopage = searchresultspage.selectProduct("MacBook Pro");
		Map<String, String> actProductDetailsMap = productinfopage.getProductDetailsMap();
	
		
		softassert.assertEquals(actProductDetailsMap.get("Brand"), "Apple");
		softassert.assertEquals(actProductDetailsMap.get("Product Code"), "Product 18");
		softassert.assertEquals(actProductDetailsMap.get("Reward Points"), "800");
		softassert.assertEquals(actProductDetailsMap.get("Availability"), "In Stock");
		softassert.assertEquals(actProductDetailsMap.get("productPrice"), "$2,000.00");
		softassert.assertEquals(actProductDetailsMap.get("productExTaxPrice"), "$2,000.00");
		
		softassert.assertAll();
		
		
		/*
		 // Hard Assertion - if any one fail then stop the execution and terminate the program
		Assert.assertEquals(actProductDetailsMap.get("Brand"), "Apple");
		Assert.assertEquals(actProductDetailsMap.get("Product Code"), "Product 18");
		Assert.assertEquals(actProductDetailsMap.get("Reward Points"), "800");
		Assert.assertEquals(actProductDetailsMap.get("Availability"), "In Stock");
		Assert.assertEquals(actProductDetailsMap.get("productPrice"), "$2,000.00");
		Assert.assertEquals(actProductDetailsMap.get("productExTaxPrice"), "$2,000.00");
		Assert.assertEquals(actProductDetailsMap.get("productHeader"), "MacBook Pro");
		Assert.assertEquals(actProductDetailsMap.get("imageCount"), "4");
		
		*/
	}
	
}
