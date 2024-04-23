package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterationPage;
import com.qa.opencart.pages.SearhResultsPage;
import com.qa.opencart.pages.ShoppingCartPage;

import io.qameta.allure.Step;

public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	
	protected LoginPage loginPage;  // create Reference of page
	protected AccountsPage accountpage;
	protected SearhResultsPage searchresultspage;
	protected ProductInfoPage productinfopage;
	protected ShoppingCartPage shoppingcartpage;
	protected RegisterationPage registerpage;
	
	protected SoftAssert softassert;
	
	@Step("SepUp: Launching {0} and initializing the properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver); 
		softassert = new SoftAssert();
	}

	@Step("Closing the Browser")
	@AfterTest
	public void tearDown() {
		//driver.quit();
	}
	
}
