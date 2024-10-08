package vTigerUsingGUAndPOM;
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
import objectRepository.CampaignInfoPage;
import objectRepository.CampaignPage;
import objectRepository.CreateCampaignPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;

public class CreateCampaignWithCloseDateUsingGU_POMTest {
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
		LoginPage lp=new LoginPage(driver);
		lp.login(UNAME, PWD);
		
		//Navigate to more option and Click on "Campaign"
		HomePage hp=new HomePage(driver);
		hp.navigateToCampaign();
		
		//Click on the "Create new campaign" look up image
		CampaignPage cp=new CampaignPage(driver);
		cp.getCreateCampaign().click();
		
		//Enter the mandatory details

		CreateCampaignPage ccp=new CreateCampaignPage(driver);
		ccp.getCampName().sendKeys(campName);
		String closeDate =jutil.getRequiredDateyyyyMMdd(5);
	    ccp.getCloseDate().clear();
	    ccp.getCloseDate().sendKeys(closeDate);
		ccp.getSaveBtn().click();
		
		//Verification
		CampaignInfoPage cip=new CampaignInfoPage(driver);
		String headerInfo2 = cip.getHeaderInfo().getText();
		if(headerInfo2.contains(campName)) {
			System.out.println(campName+" created successfully");
		}else {
			System.out.println("Failed to create "+campName);
		}
		
		//Logout
		hp.logout();
		
		//close the browser
		driver.quit();
	}	
}
