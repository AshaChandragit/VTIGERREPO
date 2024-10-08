package practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
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

public class ToCreateOrganizationUsingExcelTest {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		//Read test script from Excel file
		
		//Step1: Create the object of FIS
		FileInputStream fis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\testScriptData.xlsx");
		
		//Step2: Open the workbook in read mode
		Workbook wb = WorkbookFactory.create(fis);
		
		//Step3: Get the control of required sheet
		 Sheet sh = wb.getSheet("Org");
		//get the req row
		Row r = sh.getRow(1);
		//get the req cell
		Cell c = r.getCell(2);
		String orgName = c.getStringCellValue();
		System.out.println(orgName);
		//Step4: close the workbook
		wb.close();
		
		//Read the data from Properties File
		FileInputStream pfis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
		Properties prop=new Properties();
		prop.load(pfis);
		String BROWSER=prop.getProperty("Browser");
		String URL=prop.getProperty("Url");
		String UNAME=prop.getProperty("Username");
		String PWD=prop.getProperty("Password");
		
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
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(URL);
		driver.findElement(By.name("user_name")).sendKeys(UNAME);
		driver.findElement(By.name("user_password")).sendKeys(PWD);
		driver.findElement(By.id("submitButton")).click();
	
	//Navigate to Org Module
		driver.findElement(By.linkText("Organizations")).click();
		//click on "create new organisation" lookup image
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		//create org by entering the mandatory details
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//Verification
		String header = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(header.contains(orgName)) {
			System.out.println(orgName+" Created Successfully");
		}else {
			System.out.println("failed to create "+orgName);
		}
		
		
		//SignOut
		WebElement administartor = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions actions=new Actions(driver);
		actions.moveToElement(administartor).click().perform();
		driver.findElement(By.linkText("Sign Out")).click();
		driver.quit();
	}

}
