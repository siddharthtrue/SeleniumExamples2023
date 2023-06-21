package seleniumSessions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 * Find links on a page using for and adv. for loop. Find the href attribute of all links using getAttribute().
 * Find all images on a page and print the src and alt attribute values.
 */

public class AmazonTest3
{
	public static void main(String[] args) throws InterruptedException
	{
		BrowserUtils browser = new BrowserUtils();
		WebDriver driver = browser.initDriver("chrome"); // stored in Webdriver driver to get access to driver.
		browser.launchURL("https://amazon.com");	

//		List<WebElement> allLinks = driver.findElements(By.tagName("a"));
//		System.out.println("Total links: " + allLinks.size());
//
//// For loop
//		for(int i = 1; i < allLinks.size(); i++)
//		{
//			String e = allLinks.get(i).getText();
//			if(e.length() > 0)
//			{
//				System.out.println(i + " - " + e);
//				WebElement ele = allLinks.get(i);
//				System.out.println(ele.getAttribute("href"));
//			}			
//		}
//		
// For each loop (Advance for loop)
//		int count = 0;
//		for(WebElement e: allLinks)
//		{
//			String linkName = e.getText();
//			if(linkName.length() > 0)
//			{
//				
//				System.out.println(count + " - " + linkName);
//				System.out.println(e.getAttribute("href"));
//			}
//			count++;
//		}
		
		System.out.println("===========================================================");

// Below code does not use any of the utilities.		
//		List<WebElement> imageList = driver.findElements(By.tagName("img"));
//		System.out.println("Total images count on this page: " + imageList.size());
//		
//		int counter = 1;
//		for(WebElement imgList: imageList)
//		{
//			System.out.println(counter + " - " + imgList.getAttribute("alt"));
//			System.out.println(imgList.getAttribute("src"));
//			counter++;
//		}

		System.out.println("===========================================================");
// Below code uses the utilities.
		By links = By.tagName("a");
		By images = By.tagName("img");
		
		ElementUtils eUtils = new ElementUtils(driver);
		int totalImagesCount = eUtils.getElementsCount(images);
		System.out.println(totalImagesCount);
		int totalLinksCount = eUtils.getElementsCount(links);
		System.out.println(totalLinksCount);
		
		List<String> imgAttrValues = eUtils.getElementsAttributeValues(images, "alt");
		if(imgAttrValues.contains("Computer mice")) // This is validation, for this return statement is added in method - getElementsAttributeValues.
		{
			System.out.println("PASS");
		}
		List<String> linksAttrValues = eUtils.getElementsAttributeValues(links, "href");
		System.out.println(linksAttrValues); // prints entire list
		for(int i=0; i < linksAttrValues.size(); i++) // this code is used to write all elements in list line by line.
		{
			System.out.println(linksAttrValues.get(i));
		}
		
		System.out.println("===========================================================");
// Using utility - searchFromSuggestions
		By search = By.id("twotabsearchtextbox");
		By suggestionList = By.xpath("//div[@class='s-suggestion-container']");
		eUtils.searchAndClickFromSuggestions(search, "Shoes", suggestionList, "shoes for girls");

		System.out.println("===========================================================");
// Using utility - checkElementIsDisplayed
		
		eUtils.IsElementDisplayed(search);
		
		browser.quitBrowser();
	}
}
