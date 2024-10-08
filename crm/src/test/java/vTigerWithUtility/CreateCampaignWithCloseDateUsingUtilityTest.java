package vTigerWithUtility;
//Pass
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
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

public class CreateCampaignWithCloseDateUsingUtilityTest {
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		
		WebDriver driver= null;
		PropertyFileUtility putil=new PropertyFileUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		JavaUtility jutil=new JavaUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		//Read the common data from properties file
		
		String BROWSER = putil.readDataFromProperties("Browser");
		String URL = putil.readDataFromProperties("Url");
		String UNAME = putil.readDataFromProperties("Username");
		String PWD = putil.readDataFromProperties("Password");
		
		//Generate Random Number
		int r= jutil.getRandomNumber();
		
		//Read the Test script data from excel file
		String campName =eutil.readDataFromExcel("Campaign", 1, 2)+r;
			
		//Cross Browser Testing / launch empty browser
		if(BROWSER.equalsIgnoreCase("chrome")) {
			driver= new ChromeDriver();
		}else if(BROWSER.equalsIgnoreCase("firefox")) {
			driver= new FirefoxDriver();
		}else if(BROWSER.equalsIgnoreCase("edge")) {
			driver= new EdgeDriver();
		}else {
			driver= new ChromeDriver();
		}
		
		//Enter the URL
		driver.get(URL);
		driver.manage().window().maximize();
		wutil.waitForPageToLoad(driver);

		//Login to application
		driver.findElement(By.name("user_name")).sendKeys(UNAME);
		driver.findElement(By.name("user_password")).sendKeys(PWD);
		driver.findElement(By.id("submitButton")).click();
		
		//Navigate to more option
		driver.findElement(By.linkText("More")).click();
		
		//Click on "Campaign"
		driver.findElement(By.name("Campaigns")).click();
		
		//Click on the "Create new campaign" look up image
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();
		
		//Enter the mandatory details

		driver.findElement(By.name("campaignname")).sendKeys(campName);
		String closeDate =jutil.getRequiredDateyyyyMMdd(5);
	    driver.findElement(By.name("closingdate")).clear();
	    driver.findElement(By.name("closingdate")).sendKeys(closeDate);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//Verification
		String headerInfo2 = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(headerInfo2.contains(campName)) {
			System.out.println(campName+" created successfully");
		}else {
			System.out.println("Failed to create "+campName);
		}
		
		//Logout
		WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		wutil.moveToElement(driver, administrator);
		driver.findElement(By.linkText("Sign Out")).click();
		
		//close the browser
		driver.quit();
	}	
}
