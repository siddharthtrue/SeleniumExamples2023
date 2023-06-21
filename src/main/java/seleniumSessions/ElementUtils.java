package seleniumSessions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtils
{
	private WebDriver driver; // private because we do not want webdriver driver to be used by any 
							  // function and face nullpointer exception.
	private final int DEFAULT_TIME_OUT = 5;
	
	public ElementUtils(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void doSendKeys(By locator, String value)
	{
		if(value==null)
		{
			System.out.println("Null values are not allowed");
			throw new MyException("VALUECANNOTBENULL");
		}
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}
	
	public void doClick(By locator)
	{
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut)
	{
		checkElementClickable(locator, timeOut).click();
	}

	public WebElement getElement(By locator, int timeOut)
	{
		return waitForElementVisible(locator, timeOut);
	}
	
//	public WebElement getElement(By locator)
//	{
//		return driver.findElement(locator);
//	}
	
// Above method getElements does not cover all cases like element not found, element found after sometime.
// Below method covers all of them including element found.
	
	public WebElement getElement(By locator)
	{
		WebElement element = null; // local var is used here because we need to use it in try catch blocks below.
		try
		{
			element = driver.findElement(locator); // element found
			System.out.println("Element found with locator: " + locator);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Element not found with locator: " + locator); // element not found
			element = waitForElementVisible(locator, DEFAULT_TIME_OUT);
		}
		return element;
	}
	
	public void doClear(By locator)
	{
		//getElement(locator).clear();
		driver.findElement(locator).clear();
	}
	
	public String doGetElementText(By locator)
	{
		return getElement(locator).getText();
	}
	
	public boolean checkElementIsDisplayed(By locator)
	{
		return getElement(locator).isDisplayed();
	}
	
	public String doGetAttributeValue(By locator, String attrName)
	{
//		return getElement(locator).getAttribute(attrName);
		return driver.findElement(locator).getAttribute(attrName);
	}
	
	public List<WebElement> getElements(By locator)
	{
		return driver.findElements(locator);
	}
	
	public int getElementsCount(By locator)
	{
		return getElements(locator).size();
	}
	
	public List<String> getElementsAttributeValues(By locator, String attrName)
	{
		List<WebElement> elementList = getElements(locator);
		List<String> elementAttributes = new ArrayList<String>(); // in order to return array, used this statement.
		for(WebElement e: elementList)
		{
			String attrValues = e.getAttribute(attrName);
			elementAttributes.add(attrValues);
		}
		return elementAttributes; // method returning something helps to validate the values returned.
	}
	
	public List<String> getElementsTextList(By locator)
	{
		List<WebElement> elementsLinksList = getElements(locator);
		List<String> elementsTextList = new ArrayList<String>();
		for(WebElement e: elementsLinksList)
		{
			String text = e.getText();
			elementsTextList.add(text);
		}
		return elementsTextList;
	}
	
	public void clickElementFromPageSection(By locator, String eleText)
	{
		List<WebElement> elementList = getElements(locator);
		for(WebElement e: elementList)
		{
			String text = e.getText();
			System.out.println(text);
			if(text.equals(eleText))
			{
				e.click();
				break;
			}
		}
	}
	
	public boolean IsElementDisplayed(By locator)
	{
		List<WebElement> eleList = getElements(locator);
		if(eleList.size() > 0)
		{
			System.out.println(locator + " element is present on the page");
			return true;
		}
		else
			return false;
	}
	
	public void searchAndClickFromSuggestions(By searchLocator, String searchText, By suggestionList, String searchedText) throws InterruptedException
	{
		doSendKeys(searchLocator, searchText);
		Thread.sleep(3000);
		List<WebElement> suggestions = getElements(suggestionList);
		System.out.println("Total suggestions: " + suggestions.size());
		
		if(suggestions.size() > 0)
		{
			for(WebElement e: suggestions)
			{
				String text = e.getText();
				System.out.println(text);
				if(text.length() > 0)
				{
					if(text.contains(searchedText))
					{
						e.click();
						break;
					}
				}
				else
				{
					System.out.println("Blank values -- no suggestions");
					break;
				}
			}
		}
		else
		{
			System.out.println("No search suggesions found");
		}
	}

// Drop down utilities	
	
	public void doSelectDropdownByIndex(By locator, int indexNum)
	{
		Select sel = new Select(getElement(locator));
		sel.selectByIndex(indexNum);
	}
	
	public void doSelectDropdownByVisisbleText(By locator, String text)
	{
		Select sel = new Select(getElement(locator));
		sel.selectByVisibleText(text);
	}
	
	public void doSelectDropdownByValueAttribute(By locator, String value)
	{
		Select sel = new Select(getElement(locator));
		sel.selectByValue(value);
	}
	
	public int getDropDownValueCount(By locator)
	{
		return getAllDropdownOptions(locator).size();
	}
	
// If we do not want to use select by index, value and visible text, we can use getOptions as shown below
	public List<String> getAllDropdownOptions(By locator)
	{
		Select sel = new Select(getElement(locator));
		List<WebElement> allOptions = sel.getOptions();
		List<String> allOptionsList = new ArrayList<>();
		System.out.println(allOptions.size());
		
		for(WebElement e: allOptions)
		{
			String text = e.getText();
			System.out.println(text);
			allOptionsList.add(text);
		}
		return allOptionsList;
	}
	
	public boolean doSelectDropdownValue(By locator, String dropDownValue)
	{
		boolean flag = false;
		Select sel = new Select(getElement(locator));
		List<WebElement> allOptions = sel.getOptions();
		System.out.println(allOptions.size());		
		
		for(WebElement e: allOptions)
		{
			String text = e.getText();
			System.out.println(text);
			if(text.contains(dropDownValue))
			{
				flag = true;
				e.click();
				break;
			}
		}
		if(flag==false)
		{
			System.out.println(dropDownValue + " is not present in the dropdown " + locator);
		}
		return flag;
	}
	
// If we do not want to use select by index, value and visible text also we do not want to use getOptions
// then use below code. for any dropdown that does not have select html tag then we can use this code.
	public boolean doSelectValueFromDropdownWithoutSelect(By locator, String value)
	{
		boolean flag = false;
		List<WebElement> allOptions = getElements(locator);		
		
		for(WebElement e: allOptions)
		{
			String text = e.getText();
			if(text.equals(value))
			{
				flag = true;
				e.click();
				break;
			}
		}
		if(flag==false)
		{
			System.out.println(value + " is not present in the dropdown " + locator);
		}
		return flag;
	}
	
	public void doSelectValueFromMultiSelectDropDown(By locator, String... value) // String... value means value can contain an array or strings.
	{ // This method is used in LambdaTest4.java and it should satisfy all scenarios like
	// 1. Single selection, 1. Multiple selection, 3. All selection 
		
		boolean flag = false;
		if(value[0].equalsIgnoreCase("all")) // This is random string passed which is equal to the parameter in test file lambdatest4.java.
		{
// all selection logic
			List<WebElement> allItemsList = getElements(locator);
			System.out.println("Total elements present in multiselect drop down are: " + allItemsList.size());
			
			for(WebElement e: allItemsList)
			{
				System.out.println(e.getText());
				e.click();
				flag = true;
			}
		}
		else
		{
			WebElement element = getElement(locator);
			Select sel = new Select(element);
			if(sel.isMultiple());
			System.out.println(locator + " is a multiselect drop down");
			
			for(int i = 0; i < value.length; i++)
			{
				sel.selectByVisibleText(value[i]);
			}
			
			WebElement first = sel.getFirstSelectedOption();
			System.out.println(first.getText() + " is a first selected option");
			
			List<WebElement> allSelectedOptions = sel.getAllSelectedOptions();
			System.out.println("Selected options are: ");
			
			for(WebElement e: allSelectedOptions)
			{
				String text = e.getText();
				System.out.println(text);
			}
		}
		if(flag==false)
		{
			System.out.println("Choice is not available");
		}
	}
	
/*******  Action related utilities ********/
	public void doActionsSendKeys(By locator, String value)
	{
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator)
	{
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void doActionsClick(By locator, int timeOut)
	{
		Actions act = new Actions(driver);
		act.click(checkElementClickable(locator, timeOut)).build().perform();
	}

	public void doDragAndDrop(By sourceLocator, By targetLocator)
	{
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();
	}

	public void doContextClick(By locator)
	{
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).build().perform();
	}

	public void doMoveToElement(By locator)
	{
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
	}
	
	public void twoLvlHover(By parentMenu, By childMenu) throws InterruptedException
	{	
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}
	
	public void twoLvlHover(By parentMenu, String childMenuLinkText) throws InterruptedException
	{
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(By.linkText(childMenuLinkText));
	}
	
	public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText, String level4LinkText) throws InterruptedException
	{
		WebElement level1 = getElement(parentMenuLocator);
		Actions act = new Actions(driver);

		act.moveToElement(level1).build().perform();
		Thread.sleep(1000);

		WebElement level2 = getElement(By.linkText(level2LinkText));
		act.moveToElement(level2).build().perform();
		Thread.sleep(1000);

		WebElement level3 = getElement(By.linkText(level3LinkText));
		act.moveToElement(level3).build().perform();
		Thread.sleep(1000);

		doClick(By.linkText(level4LinkText));
	}
	
/*******  Wait related utilities ********/
	
	public Alert waitForAlertJsPopUpWithFluentWait(int timeOut, int pollingTime)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoAlertPresentException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...Alert is not found.....");
		
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForAlertJsPopUp(int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String alertJSGetText(int timeOut)
	{
		return waitForAlertJsPopUp(timeOut).getText();
	}

	public void alertAccpet(int timeOut)
	{
		waitForAlertJsPopUp(timeOut).accept();
	}

	public void alertDismiss(int timeOut)
	{
		waitForAlertJsPopUp(timeOut).dismiss();
	}

	public void EnterAlertValue(int timeOut, String value)
	{
		waitForAlertJsPopUp(timeOut).sendKeys(value);
	}
	
	public String waitForTitleIsAndCapture(String titleFraction, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleContains(titleFraction)))
		{
			String title = driver.getTitle();
			return title;
		}
		else
		{
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}
	
	public String waitForFullTitleAndCapture(String titleVal, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleIs(titleVal)))
		{
			String title = driver.getTitle();
			return title;
		}
		else
		{
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}
	
	public  String waitForURLContainsAndCapture(String urlFraction, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.urlContains(urlFraction)))
		{
			String url = driver.getCurrentUrl();
			return url;
		}
		else
		{
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}
	
	public String waitForURLAndCapture(String urlValue, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.urlToBe(urlValue)))
		{
			String url = driver.getCurrentUrl();
			return url;
		}
		else
		{
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}
	
	public boolean waitForTotalWindows(int totalWindowsToBe, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
	}
	
/*
 * Below method checks if element is present in DOM but not on UI. That is why its not proper functionality
 * to use. Element is always available on DOM first and then on UI.	
 */
// * An expectation for checking that an element is present on the DOM of a page.
// * This does not necessarily mean that the element is visible.
	
	public WebElement waitForElementPresent(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public WebElement waitForElementVisible(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * default timeout = 500 ms
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	//default timeout = intervalTime
	public List<WebElement> waitForElementsVisible(By locator, int timeOut, int intervalTime)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public WebElement checkElementClickable(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	//frames with wait:
	
	public void waitForFrameAndSwitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchFrameException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...frame is not found.....");		
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public void waitForFrameAndSwitchToItByFrameLoctor(int timeOut, By frameLocator)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotInteractableException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...element is not found.....");
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotInteractableException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...element is not found.....");
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	public WebElement retryingElement(By locator, int timeOut)
	{
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut)
		{
			try
			{
				element = getElement(locator);
				System.out.println("element is found...." + locator + " in attempt : " + attempts);
				break;
			}
			catch (NoSuchElementException e)
			{
				System.out.println("element is not found..." + locator + " in attempt : " + attempts);
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null)
		{
			System.out.println("element is not found...tried for " + timeOut + " secs " + " with the interval of 500 millisecons");
		}
		return element;
	}
	
	public WebElement retryingElement(By locator, int timeOut, int pollingTime)
	{
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut)
		{
			try
			{
				element = getElement(locator);
				System.out.println("element is found...." + locator + " in attempt : " + attempts);
				break;
			}
			catch (NoSuchElementException e)
			{
				System.out.println("element is not found..." + locator + " in attempt : " + attempts);
				try
				{
					Thread.sleep(pollingTime);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null)
		{
			System.out.println("element is not found...tried for " + timeOut + " secs " + " with the interval of 500 millisecons");
		}
		return element;
	}

	public Boolean isPageLoaded()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == \'complete\'")).toString(); //"true"
		return Boolean.parseBoolean(flag);//true
	}

	public void waitForPageLoad(int timeOut)
	{
		long endTime = System.currentTimeMillis() + timeOut;
		while (System.currentTimeMillis() < endTime)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String pageState = js.executeScript("return document.readyState").toString();
			if (pageState.equals("complete"))
			{
				System.out.println("PAGE DOM is fully loaded now....");
				break;
			}
			else
			{
				System.out.println("PAGE IS not loaded...." + pageState);
			}
		}
	}
}
