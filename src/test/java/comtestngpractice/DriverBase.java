package comtestngpractice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverBase {
	
	WebDriver driver;
	
	public WebDriver selectBrowser(String browserName)
	{
		if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().version("0.24.0").setup();
			driver = new FirefoxDriver();
		}else if(browserName.equalsIgnoreCase("chrome")){
			WebDriverManager.chromedriver().version("2.40").setup();
			driver = new ChromeDriver();
		}
		return driver;
	}

}
