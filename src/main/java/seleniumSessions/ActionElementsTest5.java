package seleniumSessions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionElementsTest5
{
	static WebDriver driver;
	
	public static void main(String args[]) throws InterruptedException
	{
		driver = new ChromeDriver();
		driver.get("http://mrbool.com/search/?txtsearch=how-to-create-menu-with-submenu-using-css-html");
		
		By FirstLvlMenu = By.xpath("//a[@class='menulink']");
		By SecondLvlMenu = By.linkText("COURSES");

		ElementUtils eUtil = new ElementUtils(driver);
		eUtil.twoLvlHover(FirstLvlMenu, SecondLvlMenu);
		eUtil.twoLvlHover(FirstLvlMenu, "ARTICLES");
	}
}
