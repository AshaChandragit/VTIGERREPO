package practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

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

public class CreateOrgWithRandomNumTest {
public static void main(String[] args) throws InterruptedException, EncryptedDocumentException, IOException {
	//Read common data from Properties file
	FileInputStream pfis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
	Properties prop =new Properties();
	prop.load(pfis);
	String BROWSER = prop.getProperty("Browser");
	String URL = prop.getProperty("Url");
	String UNAME = prop.getProperty("Username");
	String PWD = prop.getProperty("Password");
	
	//Generate the Random number
	Random ran=new Random();
	int r = ran.nextInt(1000);
	
	//Read test script data from excel file
	FileInputStream efis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\testScriptData.xlsx");
	Workbook wb = WorkbookFactory.create(efis);
	Sheet sheet = wb.getSheet("Org");
	Row row = sheet.getRow(1);
	Cell cell = row.getCell(2);
	String orgName = cell.getStringCellValue()+r;//Attaching the random number to Org name
	wb.close();
	
	//Automation Scripts
	//Step1: Launching the browser
	WebDriver driver=null;
	if(BROWSER.equalsIgnoreCase("chrome")) {
		driver=new ChromeDriver();
	}else if(BROWSER.equalsIgnoreCase("firefox")) {
		driver=new FirefoxDriver();
	
}else 	if(BROWSER.equalsIgnoreCase("Edge")) {
	driver=new EdgeDriver();
}else {
	driver=new ChromeDriver();
}
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//Step2: Navigate to URL and enter credentials
driver.get(URL);
//Login to the application
driver.findElement(By.name("user_name")).sendKeys(UNAME);
driver.findElement(By.name("user_password")).sendKeys(PWD);
driver.findElement(By.id("submitButton")).click();

//Step3:Navigate to Organisation module and click on "+" lookup image 
driver.findElement(By.linkText("Organizations")).click();
driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

//Step4: Enter the mandatory details and save the org
driver.findElement(By.name("accountname")).sendKeys(orgName);
driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

//Step5: Validation/ Verification
String headerText = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
if(headerText.contains(orgName) ){
	System.out.println(orgName+" created successfully");
}
else {
	System.out.println("failed to create "+orgName);
}
//Step6: Logout
WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
Actions actions=new Actions(driver);
actions.moveToElement(administrator).click().perform();
driver.findElement(By.linkText("Sign Out")).click();

//Step7: close the browser
Thread.sleep(2000);
driver.quit();
}
}