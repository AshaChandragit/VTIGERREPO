package practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ToLearnBatchExecution2 {
	@Test(groups = {"Smoke Test","Regression Test"})
	public void m4() {
		WebDriver driver=new ChromeDriver();
	System.out.println("M4 executed");//smoke
}
@Test(groups = "Regression Test")
public void m5() {
System.out.println("M5 executed");//Regression
}
@Test(groups = "Regression Test")
public void m6() {
System.out.println("M6 executed");//Regression
}
}
