package objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateCampaignPage {
WebDriver driver;

public CreateCampaignPage(WebDriver driver) {
	this.driver = driver;
	PageFactory.initElements(driver, this);
}

@FindBy(name = "campaignname")
private WebElement campName;

public WebElement getCampName() {
	return campName;
}

public WebElement getCloseDate() {
	return closeDate;
}

public WebElement getSaveBtn() {
	return saveBtn;
}

@FindBy(name = "closingdate")
private WebElement closeDate;

@FindBy(xpath="//input[@title='Save [Alt+S]']")
private WebElement saveBtn;
}
