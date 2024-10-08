package objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganisationInfoPage {
	WebDriver driver;
	public OrganisationInfoPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//span[@class='dvHeaderText']")
	 private WebElement headerText;
	public WebElement getHeaderText() {
		return headerText;
	}
	@FindBy(id = "dtlview_Industry")
	private WebElement industryHeader;
	
	public WebElement getIndustryHeader() {
		return industryHeader;
	}

	public WebElement getTypeHeader() {
		return typeHeader;
	}
	@FindBy(id = "dtlview_Type")
	private WebElement typeHeader;

	
}
