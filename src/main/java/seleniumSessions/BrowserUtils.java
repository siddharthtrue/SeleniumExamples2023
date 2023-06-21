package seleniumSessions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Date: March 25, 2023 
 * @author Siddharth
 *
 */

public class BrowserUtils
{
	WebDriver driver;
	
	public WebDriver initDriver(String browserName)
	{
		System.out.println("Launching " + browserName + " browser....");
		
		if(browserName == null)
		{
			System.out.println("Browser name cannot be null");
			throw new MyException("BROWSERNULLEXCEPTION");
		}
		
		switch (browserName.toLowerCase().trim())
		{
			case "chrome": 
//				ChromeOptions co = new ChromeOptions();
//				co.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver();
				break;
			
			case "firefox": 
				driver = new FirefoxDriver();
				break;
			
			default: 
				System.out.println("Please pass the correct browser");
				throw new MyException("WRONGBROWSEREXCEPTION");
		}
		return driver;
	}
	
	public void launchURL(String url)
	{
		if(url == null)
		{
			System.out.println("URL can not be null");
			throw new MyException("NULLURLEXCEPTION");
		}
		else if (url.contains("http"))
		{
			driver.get(url);
		}
		else
		{
			System.out.println("http(s) is missing");
		}
	}
	
	public String getPageTitle()
	{
		return driver.getTitle();
	}
	
	public String getPageURL()
	{
		return driver.getCurrentUrl();
	}
	
	public void closeBrowser()
	{
		driver.close();
	}
	
	public void quitBrowser()
	{
		driver.quit();
	}
}
