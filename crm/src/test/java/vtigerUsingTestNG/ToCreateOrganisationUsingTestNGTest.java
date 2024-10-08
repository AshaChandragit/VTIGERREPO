package vtigerUsingTestNG;
//Pass
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
@Listeners(listenersUtility.ListenerImplementationClass.class)
public class ToCreateOrganisationUsingTestNGTest extends BaseClass{
	@Test
	public void createOrganisationTest() throws EncryptedDocumentException, IOException {
		JavaUtility jutil=new JavaUtility();
		ExcelFileUtility eutil=new ExcelFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		//Generate the Random number
		int r=jutil.getRandomNumber();
		
		//Read test script data from excel file
		String orgName=eutil.readDataFromExcel("Org", 1, 2)+r;
				
		wutil.waitForPageToLoad(driver);
	
		//Step3:Navigate to Organisation module and click on "+" lookup image 
		HomePage hp=new HomePage(driver);
		hp.getOrgLink().click();
		
		//Click on "Create Org" lookup image
		OrganisationPage op=new OrganisationPage(driver);
		op.getCreateOrg().click();
	
		//Step4: Enter the mandatory details and save the org
		CreateNewOrganisationPage cno=new CreateNewOrganisationPage(driver);
		cno.createOrg(orgName);
	
	//Step5: Validation/ Verification
	OrganisationInfoPage oip=new OrganisationInfoPage(driver);
	String headerText =oip.getHeaderText().getText();
	boolean actStatus = headerText.contains(orgName);
	
	//Hard Assert
	Assert.assertEquals(actStatus, true, "Failed ");
	Reporter.log(orgName+" created successfully", true);
	
	//OR
	/*//Soft Assert
	SoftAssert soft=new SoftAssert();
	soft.assertEquals(actStatus, true);//or
	soft.assertEquals(actStatus, true, "Failed");// "Failed" will be printed if actStatus and expected status doesn't match
	Reporter.log(orgName+" created successfully", true);
	soft.assertAll();*/
	
	}
}
