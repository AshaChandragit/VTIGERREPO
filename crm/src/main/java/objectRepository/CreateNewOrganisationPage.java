package objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateNewOrganisationPage {
	WebDriver driver;
	public CreateNewOrganisationPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="accountname")
	private WebElement OrgName;
	
	@FindBy(name="industry")
	private WebElement Industry;
	
	@FindBy(name="accounttype")
	private WebElement Type;
	
	@FindBy(xpath="//input[@title='Save [Alt+S]']")
	private WebElement SaveBtn;
	
	public WebElement getOrgName() {
		return OrgName;
	}

	public WebElement getIndustry() {
		return Industry;
	}

	public WebElement getType() {
		return Type;
	}

	public WebElement getSaveBtn() {
		return SaveBtn;
	}
	public void createOrg(String org) {
		OrgName.sendKeys(org);
		SaveBtn.click();
	}
	public void createOrg(String org, String industry) {
		OrgName.sendKeys(org);
		Select sel1=new Select(Industry);
		sel1.selectByVisibleText(industry);
		SaveBtn.click();
	}	
	public void createOrg(String org, String industry, String type) {
		OrgName.sendKeys(org);
		Select sel1=new Select(Industry);
		sel1.selectByVisibleText(industry);
		Select sel2=new Select(Type);
		sel2.selectByVisibleText(type);
		SaveBtn.click();
	}
	
	
}