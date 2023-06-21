package seleniumSessions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
// jquery drop down or multi-select drop down

public class LambdaTest4
{
	public static void main(String[] args)
	{
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.lambdatest.com/selenium-playground/select-dropdown-demo");
		
		By week = By.id("select-demo");
		By state = By.id("multi-select");
		By allItems = By.xpath("//select[@id='multi-select']//option");
		
//		WebElement weekele = driver.findElement(week);
//		WebElement stateele = driver.findElement(state);
//		
//		Select sel1 = new Select(weekele);
//		System.out.println(sel1.isMultiple());
//		
//		Select sel2 = new Select(stateele);
//		System.out.println(sel2.isMultiple());
//		
//		sel2.selectByVisibleText("Texas");
//		sel2.selectByVisibleText("Florida");
		
		ElementUtils eUtils = new ElementUtils(driver);
		eUtils.doSelectValueFromMultiSelectDropDown(state, "India"); //Single selection
//		eUtils.doSelectValueFromMultiSelectDropDown(state, "Washington", "Texas", "Florida", "Ohio"); // Multiple selection
//		eUtils.doSelectValueFromMultiSelectDropDown(allItems, "all"); // all selection
	}
}
