package vtigerUsingTestNG;
//Pass
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.ContactInfoPage;
import objectRepository.ContactsPage;
import objectRepository.CreateNewContactsPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;

public class ToCreateContactWithSupportDateUsingTestNGTest extends BaseClass {
	@Test
	public void createContactWithSupportDateTest() throws EncryptedDocumentException, IOException {
		//Read the common data from properties file
		JavaUtility jutil=new JavaUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		//Generate Random number
		int r=jutil.getRandomNumber();
		
		//Read the Test Script data from excel file
		String lastName = eutil.readDataFromExcel("Contact", 1, 2)+r;
			
		wutil.waitForPageToLoad(driver);
		
		//Navigate to contacts module
		HomePage hp=new HomePage(driver);
		hp.getContactLink().click();
		
		//Click on "create contact" lookup image
		ContactsPage cp=new ContactsPage(driver);
		cp.getCreateContactImage().click();;
		
		//create contact with mandatory fields
		CreateNewContactsPage ncp=new CreateNewContactsPage(driver);
		ncp.getLastName().sendKeys(lastName);
		
		//Add the support date
		String startDate=jutil.getSystemDateyyyyMMdd();
		String endDate=jutil.getRequiredDateyyyyMMdd(30);
		ncp.getSupportSystemDate(startDate, endDate);
		ncp.getSaveBtn().click();
		
		//Verification
		ContactInfoPage cip=new ContactInfoPage(driver);
		String headerInfo = cip.getHeaderMsg().getText();
		boolean actStatus = headerInfo.contains(lastName);		
		Assert.assertEquals(actStatus, true, "Failed to create "+lastName);
		Reporter.log(lastName+" created successfully", true);
			
		}
}
