package genericUtility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility {
public void waitForPageToLoad(WebDriver driver) {
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	
}
public void waitForTheElementToBePresent(WebDriver driver, WebElement element) {
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
}
public void switchToframe(WebDriver driver, int index) {
	driver.switchTo().frame(index);
}
public void switchToframe(WebDriver driver, String nameOrId) {
	driver.switchTo().frame(nameOrId);
}
public void switchToframe(WebDriver driver, WebElement ele) {
	driver.switchTo().frame(ele);
}

public void switchToAlertAccept(WebDriver driver) {
	driver.switchTo().alert().accept();;
}
public void switchToAlertDismiss(WebDriver driver) {
	driver.switchTo().alert().dismiss();
	
}
public void moveToElement(WebDriver driver,WebElement ele) {
	Actions act=new Actions(driver);
	act.moveToElement(ele).perform();
}
public void doubleClick(WebDriver driver,WebElement ele) {
	Actions act=new Actions(driver);
	act.doubleClick(ele).perform();
}
public void select(WebElement ele,int index) {
	Select sel=new Select(ele);
	sel.selectByIndex(index);
}
public void select(WebElement ele,String value) {
	Select sel=new Select(ele);
	sel.selectByValue(value);
}
public void select(String visibleText,WebElement ele) {
	Select sel=new Select(ele);
	sel.selectByValue(visibleText);
}
public void takesScreenshotOfWebPage(WebDriver driver, String ssName) throws IOException {
	TakesScreenshot tks=(TakesScreenshot)driver;
	File src=tks.getScreenshotAs(OutputType.FILE);
	File dest=new File("./errorShots/"+ssName+".png");
	FileHandler.copy(src, dest);
}


}