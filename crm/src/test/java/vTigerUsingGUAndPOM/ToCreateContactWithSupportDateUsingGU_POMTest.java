package vTigerUsingGUAndPOM;
//Pass
import java.io.IOException;

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
import objectRepository.HomePage;
import objectRepository.LoginPage;

public class ToCreateContactWithSupportDateUsingGU_POMTest {
	public static void main(String[] args) throws EncryptedDocumentException,IOException{
		//Read the common data from properties file
		PropertyFileUtility putil=new PropertyFileUtility();
		JavaUtility jutil=new JavaUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		String BROWSER=putil.readDataFromProperties("Browser");
		String URL=putil.readDataFromProperties("Url");
		String UNAME=putil.readDataFromProperties("Username");
		String PWD=putil.readDataFromProperties("Password");
		
		//Generate Random number
		int r=jutil.getRandomNumber();
		
		//Read the Test Script data from excel file
		String lastName = eutil.readDataFromExcel("Contact", 1, 2)+r;
			
		//Cross Browser Testing/ launch empty browser
		WebDriver driver=null;
		if (BROWSER.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
		}else if (BROWSER.equalsIgnoreCase("Firefox")) {
			driver=new FirefoxDriver();
		}else if (BROWSER.equalsIgnoreCase("Edge")) {
			driver=new EdgeDriver();
		}else {
			driver=new ChromeDriver();
		}
		
		//Enter the Url
		driver.get(URL);
		driver.manage().window().maximize();
		wutil.waitForPageToLoad(driver);
		
		//Login to the application
		LoginPage lp=new LoginPage(driver);
		lp.login(UNAME, PWD);
			
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
				if(headerInfo.contains(lastName)) {
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
