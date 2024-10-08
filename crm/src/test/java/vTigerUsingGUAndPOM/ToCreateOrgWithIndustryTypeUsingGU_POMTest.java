package vTigerUsingGUAndPOM;
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
import objectRepository.CreateNewOrganisationPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;
import objectRepository.OrganisationInfoPage;
import objectRepository.OrganisationPage;

public class ToCreateOrgWithIndustryTypeUsingGU_POMTest {
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
		
		LoginPage lp=new LoginPage(driver);
		lp.login();
		
		//Navigate to Organisation Module
		HomePage hp=new HomePage(driver);
		hp.getOrgLink().click();
				
		//click on "create new organisation" lookup image
		OrganisationPage op=new OrganisationPage(driver);
		op.getCreateOrg().click();
		
		//create org by entering the mandatory details
		CreateNewOrganisationPage cno=new CreateNewOrganisationPage(driver);
		cno.createOrg(orgName, industry, type);
		
		//Verification
		OrganisationInfoPage oip=new OrganisationInfoPage(driver);
		String header =oip.getHeaderText().getText();
			
			if(header.contains(orgName)) {
					System.out.println(orgName+" Created Successfully");
				}else {
					System.out.println("failed to create "+orgName);
				}
				//Verifying Industry and Type
				String actIndustry =oip.getIndustryHeader().getText();
				if(actIndustry.equals(industry)) {
					System.out.println(industry+" info is verified successfully");
				}else {
					System.out.println(industry+" info failed to verify");
				}
				String actType = oip.getTypeHeader().getText();
				if(actType.equals(type)) {
					System.out.println(type+" info is verified successfully");
				}else {
					System.out.println(type+" info failed to verify");
				}

				//SignOut
				hp.logout();
			
				//close the browser
				driver.quit();
	}
}
