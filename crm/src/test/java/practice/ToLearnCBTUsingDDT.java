package practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ToLearnCBTUsingDDT {
public static void main(String[] args) throws IOException {
	
	//Reading the data
	FileInputStream fis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\commonData.properties");
	Properties prop=new Properties();
	prop.load(fis);
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
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	driver.get(URL);
	driver.findElement(By.name("user_name")).sendKeys(UNAME);
	driver.findElement(By.name("user_password")).sendKeys(PWD);
	driver.findElement(By.id("submitButton")).click();
	
	
}
}
