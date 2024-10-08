package vTigerWithUtility;
//pass
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

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

public class CreateContactWithOrganizationUsingUtilityTest {
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		//Read the common data from properties file
		PropertyFileUtility putil=new PropertyFileUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		JavaUtility jutil=new JavaUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		String BROWSER=putil.readDataFromProperties("Browser");
		String URL=putil.readDataFromProperties("Url");
		String UNAME=putil.readDataFromProperties("Username");
		String PWD=putil.readDataFromProperties("Password");
		
				//Generate Random Number
				int r=jutil.getRandomNumber();
				
				//Read the Test script data from excel file
				String lastName = eutil.readDataFromExcel("Contact", 1, 2)+r;
				String orgName =eutil.readDataFromExcel("Contact", 1, 3)+r;
								
				//Cross Browser Testing / launch empty browser
				
				WebDriver driver= null;
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
				
				//Navigate to organization module
				driver.findElement(By.linkText("Organizations")).click();
				
				//Click on "Create Org" lookup image
				driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
				
				//Create org with mandatory details
				driver.findElement(By.name("accountname")).sendKeys(orgName);
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
				//Verification
				String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
				if(headerInfo.contains(orgName)) {
					System.out.println(orgName+" created successfully");
				}else {
					System.out.println("Failed to create "+orgName);
				}
				
				//Navigate to Contact module
				driver.findElement(By.linkText("Contacts")).click();
				
				//Click on "Create contact" lookup image
				driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
				
				//Create Contact with mandatory details
				driver.findElement(By.name("lastname")).sendKeys(lastName);
				driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
				
				//Switch to windows
				Set<String> allIds = driver.getWindowHandles();
				Iterator<String> it= allIds.iterator();
				while(it.hasNext()) {
					String windowId = it.next();
					driver.switchTo().window(windowId);
					
					String actUrl = driver.getCurrentUrl();
					if(actUrl.contains("module=Accounts")) {
						break;
					}
				}
				//Switch to windows
				//wutil.toSwitchToWindow(driver, "module=Accounts"); // need to write a method in generic utility for this

				driver.findElement(By.name("search_text")).sendKeys(orgName);
				driver.findElement(By.name("search")).click();
				driver.findElement(By.xpath("//a[text()='"+orgName+"']")).click();
				
				//SwitchTo parent Window
			 
				Iterator<String> it2= allIds.iterator();
				while(it2.hasNext()) {
					String windowId = it2.next();
					driver.switchTo().window(windowId);
				
					String actUrl = driver.getCurrentUrl();
					if(actUrl.contains("module=Contacts")) {
						break;
					}
				}
				//SwitchTo parent Window
			    //wutil.toSwitchToWindow(driver, "module=Contacts");// need to write a method in generic utility for this
			    
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
				//Verification
				String headerInfo2 = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
				if(headerInfo2.contains(lastName)) {
					System.out.println(lastName+" created successfully");
				}else {
					System.out.println("Failed to create "+lastName);
				}
				//Logout
				WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
				wutil.moveToElement(driver, administrator);
				driver.findElement(By.linkText("Sign Out")).click();
				
				//close the browser
				driver.quit();
				


	}
}
