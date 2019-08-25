package comtestngpractice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestNGExample {
	WebDriver driver;
	SoftAssert assertion = new SoftAssert();
	
	@BeforeClass
	@Parameters("browser")
	public void startBrowser(String browserName)
	{
		DriverBase browserType = new DriverBase();
		driver = browserType.selectBrowser(browserName);
		System.out.println("Browser started");
		driver.get("https://www.phptravels.net/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	@Test(priority=1,description="This test will start load the login page of the application")
	public void startApplication()
	{
		String currentUrl = driver.getCurrentUrl();
		assertion.assertTrue(currentUrl.contains("login"));
		System.out.println("Login page has been opened");
	}
	
	@Test(dependsOnMethods="startApplication",description="User will be able to login into the application after providing valid credentails")
	public void loginIntoApplication() throws InterruptedException
	{
		Thread.sleep(1000);;
		verifyLoginValidation();
		driver.findElement(By.cssSelector("input[name='username']")).sendKeys("user@phptravels.com");
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys("demouser");
		Thread.sleep(5000);
		WebElement element = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);",element);
		Thread.sleep(500);
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		Thread.sleep(1000);
		assertion.assertTrue(driver.findElement(By.xpath("//h3[contains(text(),'Hi, Demo User')]")).isDisplayed());
		System.out.println("User has successfully logged into the application");
	}
	
	
	public void verifyLoginValidation()
	{	
		try {
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
			// verify login page error using getText() method
			String errorText = driver.findElement(By.cssSelector("div.resultlogin div")).getText();
			assertion.assertTrue(errorText.contains("Invalid Email or Password"));

			//verify login page error using getAttribute("innerHTML")

			String actualErrorMessage = driver.findElement(By.cssSelector("div.resultlogin div")).getAttribute("innerHTML");
			String expectedErrorMessage = "Invalid Email or Password";
			assertion.assertEquals(actualErrorMessage, expectedErrorMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(dependsOnMethods="loginIntoApplication")
	public void logOut()
	{
		try {
			Thread.sleep(1000);

			driver.findElement(By.xpath("//nav//a[contains(text(),' Demo ')]")).click();
			driver.findElement(By.xpath("//nav//a[contains(text(),'Logout')]")).click();
			assertion.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Login')]")).isDisplayed());
			System.out.println("User has been logged out from the application");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void quitBrowser()
	{
		driver.quit();
	}

}
