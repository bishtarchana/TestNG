package comtestngpractice;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.google.common.base.Function;


public class TestNGExample {
	WebDriver driver;
	SoftAssert assertion = new SoftAssert();
	WebElement demo;
	WebDriverWait wait;
	Wait<WebDriver> waitForElement;
			
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
		wait = new WebDriverWait(driver, 40);
		waitForElement = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(5))
					.ignoring(NoSuchElementException.class);
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
		verifyLoginValidation();
		driver.findElement(By.cssSelector("input[name='username']")).sendKeys("user@phptravels.com");
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys("demouser");
		
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[contains(text(),'Login')]"))));
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);",element);
		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Login')]"))));
		loginButton.click();
		
		WebElement userText = waitForElement.until(new Function<WebDriver, WebElement>(){
			public WebElement apply(WebDriver driver){
				WebElement homePage = driver.findElement(By.xpath("//h3[contains(text(),'Hi, Demo User')]"));
				return homePage;
			}
		});
		assertion.assertTrue((userText).isDisplayed());
		System.out.println("User has successfully logged into the application");
	}
	
	
	public void verifyLoginValidation()
	{	
		try {
			WebElement login = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Login')]"))));
			login.click();
			
			// verify login page error using getText() method
			String errorText = driver.findElement(By.cssSelector("div.resultlogin div")).getText();
			assertion.assertTrue(errorText.contains("Invalid Email or Password"));

			//verify login page error using getAttribute("innerHTML")

			String actualErrorMessage = driver.findElement(By.cssSelector("div.resultlogin div")).getAttribute("innerHTML");
			String expectedErrorMessage = "Invalid Email or Password";
			assertion.assertEquals(actualErrorMessage, expectedErrorMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(dependsOnMethods="loginIntoApplication")
	public void logOut()
	{
		try {
			WebElement demoText = waitForElement.until(new Function<WebDriver, WebElement>(){
				public WebElement apply(WebDriver driver){
					WebElement demo = driver.findElement(By.xpath("//nav//a[contains(text(),' Demo ')]"));
					return demo;
				}
			});
			demoText.click();
			driver.findElement(By.xpath("//nav//a[contains(text(),'Logout')]")).click();
			assertion.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Login')]")).isDisplayed());
			System.out.println("User has been logged out from the application");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void quitBrowser()
	{
		driver.quit();
	}

}
