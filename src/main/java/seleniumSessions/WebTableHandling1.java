package seleniumSessions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTableHandling1
{
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException
	{
		driver = new ChromeDriver();
		driver.get("https://classic.crmpro.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.findElement(By.name("username")).sendKeys("newautomation");
		driver.findElement(By.name("password")).sendKeys("Selenium@12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Thread.sleep(2000);
		driver.switchTo().frame("mainpanel");
		driver.findElement(By.linkText("CONTACTS")).click();
		Thread.sleep(2000);
		
//		selectContact("deepti gupta");
//		selectSameContacts("David Cris");
//		String a = getContactCompanyName("David Cris");
//		System.out.println(a);
		
//		System.out.println(getContactPhone("Abel Kainan"));
//		System.out.println(getContactPhone("David Cris"));
		
		List<String> contactData = getContactInfo("Kevin Froster");
		System.out.println(contactData);
	}
	
	public static void selectContact (String contactName)
	{
		driver.findElement(By.xpath("//a[text()='"+contactName+"']/parent::td/preceding-sibling::td/input[@type='checkbox']")).click();
	}
	
	public static void selectSameContacts (String contactName)
	{
		List<WebElement> contactNames = driver.findElements(By.xpath("//a[text()='"+contactName+"']/parent::td/preceding-sibling::td/input[@type='checkbox']"));
		for(WebElement e: contactNames)
		{
			e.click();
		}
	}
	
	public static String getContactCompanyName(String contactName)
	{
		return driver.findElement(By.xpath("//a[text()='"+contactName+"']/parent::td/following-sibling::td/a[@context='company']")).getText();
	}
	
	public static String getContactPhone(String contactName)
	{
//		WebElement contactPhone = driver.findElement(By.xpath("(//a[text()='"+contactName+"']/parent::td//following-sibling::td/span[@context='phone'])[1]"));
		WebElement contactPhone = driver.findElement(By.xpath("//a[text()='"+contactName+"']/parent::td/following-sibling::td[2]"));
		if(contactPhone.getText().isBlank())
		{
			System.out.println("Phone number not available for " + contactName);
		}
		return contactPhone.getText();
	}
	
	public static List<String> getContactInfo(String contactName)
	{
		List<WebElement> contactInfo = driver.findElements(By.xpath("//a[text()='"+contactName+"']/parent::td/following-sibling::td//following-sibling::td"));
		List<String> contactInfoList = new ArrayList<String>();
		for(int i=0; i<contactInfo.size()-1; i++)
		{
			String output = contactInfo.get(i).getText();
			if(contactInfo.get(i).getText().isBlank())
			{
//				System.out.println("Info not available for " + contactName);
				contactInfoList.add(output);
			}
//			System.out.println(output);
			contactInfoList.add(output);
		}
		return contactInfoList;
	}
}
