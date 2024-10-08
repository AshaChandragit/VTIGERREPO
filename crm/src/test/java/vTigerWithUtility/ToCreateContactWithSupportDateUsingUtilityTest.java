package vTigerWithUtility;
//pass
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

public class ToCreateContactWithSupportDateUsingUtilityTest {
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
		String lastname = eutil.readDataFromExcel("Contact", 1, 2)+r;

			
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
		driver.findElement(By.name("user_name")).sendKeys(UNAME);
		driver.findElement(By.name("user_password")).sendKeys(PWD);
		driver.findElement(By.id("submitButton")).click();
		
		//Navigate to contacts module
		driver.findElement(By.linkText("Contacts")).click();
		//Click on "create contact" lookup image
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		
		//create contact with mandatory fields
		driver.findElement(By.name("lastname")).sendKeys(lastname);
		
		//Add the support date
		String startDate=jutil.getSystemDateyyyyMMdd();
		String endDate=jutil.getRequiredDateyyyyMMdd(30);
					
		driver.findElement(By.name("support_start_date")).clear();
		driver.findElement(By.name("support_start_date")).sendKeys(startDate);
		driver.findElement(By.name("support_end_date")).clear();
		driver.findElement(By.name("support_end_date")).sendKeys(endDate);
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//Verification
				String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
				if(headerInfo.contains(lastname)) {
					System.out.println(lastname+" created successfully");
				}else {
					System.out.println("Failed to create "+lastname);
				}
							
				//Logout
				WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
				wutil.moveToElement(driver, administrator);
				driver.findElement(By.linkText("Sign Out")).click();
				
				//close the browser
				driver.quit();
			}
}
