package practice;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class ReadDataFromJsonFileTest {

	public static void main(String[] args) throws InterruptedException, IOException, ParseException {
		//Read common data from JSON file
		
		//Step1:Create the object of JSONParser class
		JSONParser parser=new JSONParser();
		
		//Step2:  Parse the physical file into Java Object
		Object obj = parser.parse(new FileReader("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\appCommonData.json"));
		
		//Step3: Convert the java object into JSON Object (using Typecasting)
		JSONObject map=(JSONObject)obj;
		
		//Step4: Capture the data/ values using get() by passing “key”
		String BROWSER = map.get("Browser").toString();
		System.out.println(BROWSER);
		String UNAME = map.get("Username").toString();
		System.out.println(UNAME);
		String PWD = map.get("Password").toString();
		System.out.println(PWD);
		String URL = map.get("Url").toString();
		System.out.println(URL);
		
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
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	//Step2: Navigate to URL and enter credentials
	driver.get(URL);
	driver.findElement(By.name("user_name")).sendKeys(UNAME);
	driver.findElement(By.name("user_password")).sendKeys(PWD);
	driver.findElement(By.id("submitButton")).click();

	//Step3:Navigate to Organisation module and click on "+" lookup image 
	driver.findElement(By.linkText("Organizations")).click();
	driver.findElement(By.xpath("//img[contains(@title,'Create Organization')]")).click();

	//Step4: Enter the mandatory details and save the org
	driver.findElement(By.name("accountname")).sendKeys(orgName);
	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

	//Step5: Validation
	String headerText = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
	if(headerText.contains(orgName)) {
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
	Thread.sleep(5000);
	driver.quit();


	}

}
