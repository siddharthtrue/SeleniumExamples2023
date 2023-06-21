package seleniumSessions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 * find placeholders before sendkeys, sendkeys, find value attribute after sendkeys and used some utilities. 
 */

public class RegisterAccountTest2
{
	public static void main(String[] args)
	{
		BrowserUtils browser = new BrowserUtils();
		WebDriver driver = browser.initDriver("chrome"); // stored in Webdriver driver to get access to driver.
		browser.launchURL("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
		
// Since driver.quit or other methods with driver are not present so we may get null pointer exception.
// Due to this we need to return driver from browserutils class.
// Based on line 10 above, driver gets life as shown below.		

		ElementUtils element = new ElementUtils(driver);
		
		By firstname = By.id("input-firstname");
		By lastname = By.id("input-lastname");
		By email = By.id("input-email");
		By telephone = By.id("input-telephone");
		By password = By.id("input-password");
		By confirmPassword = By.id("input-confirm");

// Below code finds the placeholder value before sendkeys into fields by using getAttribute method.
		System.out.println("Placeholder for First Name: " + element.doGetAttributeValue(firstname, "placeholder"));
		System.out.println("Placeholder for Last Name: " + element.doGetAttributeValue(lastname, "placeholder"));
		System.out.println("Placeholder for E-mail: " + element.doGetAttributeValue(email, "placeholder"));
		System.out.println("Placeholder for Telephone: " + element.doGetAttributeValue(telephone, "placeholder"));
		System.out.println("Placeholder for Password: " + element.doGetAttributeValue(password, "placeholder"));
		System.out.println("Placeholder for Password Confirm: " + element.doGetAttributeValue(confirmPassword, "placeholder"));
		
		element.doSendKeys(firstname, "Siddharth");
		element.doSendKeys(lastname, "Shah");
		element.doSendKeys(email, "au9bc@gmaiul.com");
		element.doSendKeys(telephone, "7020779955");
		element.doSendKeys(password, "Siddharth123");
		element.doSendKeys(confirmPassword, "Siddharth123");

// Below code finds the value attribute of each field after sendkeys. (outputs value entered in fields) 

		System.out.println("Value entered in First Name field is: " + element.doGetAttributeValue(firstname, "value"));
		System.out.println("Value entered in Last Name field is: " + element.doGetAttributeValue(lastname, "value"));
		System.out.println("Value entered in E-mail field is: " + element.doGetAttributeValue(email, "value"));
		System.out.println("Value entered in Telephone field is: " + element.doGetAttributeValue(telephone, "value"));
		System.out.println("Value entered in Password field is: " + element.doGetAttributeValue(password, "value"));
		System.out.println("Value entered in Password Confirm field is: " + element.doGetAttributeValue(confirmPassword, "value"));
		
		System.out.println("========================================================");
		By agreeChkBox = By.xpath("//input[@name='agree']");
		element.doClick(agreeChkBox);
		By BtnContinue = By.xpath("//input[@type='submit']");
		element.doClick(BtnContinue);
		
		By acctCreate = By.tagName("h1");
		System.out.println(element.doGetElementText(acctCreate));
		
		By searchTxtBox = By.name("search");
		if(element.checkElementIsDisplayed(searchTxtBox))
		{
			System.out.println("Element is displayed");
		}

		System.out.println("========================================================");

// Utility used - getElementsTextList
		By rightPanelLinks = By.className("list-group-item");
		if(element.getElements(rightPanelLinks).size()==13)
		{
			System.out.println("Right panel links count is correct");
		}
		List<String> rightPanelLinksTextList = element.getElementsTextList(rightPanelLinks);
		if(rightPanelLinksTextList.contains("My Account"))
		{
			System.out.println("My Account - PASS");
		}
		
		System.out.println("========================================================");

// Utility used - clickElementFromPageSection
		element.clickElementFromPageSection(rightPanelLinks, "Forgotten Password");
		
		driver.findElement(By.xpath("(//a[contains(text(), 'Tablets')])[1]")).click();

		System.out.println("========================================================");

// Utility used - doSelectDropdownByIndex
		By sortBy = By.id("input-sort");
		By show = By.id("input-limit");
		
//		element.doSelectDropdownByIndex(sortBy, 2);
//		element.doSelectDropdownByIndex(show, 2);
		
		element.doSelectDropdownByVisisbleText(sortBy, "Model (A - Z)");
		element.doSelectDropdownByVisisbleText(show, "75");
		
//		element.doSelectDropdownByValueAttribute(sortBy, "");
//		element.doSelectDropdownByValueAttribute(show, "");
		
		System.out.println("========================================================");
// Utility used - getAllDropdownOptions
		element.getAllDropdownOptions(sortBy);
		element.getAllDropdownOptions(show);
		
		element.doSelectDropdownValue(show, "100");
		
		browser.quitBrowser();
	}
}
