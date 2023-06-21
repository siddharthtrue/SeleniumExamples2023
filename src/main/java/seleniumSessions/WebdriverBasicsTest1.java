package seleniumSessions;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;

public class WebdriverBasicsTest1
{
	public static void main(String[] args)
	{
//		ChromeOptions co = new ChromeOptions();
//		co.addArguments("--remote-allow-origins=*");
//		WebDriver driver = new ChromeDriver(co);
//		driver.get("https://www.google.com");
//		String title = driver.getTitle();
//		System.out.println(title);

// Below code is developed using utilities defined in other class.
		BrowserUtils bu = new BrowserUtils();
		bu.initDriver("chrome");
		bu.launchURL("https://www.amazon.com");
		System.out.println("Page Title: " + bu.getPageTitle());
		
		VerifyText.equalValues(bu.getPageTitle(), "Google");
		VerifyText.containValues(bu.getPageURL(), "amazon");
		
		bu.quitBrowser();
	}
}
