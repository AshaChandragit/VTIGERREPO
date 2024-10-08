package practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ToReadDataFromPropertiesFileWithRandomNum {
public static void main(String[] args) throws IOException {
		//Read common data from Properties File
	FileInputStream pfis =new FileInputStream("");
	Properties prop=new Properties();
	prop.load(pfis);
	String BROWSER=prop.getProperty("Browser");
	String URL=prop.getProperty("Url");
	String UNAME=prop.getProperty("Username");
	String PWD=prop.getProperty("Password");

		//Generate the Random number
	Random ran=new Random();
	int r=ran.nextInt(1000);
	
	//Read test script data from excel file
	FileInputStream efis=new FileInputStream("");
	Workbook wb = WorkbookFactory.create(efis);
	Sheet sheet = wb.getSheet("Org");
	Row row = sheet.getRow(1);
	Cell cell = row.getCell(2);
	String orgName = cell.getStringCellValue()+r; //Attaching the random numbers to org name
	wb.close();
	
	//Automation Scripts
	//Step1: Launching the Browser
	WebDriver driver=null;
	if(BROWSER.equalsIgnoreCase("chrome")) {
		driver=new ChromeDriver();
		
	}
	
}
}
