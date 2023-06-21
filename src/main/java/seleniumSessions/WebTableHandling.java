package seleniumSessions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTableHandling
{
	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException
	{
		driver = new ChromeDriver();
		driver.get("https://selectorshub.com/xpath-practice-page/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		
		selectUserFromTable("Jasmine.Morgan");
		selectUserFromTable("Joe.Root");
		
		List<String> userInfo = getUserInfo("Joe.Root");
		System.out.println(userInfo);
	}
	
	public static void selectUserFromTable(String userName)
	{
		driver.findElement(By.xpath("//a[text()='"+userName+"']/parent::td//preceding-sibling::td/input")).click();
	}
	
	public static List<String> getUserInfo(String userName)
	{
		List<WebElement> userInfoList = driver.findElements(By.xpath("//a[text()='"+userName+"']/parent::td//following-sibling::td"));
		List<String> userList = new ArrayList<String>();
		for(WebElement e: userInfoList)
		{
			String text = e.getText();
			userList.add(text);
		}
		return userList;
	}
}
