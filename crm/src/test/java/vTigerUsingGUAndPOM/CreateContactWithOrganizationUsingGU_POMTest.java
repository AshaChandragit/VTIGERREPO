package vTigerUsingGUAndPOM;
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

public class CreateContactWithOrganizationUsingGU_POMTest {
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
				LoginPage lp=new LoginPage(driver);
				lp.login(UNAME, PWD);
				
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
				if(headerInfo.contains(orgName)) {
					System.out.println(orgName+" created successfully");
				}else {
					System.out.println("Failed to create "+orgName);
				}
				
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
				if(headerInfo2.contains(lastName)) {
					System.out.println(lastName+" created successfully");
				}else {
					System.out.println("Failed to create "+lastName);
				}
				//Logout
				hp.logout();
				//close the browser
				driver.quit();
	}
}
