package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ShoppingCartPageTest extends BaseTest{
	
	@BeforeClass
	public void shoppingCartPageSetup() {
		accountpage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getproductModelDetailsTestData() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
		};
	}
	
	@Test(dataProvider = "getproductModelDetailsTestData")
	public void productModelDetailsTest(String searchKey, String productName) {
		searchresultspage = accountpage.doSearch(searchKey);
		productinfopage = searchresultspage.selectProduct(productName);
		shoppingcartpage = productinfopage.productAddToCart();
		
		String actModelName = shoppingcartpage.getproductModelDetails();
		Assert.assertEquals(actModelName, "Product 18");
	}
	
	
	/** One page to another page / Landing page work flow
	 *  Account Page  		 <- go to <-	 Login Page
	 *  Search Results Page  <- go to <-	 Account Page
	 *  Product Info Page    <- go to <-	 Search Results Page
	 *  Shopping Cart Page   <- go to <-	 Product Info Page
	 */
	
}
