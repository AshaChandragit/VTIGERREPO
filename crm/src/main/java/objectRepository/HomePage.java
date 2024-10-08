package objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
@FindBy(linkText="Organizations")
private WebElement OrgLink;

@FindBy(linkText = "Contacts")
private WebElement ContactLink;

@FindBy(linkText = "Campaigns")
private WebElement CampaignsLink;

@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
private WebElement Administrator;

@FindBy(linkText = "Sign Out")
private WebElement SignOutLink;

@FindBy(linkText = "More")
private WebElement MoreOptLink;

public WebElement getAdministrator() {
	return Administrator;
}

public WebElement getOrgLink() {
	return OrgLink;
}

public WebElement getContactLink() {
	return ContactLink;
}

public WebElement getCampaignsLink() {
	return CampaignsLink;
}

public WebElement getSignOutLink() {
	return SignOutLink;
}

public WebElement getMoreOptLink() {
	return MoreOptLink;
}

public void logout() {
	Actions action=new Actions(driver);
	action.moveToElement(Administrator).click().perform();
	SignOutLink.click();
}

public void navigateToCampaign() {
	Actions action=new Actions(driver);
	action.moveToElement(MoreOptLink).click().perform();
	CampaignsLink.click();
	
}
}
