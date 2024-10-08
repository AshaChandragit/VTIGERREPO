package practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ToLearnDDTUsingPropertiesFile {
	public static void main(String[] args) throws IOException {
	WebDriver driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	
	//write or Insert data into properties file
	//To write the data
	Properties prop1=new Properties();
	prop1.setProperty("Browser", "firefox");
	prop1.setProperty("Url", "http://localhost:8888/");
	prop1.setProperty("Username", "admin");
	prop1.setProperty("Password", "password");
	
	//create the object of FOS
	FileOutputStream fos=new FileOutputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
	
	//store the data
	
	
	//Read Data
	//step1: create object of FIS
	FileInputStream fis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
	
	//step2: Load all the keys
	Properties prop=new Properties();
	prop.load(fis);
	
	//step3: Read the data
	
	String BROWSER=prop.getProperty("Browser");
	System.out.println(BROWSER);
	String UNAME=prop.getProperty("Username");
	System.out.println(UNAME);
	String PWD=prop.getProperty("Password");
	System.out.println(PWD);
	String URL=prop.getProperty("Url");
	System.out.println(URL);
	
	//driver.get("https://localhost:8888/");
	//driver.findElement(By.name("user_name")).sendKeys("admin");
	//driver.findElement(By.name("user_password")).sendKeys("password");
	//driver.findElement(By.id("submitButton")).click();
	
	}
}
