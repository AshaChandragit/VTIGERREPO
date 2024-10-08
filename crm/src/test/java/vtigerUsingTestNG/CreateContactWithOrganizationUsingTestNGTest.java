package vtigerUsingTestNG;
//Pass
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

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
import objectRepository.CreateNewOrganisationPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;
import objectRepository.OrganisationInfoPage;
import objectRepository.OrganisationPage;

public class CreateContactWithOrganizationUsingTestNGTest extends BaseClass{
	@Test
	public void createContactWithOrganizationTest() throws EncryptedDocumentException, IOException {
		//Read the common data from properties file
		ExcelFileUtility eutil=new ExcelFileUtility();
		JavaUtility jutil=new JavaUtility();
		WebDriverUtility wutil=new WebDriverUtility();
	
				//Generate Random Number
				int r=jutil.getRandomNumber();
				
				//Read the Test script data from excel file
				String lastName = eutil.readDataFromExcel("Contact", 1, 2)+r;
				String orgName =eutil.readDataFromExcel("Contact", 1, 3)+r;
				
				wutil.waitForPageToLoad(driver);
				
				//Navigate to organization module
				HomePage hp=new HomePage(driver);
				hp.getOrgLink().click();

				//Click on "Create Org" lookup image
				OrganisationPage op=new OrganisationPage(driver);
				op.getCreateOrg().click();
				
				//Create org with mandatory details
				CreateNewOrganisationPage nop=new CreateNewOrganisationPage(driver);
				nop.createOrg(orgName);
				
				//Verification
				OrganisationInfoPage oip=new OrganisationInfoPage(driver);
				String headerInfo =oip.getHeaderText().getText();
				boolean actStatus = headerInfo.contains(orgName);
				
				//Hard Assert
				Assert.assertEquals(actStatus, true);
				Reporter.log(orgName+" created successfully", true);
				
				
				//Navigate to Contact module
				hp.getContactLink().click();
				
				//Click on "Create contact" lookup image
				ContactsPage cp=new ContactsPage(driver);
				cp.getCreateContactImage().click();
				
				//Create Contact with mandatory details
				CreateNewContactsPage ncp=new CreateNewContactsPage(driver);
				ncp.getLastName().sendKeys(lastName);
				ncp.addContactWithOrg(orgName);
				ncp.getSaveBtn().click();
				
				//Verification
				ContactInfoPage cip=new ContactInfoPage(driver);
				String headerInfo2 = cip.getHeaderMsg().getText();
				boolean contactStatus = headerInfo2.contains(lastName);		
				Assert.assertEquals(contactStatus, true, "Failed to create "+lastName);
				Reporter.log(lastName+" created successfully", true);
				
		}
}
