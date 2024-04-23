package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.qa.opencart.logger.Log;

public class OptionsManager {

	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	private Properties prop;

	// supply date to these with help of counstructor

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() { // read the headless -incognito - hightlight key value from config file
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			//System.out.println("Running Test Cases in Chrome Headless Browser");
			Log.info("Running Test Cases in Chrome Headless Browser");
			co.addArguments("--headless");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			Log.info("Running Test Cases in Chrome incognito Browser");
			co.addArguments("--incognito");
		}
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() { // read the headless -incognito - hightlight key value from config file
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			//System.out.println("Running Test Cases in Firefox Headless Browser");
			Log.info("Running Test Cases in Firefox Headless Browser");
			fo.addArguments("--headless");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			Log.info("Running Test Cases in forefox incognito Browser");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() { // read the headless -incognito - hightlight key value from config file
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			//System.out.println("Running Test Cases in Edge Headless Browser");
			Log.info("Running Test Cases in Edge Headless Browser");
			eo.addArguments("--headless");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			Log.info("Running Test Cases in Edge incognito Browser");
			eo.addArguments("--inprivate");
		}
		return eo;
	}
}
