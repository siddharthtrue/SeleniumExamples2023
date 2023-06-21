package seleniumSessions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PseudoElementsHandling
{
	static WebDriver driver;
	public static void main(String[] args)
	{
		driver = new ChromeDriver();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
		
		String firstName = "input-firstname";
		String lastName = "input-lastname";
		
		pseudoElementHandle(firstName);
		pseudoElementHandle(lastName);
	}
	
	public static void pseudoElementHandle(String element)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
// window.getComputedStyle(document.querySelector("label[for='input-firstname']"),'::before').getPropertyValue('content')
		String man_field_text = js.executeScript("return window.getComputedStyle(document.querySelector(\"label[for='"+element+"']\"),'::before').getPropertyValue('content')").toString();
		System.out.println(man_field_text);		

		if(man_field_text.contains("*"))
		{
			System.out.println(element + " is a mandatory field");
		}
	}	
}
