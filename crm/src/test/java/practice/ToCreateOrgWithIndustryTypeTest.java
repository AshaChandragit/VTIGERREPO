package practice;

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

public class ToCreateOrgWithIndustryTypeTest {
public static void main(String[] args) throws EncryptedDocumentException, IOException {
	
			
	//Read the data from Properties File
	FileInputStream pfis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
	Properties prop=new Properties();
	prop.load(pfis);
	String BROWSER=prop.getProperty("Browser");
	String URL=prop.getProperty("Url");
	String UNAME=prop.getProperty("Username");
	String PWD=prop.getProperty("Password");
	
	//Generate the Random number
	Random ran=new Random();
	int r = ran.nextInt(1000);
	
	//Read Test script data from excel file
	
		//create object of FIS 
		FileInputStream efis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\testScriptData.xlsx");
		
		//open the workbook in read mode
		Workbook wb = WorkbookFactory.create(efis);
		
		Sheet sh = wb.getSheet("Org");
		Row row = sh.getRow(1);
		//Cell c = row.getCell(2);
		String orgName = row.getCell(2).getStringCellValue()+r;
		System.out.println(orgName);
		String industry = row.getCell(5).getStringCellValue();
		String type = row.getCell(6).getStringCellValue();
		wb.close();
		
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
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	driver.get(URL);
	
	//login to application
	driver.findElement(By.name("user_name")).sendKeys(UNAME);
	driver.findElement(By.name("user_password")).sendKeys(PWD);
	driver.findElement(By.id("submitButton")).click();
	
			
			//Navigate to Organisation Module
			driver.findElement(By.linkText("Organizations")).click();
			//click on "create new organisation" lookup image
			driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
			//create org by entering the mandatory details
			driver.findElement(By.name("accountname")).sendKeys(orgName);

			WebElement indutryDD = driver.findElement(By.name("industry"));
			Select sel=new Select(indutryDD);
			sel.selectByValue(industry);// fetching from excel
			//sel.selectByValue("Education");// hardcode
			
			WebElement typeDD = driver.findElement(By.name("accounttype"));
			Select sel1=new Select(typeDD);
			sel1.selectByValue(type);
			sel1.selectByValue("Other");
			
			driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
	
			//Verification
			String header = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
			if(header.contains(orgName)) {
				System.out.println(orgName+" Created Successfully");
			}else {
				System.out.println("failed to create "+orgName);
			}
			//Verifying Industry and Type
			String actIndustry = driver.findElement(By.id("dtlview_Industry")).getText();
			if(actIndustry.equals(industry)) {
				System.out.println(industry+" info is verified successfully");
			}else {
				System.out.println(industry+" info failed to verify");
			}
			String actType = driver.findElement(By.id("dtlview_Type")).getText();
			if(actType.equals(type)) {
				System.out.println(type+" info is verified successfully");
			}else {
				System.out.println(type+" info failed to verify");
			}

			//SignOut
			WebElement administartor = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
			Actions actions=new Actions(driver);
			actions.moveToElement(administartor).click().perform();
			driver.findElement(By.linkText("Sign Out")).click();
			
			//close the browser
			driver.quit();
}
}
