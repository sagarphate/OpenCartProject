package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

public class DriverFactory {

	// initialize the driver and launch the browser

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;

	public static String hightlight;
	
	// create personal local thread / driver for execution - for parallel Execution 
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();  ;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		//System.out.println("browser name is : " + browserName);
		Log.info("browser name is : " + browserName); // log 4J
		hightlight = prop.getProperty("highlight"); // to highligt elelemt

		optionsManager = new OptionsManager(prop);
		
		switch (browserName) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set( new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		default:
			//System.out.println("Please pass the correct browser name : " + browserName);
			Log.error("Please pass the correct browser name : " + browserName);
			throw new BrowserException("NO BROWSER FOUND .." + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	// To run the Test cases in different Environemnt
	// like - envName = qa, stage, proad, uat , dev
	// use the command as
	// mvn clean install -Denv = "qa" -- env is the vaiable name and qa is the value
	// passed to these variable
	// to read these value we can use System class from java
	
	public Properties initProp() {

		String envName = System.getProperty("env");
		System.out.println("Running Test Cases in Environment : " + envName); 
		
		FileInputStream file = null;
		prop = new Properties();

		try {
			if (envName == null) {
				System.out.println("No Environment id given Hence RUnning on the QA Environment..");
				file = new FileInputStream("./src/test/resources/config/config.qa.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					file = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "dev":
					file = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "stage":
					file = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					file = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "prod":
					file = new FileInputStream("./src/test/resources/config/config.properties");
				default:
					System.out.println("Please Pass the Right Environment Name : " + envName);
					throw new FrameworkException(AppError.ENVIRONMENT_NAME_NOT_FOUND + " : " + envName);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Properties file is not Found ..");
			e.printStackTrace();
		}

		try {
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	/**
	 * take screenshot
	 */
	
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);//temp directory
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}
