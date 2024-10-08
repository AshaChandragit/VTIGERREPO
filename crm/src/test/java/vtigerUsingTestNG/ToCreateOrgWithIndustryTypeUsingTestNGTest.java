package vtigerUsingTestNG;
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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.CreateNewOrganisationPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;
import objectRepository.OrganisationInfoPage;
import objectRepository.OrganisationPage;

public class ToCreateOrgWithIndustryTypeUsingTestNGTest extends BaseClass{
@Test
	public void createOrgWithIndustryTypeTest() throws EncryptedDocumentException, IOException {
		JavaUtility jutil=new JavaUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		//Generate the Random number
		int r=jutil.getRandomNumber();
		
		//Read Test script data from excel file
			String orgName = eutil.readDataFromExcel("Org", 1, 2)+r;
			String industry = eutil.readDataFromExcel("Org", 1, 5);
			String type = eutil.readDataFromExcel("Org", 1, 6);
		
		wutil.waitForPageToLoad(driver);
				
		//Navigate to Organisation Module
		HomePage hp=new HomePage(driver);
		hp.getOrgLink().click();
		
		//Click on "Create Org" lookup image
		OrganisationPage op=new OrganisationPage(driver);
		op.getCreateOrg().click();
		
		//Create org with mandatory details
		CreateNewOrganisationPage cno=new CreateNewOrganisationPage(driver);
		cno.createOrg(orgName, industry, type);
		
		//Verification
		OrganisationInfoPage oip=new OrganisationInfoPage(driver);
		String header =oip.getHeaderText().getText();
		boolean actStatus = header.contains(orgName);
		
		//Hard Assert
		Assert.assertEquals(actStatus, true);
		Reporter.log(orgName+" created successfully", true);
		
		//OR
		/*//Soft Assert
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(actStatus, true);//or
		soft.assertEquals(actStatus, true, "Failed");// "Failed" will be printed if actStatus and expected status doesn't match
		Reporter.log(orgName+" created successfully", true);
		soft.assertAll();*/		
		
				//Verifying Industry and Type
				String actIndustry = oip.getIndustryHeader().getText();
				Assert.assertEquals(actIndustry, industry);
				Reporter.log(industry+" info is verified successfully", true);
				
				String actType = oip.getTypeHeader().getText();
				Assert.assertEquals(actType, type);
				Reporter.log(type+" info is verified successfully", true);
				

		}
}
