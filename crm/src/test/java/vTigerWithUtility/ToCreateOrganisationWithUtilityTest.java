package vTigerWithUtility;
//Pass
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class ToCreateOrganisationWithUtilityTest {
	public static void main(String[] args) throws InterruptedException, EncryptedDocumentException, IOException {
		//Read common data from Properties file
		PropertyFileUtility putil=new PropertyFileUtility();
		JavaUtility jutil=new JavaUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		String BROWSER=putil.readDataFromProperties("Browser");
		String URL=putil.readDataFromProperties("Url");
		String UNAME=putil.readDataFromProperties("Username");
		String PWD=putil.readDataFromProperties("Password");
		
		//Generate the Random number
		int r=jutil.getRandomNumber();
		
		//Read test script data from excel file
		String orgName=eutil.readDataFromExcel("Org", 1, 2)+r;
				
		//Automation Scripts
		//Step1: Launching the browser
		WebDriver driver=null;
		if(BROWSER.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
		}else if(BROWSER.equalsIgnoreCase("firefox")) {
			driver=new FirefoxDriver();
		
	}else 	if(BROWSER.equalsIgnoreCase("Edge")) {
		driver=new EdgeDriver();
	}else {
		driver=new ChromeDriver();
	}
	driver.manage().window().maximize();
	wutil.waitForPageToLoad(driver);
	
	//Step2: Navigate to URL and enter credentials
	driver.get(URL);
	//Login to the application
	driver.findElement(By.name("user_name")).sendKeys(UNAME);
	driver.findElement(By.name("user_password")).sendKeys(PWD);
	driver.findElement(By.id("submitButton")).click();

	//Step3:Navigate to Organisation module and click on "+" lookup image 
	driver.findElement(By.linkText("Organizations")).click();
	driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

	//Step4: Enter the mandatory details and save the org
	driver.findElement(By.name("accountname")).sendKeys(orgName);
	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

	//Step5: Validation/ Verification
	String headerText = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
	if(headerText.contains(orgName) ){
		System.out.println(orgName+" created successfully");
	}
	else {
		System.out.println("failed to create "+orgName);
	}
	//Step6: Logout
	WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
	wutil.moveToElement(driver, administrator);
	driver.findElement(By.linkText("Sign Out")).click();

	//Step7: close the browser
	Thread.sleep(2000);
	driver.quit();
	}
}
