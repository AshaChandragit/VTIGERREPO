package vtigerUsingTestNG;
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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.CampaignInfoPage;
import objectRepository.CampaignPage;
import objectRepository.CreateCampaignPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;

public class CreateCampaignWithCloseDateUsingTestNGTest extends BaseClass{
@Test
	public void createCampaignTest() throws EncryptedDocumentException, IOException {
		ExcelFileUtility eutil=new ExcelFileUtility();
		JavaUtility jutil=new JavaUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		//Generate Random Number
		int r= jutil.getRandomNumber();
		
		//Read the Test script data from excel file
		String campName =eutil.readDataFromExcel("Campaign", 1, 2)+r;
		
		//Enter the URL
		wutil.waitForPageToLoad(driver);
		
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
		boolean actStatus = headerInfo2.contains(campName);
		Assert.assertEquals(actStatus, true, "Failed to create "+campName);
		Reporter.log(campName+" created successfully", true);
		
	}	
}
