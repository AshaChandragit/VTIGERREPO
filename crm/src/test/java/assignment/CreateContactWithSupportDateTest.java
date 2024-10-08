package assignment;
//prg done by madam
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.text.SimpleDateFormat;
	import java.time.Duration;
	import java.util.Calendar;
	import java.util.Date;
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

	public class CreateContactWithSupportDateTest {

		public static void main(String[] args) throws EncryptedDocumentException,IOException {
			//Read the common data from properties file
			FileInputStream pfis= new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
			Properties prop= new Properties();
			prop.load(pfis);
			String BROWSER = prop.getProperty("Browser");
			String URL = prop.getProperty("Url");
			String UNAME = prop.getProperty("Username");
			String PWD = prop.getProperty("Password");
			
			//Generate Random Number
			Random ran= new Random();
			int r= ran.nextInt(1000);
			
			//Read the Test script data from excel file
			FileInputStream efis= new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\testScriptData.xlsx");
			Workbook wb = WorkbookFactory.create(efis);
			Sheet sh = wb.getSheet("Contact");
			Row row = sh.getRow(1);
			String lastName = row.getCell(2).getStringCellValue()+r;
			
			wb.close();
			
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
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			
			//Login to application
			driver.findElement(By.name("user_name")).sendKeys(UNAME);
			driver.findElement(By.name("user_password")).sendKeys(PWD);
			driver.findElement(By.id("submitButton")).click();
			
			//Navigate to Contact module
			driver.findElement(By.linkText("Contacts")).click();
			
			//Click on "Create contact" lookup image
			driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
			
			//Create Contact with mandatory details
			driver.findElement(By.name("lastname")).sendKeys(lastName);
			
			 //Add the support date
			Date dateObj= new Date();
			SimpleDateFormat sim= new SimpleDateFormat("yyyy-MM-dd");
			String startDate = sim.format(dateObj);
			
			Calendar cal= sim.getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, 30);
			String endDate=sim.format(cal.getTime());
			
			driver.findElement(By.name("support_start_date")).clear();
			driver.findElement(By.name("support_start_date")).sendKeys(startDate);
			
			driver.findElement(By.name("support_end_date")).clear();
			driver.findElement(By.name("support_end_date")).sendKeys(endDate);
			
			driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
			
			//Verification
			String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
			if(headerInfo.contains(lastName)) {
				System.out.println(lastName+" created successfully");
			}else {
				System.out.println("Failed to create "+lastName);
			}
			
			
			//Logout
			WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
			
			Actions act= new Actions(driver);
			act.moveToElement(administrator).click().perform();
			driver.findElement(By.linkText("Sign Out")).click();
			
			//close the browser
			driver.quit();
			

		}

}
