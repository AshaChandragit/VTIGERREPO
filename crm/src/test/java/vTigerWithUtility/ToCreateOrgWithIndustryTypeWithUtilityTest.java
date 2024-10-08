package vTigerWithUtility;
//Pass
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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
import org.openqa.selenium.support.ui.Select;

import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class ToCreateOrgWithIndustryTypeWithUtilityTest {
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		PropertyFileUtility putil=new PropertyFileUtility();
		JavaUtility jutil=new JavaUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		//Read the data from Properties File
		String BROWSER=putil.readDataFromProperties("Browser");
		String URL=putil.readDataFromProperties("Url");
		String UNAME=putil.readDataFromProperties("Username");
		String PWD=putil.readDataFromProperties("Password");
		
		//Generate the Random number
		int r=jutil.getRandomNumber();
		
		//Read Test script data from excel file
		
			//create object of FIS 
		//open the workbook in read mode
			
			String orgName = eutil.readDataFromExcel("Org", 1, 2)+r;
			System.out.println(orgName);
			String industry = eutil.readDataFromExcel("Org", 1, 5);
			String type = eutil.readDataFromExcel("Org", 1, 6);
			
		//cross browser testing/ launch empty browser
		WebDriver driver=null;
		if(BROWSER.equalsIgnoreCase("Chrome")) {
			driver=new ChromeDriver();
		}
		else if(BROWSER.equalsIgnoreCase("firefox")) {
			driver=new FirefoxDriver();
		}
		else if(BROWSER.equalsIgnoreCase("edge")) {
			driver=new EdgeDriver();
		}
		else {
			driver=new ChromeDriver();
			
		}
		
		//Enter the URL
		driver.manage().window().maximize();
		wutil.waitForPageToLoad(driver);
		driver.get(URL);
		
		//login to application
		driver.findElement(By.name("user_name")).sendKeys(UNAME);
		driver.findElement(By.name("user_password")).sendKeys(PWD);
		driver.findElement(By.id("submitButton")).click();
		
				
				//Navigate to Organisation Module
				driver.findElement(By.linkText("Organizations")).click();
				//click on "create new organisation" lookup image
				driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
				//create org by entering the mandatory details
				driver.findElement(By.name("accountname")).sendKeys(orgName);

				WebElement industryDD = driver.findElement(By.name("industry"));
				wutil.select( industryDD, industry);
				// fetching from excel
				//sel.selectByValue("Education");// hardcode
				
				WebElement typeDD = driver.findElement(By.name("accounttype"));
				wutil.select(typeDD, type);
				//sel1.selectByValue("Other");
				
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
				//Verification
				String header = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
				if(header.contains(orgName)) {
					System.out.println(orgName+" Created Successfully");
				}else {
					System.out.println("failed to create "+orgName);
				}
				//Verifying Industry and Type
				String actIndustry = driver.findElement(By.id("dtlview_Industry")).getText();
				if(actIndustry.equals(industry)) {
					System.out.println(industry+" info is verified successfully");
				}else {
					System.out.println(industry+" info failed to verify");
				}
				String actType = driver.findElement(By.id("dtlview_Type")).getText();
				if(actType.equals(type)) {
					System.out.println(type+" info is verified successfully");
				}else {
					System.out.println(type+" info failed to verify");
				}

				//SignOut
				WebElement administartor = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
				wutil.moveToElement(driver, administartor);
				driver.findElement(By.linkText("Sign Out")).click();
				
				//close the browser
				driver.quit();
	}
}
