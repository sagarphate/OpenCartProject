package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	private final String DEFAULT_ELEMENT_TIME_OUT_MESSAGE = "Time Out.. Element Is Not Found";
	private final String DEFAULT_ALERT_TIME_OUT_MESSAGE = "Time Out.. Alert Is Not Found";
	
	private void nullBlankCheck(String value ) {
		if(value == null || value.length() == 0) {
			throw new ElementException("VALUE OR VISIBLE TEXT CAN NOT BE NULL OR BLANK");
		}
	}
	
	public ElementUtil(WebDriver driver) {  // Constructor to initialize the driver
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	private void checkHighlight(WebElement element) {  // method for highlight the element
		if(Boolean.parseBoolean(DriverFactory.hightlight)) {
			jsUtil.flash(element);
		} 
	}
	
	@Step("getting webelemet using locator {0}")
	public WebElement getElement(By locator) {  // Responsible for create / finding WebElement
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			checkHighlight(element);
		}
		catch(NoSuchElementException e){
			System.out.println("Element NOT Present on the Page ..");
			e.printStackTrace();
		}
		return element;
	}
	
	public List<WebElement> getElements(By locator) {
		return	driver.findElements(locator);
	}
	
	@Step("Entering value :{1} in element :{0}")
	public void doSendKeys(By locator, String value) { //do the action with help of element and value 
		nullBlankCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	@Step("Clicking on element ussing locator :{0}")
	public void doClick(By locator) { // click on WebElement once address found
		getElement(locator).click();
	}

	public String getElementText(By locator) { // Method for get the text when we pass the by locator
		return getElement(locator).getText();
	}
	
	public String getElementGetAttibute(By locator, String attName) {
		return getElement(locator).getAttribute(attName);
	}
	
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	@Step("Checking the Element {0}")
	public boolean isElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	public boolean isElementExist(By locator) {
		if(getElements(locator).size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean multipleElementExist(By locator) {
		List<WebElement> logo = getElements(locator);
		if(logo.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public  ArrayList<String> getElementsTextList(By locator) {
		List<WebElement> list = getElements(locator);
		ArrayList<String> finallist = new ArrayList<String>();
		
		for(WebElement e : list) {
			String text = e.getText();
			if(text.length() !=0) {
				finallist.add(text);
			}
		}
		return finallist;
	}
	
	public ArrayList<String> getElementAttributeList(By locator, String attName) {
		
		 List<WebElement> attList = getElements(locator);
		 ArrayList<String> attFinalList = new ArrayList<String>();
		 
		 for(WebElement e : attList) {
			 String attValue = e.getAttribute(attName);
			 if(attValue.length() !=0 ) {
				 attFinalList.add(attValue);

			 }
 		 }
		 return attFinalList;
	}
	/**
	 * Used for Any Search and click on specific search value
	 * @param searchlocator
	 * @param searchSuggutions
	 * @param searchkey
	 * @param value
	 * @throws InterruptedException
	 */
	public void doSerch(By searchlocator, By searchSuggutions, String searchkey , String value ) throws InterruptedException {
		
		getElement(searchlocator).sendKeys(searchkey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(searchSuggutions);
		
		for(WebElement e: suggList) {
			String text = e.getText();
			if(text.contains(value)) {
				e.click();
				break;
			}
		}
	}
	
 
	//******************************** Select Base Drop Down Utility *********************************************
	
	public void doSelectByIndex(By locator, int index ) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	public void doSelectByValue(By locator, String value) {
		nullBlankCheck(value); // custom Exception Handling method for value
		
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public void doSelectByVisibleText(By locator, String visibleText) {
		nullBlankCheck(visibleText); // custom Exception Handling method for visibleText
		
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}
	
	public List<String> getDropDownOptionsTextList(By locator) {
 		List<WebElement> optionsList = getDropDownOptionsList(locator);
 		List<String> optionsTextList = new ArrayList<String>(); // Top Casting
 		
 		for(WebElement e : optionsList) {
 			String text = e.getText();
 			optionsTextList.add(text);
 		}
 		return optionsTextList;
	}
	
	public List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}
	
	public int getDropDownValueCount(By locator) {
		return getDropDownOptionsList(locator).size();
	}
	
	// With Out used Existing method we are able to select the specific value from drop down list
	public void doSelectDropDownValue(By Locator, String value ) {
		
		nullBlankCheck(value);
		
		List<WebElement> optionList = getDropDownOptionsList(Locator);
		for(WebElement e : optionList ) {
			String text = e.getText();
		
			if(text.equals(value)) {
				e.click();
				break;
			}
		}	
	}
	
	public void printSelectDropDownValue(By Locator) {
		List<WebElement> optionsList = getDropDownOptionsList(Locator);
		for(WebElement e : optionsList) {
			String optionText = e.getText();
			System.out.println(optionText);
		}
	}
	
		// select the value from drop down list and click on that ** WITH OUT SELECT CLASS *** 
		public void doSelectValueFromDown(By locator, String value) {
			List<WebElement> optionList = getElements(locator);
			for(WebElement e : optionList) {
				String text = e.getText();
				
				if(text.equals(value)) {
					e.click();
					break;
				}
			}	
		}
	
//********************************Action Class Utility  ******************************************** //

		public void handleMenuSubMenuLevelTwo(By parentLocator, By menuLocator) throws InterruptedException {
			
			Actions act = new Actions(driver);
			act.moveToElement(getElement(parentLocator)).perform();
			Thread.sleep(2000);
			// getElement(menuLocator).click();
			doClick(menuLocator);
			
		}

		
		public void handleMenuSubMenuLevelFour(By lelvelOneMenuLocator, By levelTwoMenuLocator, By levelThreeMenuLocator, By levelfourMenuLocator) throws InterruptedException {
			//getElement(lelvelOneMenuLocator).click();
			doClick(lelvelOneMenuLocator);
			
			Actions act = new Actions(driver);
			
			Thread.sleep(2000);
			act.moveToElement(getElement(levelTwoMenuLocator)).perform();
			Thread.sleep(2000);
			act.moveToElement(getElement(levelThreeMenuLocator)).perform();
			Thread.sleep(2000);
			
			//getElement(levelfourMenuLocator).click();
			doClick(levelfourMenuLocator);
		}

		public void doActionsSendKeys(By locator, String value) {
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator), value).perform();
		}
		
		public void doActionsClick(By locator) {			
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator)).perform();
		}

// ****************************** Wait Utility ****************************************************
		/**
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeOut
		 */
		public void clickWhenReady(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			checkHighlight(element);
			element.click();
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			checkHighlight(element);
			return element;
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		
		@Step("waiting for element using locator {0} with in time out of {1}")
		public WebElement waitForElementVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			checkHighlight(element);
			return element;
		}
		
		/**
		 * An expectation for checking that there is at least one element present on a web page.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		public List<WebElement> waitForElementsPresenceWithFlientWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			                           .withTimeout(Duration.ofSeconds(timeOut))
			                           .pollingEvery(Duration.ofSeconds(pollingTime))
			                           .ignoring(NoSuchElementException.class)
			                           .withMessage(DEFAULT_ELEMENT_TIME_OUT_MESSAGE);
			
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locator are visible.
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * default interval time = 500 milliseconds 
		 * @return
		 */
		public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * //Wait till give total time with polling time / intervalTime
		 * sleep The duration in milliseconds to sleep between polls.
		 * @param locator
		 * @param timeOut
		 * @param intervalTime
		 * @return
		 */
		
		public List<WebElement> waitForElementsVisible(By locator, int timeOut, int intervalTime) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(intervalTime));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		public String waitForTitleContains(String titleFranctionValue, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if (wait.until(ExpectedConditions.titleContains(titleFranctionValue)))
					return driver.getTitle();
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("Title id NOT Found With In = " +timeOut);
			}
			return null;
		}
		
		@Step("Waiting for the Expected Title")
		public String waitForTitleIs(String title, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if (wait.until(ExpectedConditions.titleIs(title)))
					return driver.getTitle();
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("Title id NOT Found With In = " +timeOut);
			}
			return null;
		}
		
		public String waitForUrlContains(String urlFranctionValue, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if (wait.until(ExpectedConditions.urlContains(urlFranctionValue)))
					return driver.getCurrentUrl();
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("URL id NOT found With In =" +timeOut);
			}
			return null;
		}
		
		public String waitForUrlToBe(String url, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if (wait.until(ExpectedConditions.urlToBe(url)))
					return driver.getCurrentUrl();
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("URL id NOT found With In =" +timeOut);
			}
			return null;
		}
		
		
// ************************ wait Alert Utility *********************		

		public Alert waitForAlertPresenceWithFlientWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			                           .withTimeout(Duration.ofSeconds(timeOut))
			                           .pollingEvery(Duration.ofSeconds(pollingTime))
			                           .ignoring(NoAlertPresentException.class)
			                           .withMessage(DEFAULT_ALERT_TIME_OUT_MESSAGE);
			
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		
		public Alert waitForJSAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public String getAlertText(int timeOut) {
			Alert alert = waitForJSAlert(timeOut);
			return alert.getText();
		}
		
		public  void acceptAlert(int timeOut) {
			waitForJSAlert(timeOut).accept();
		}
		
		public void dismissAlert(int timeOut) {
			waitForJSAlert(timeOut).dismiss();
		}
		
		public void alertSendKeys(int timeOut, String value) {
			waitForJSAlert(timeOut).sendKeys(value);
		}
		
		public boolean waitForWindows(int totalNumberOfWindow, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.numberOfWindowsToBe(totalNumberOfWindow));
		}
		
		public void waitForFrameAndSwitchToIt(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		}
		
		public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		
		public void waitForFrameAndSwitchToIt(String frameName, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
		}
		
		public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}
		
		public WebElement retryingElement(By locator, int timeOut) {
			WebElement element = null;
			int attempts = 0;
			while (attempts < timeOut) {
				try{
					element = driver.findElement(locator);
					System.out.println("Element is found in attempt : " + attempts);
					break;
				}
				catch (NoSuchElementException e) {
					System.out.println("Element is NOT found in attempt :" + attempts);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}	
				attempts++;	
			}
			if(element == null) {
				System.out.println("Element Is NOT Found Try For "+ timeOut +" times "+ " With Interval Time Of " + 500 + " MilliSeconds" );
				throw new ElementException("No Such Element");
			}	
			checkHighlight(element);
			return element;
		}
		
		
		public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
			WebElement element = null;
			int attempts = 0;
			while (attempts < timeOut) {
				try{
					element = driver.findElement(locator);
					System.out.println("Element is found in attempt : " + attempts);
					break;
				}
				catch (NoSuchElementException e) {
					System.out.println("Element is NOT found in attempt :" + attempts);
					try {
						Thread.sleep(intervalTime * 1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}	
				attempts++;	
			}
			if(element == null) {
				System.out.println("Element Is NOT Found Try For "+ timeOut +" times "+ " With Interval Time Of " + intervalTime + " Seconds" );
				throw new ElementException("No Such Element");
			}	
			checkHighlight(element);
			return element;
		}
		
}
