package objectRepository;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericUtility.PropertyFileUtility;

public class LoginPage {
	//Create Constructor
	WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	PropertyFileUtility putil=new PropertyFileUtility();

@FindBy(name="user_name")
private WebElement usernameTF;

@FindBy(name="user_password")
private WebElement passwordTF;

@FindBy(id="submitButton")
private WebElement loginBtn;

public WebElement getUsernameTF() {
	return usernameTF;
}

public WebElement getPasswordTF() {
	return passwordTF;
}

public WebElement getLoginBtn() {
	return loginBtn;
}
public void login() throws IOException {
	String UNAME = putil.readDataFromProperties("Username");
	String PWD = putil.readDataFromProperties("Password");
	usernameTF.sendKeys(UNAME);
	passwordTF.sendKeys(PWD);
	loginBtn.click();
}
// Alternate way to create another method for login
public void login(String user, String pass) {
usernameTF.sendKeys(user);
passwordTF.sendKeys(pass);
	loginBtn.click();
}

}
