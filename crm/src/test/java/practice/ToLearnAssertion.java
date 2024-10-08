package practice;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ToLearnAssertion {
@Test
	public void homePage() {
	System.out.println("Step1");
	System.out.println("Step2");
	Assert.assertEquals("Home", "Home");//if this Hard assert fail, execution stops here
	System.out.println("Step3");
	SoftAssert soft=new SoftAssert();//if this Soft Assert fails, execution will continue and tells us the error at the end of execution
	soft.assertEquals("Home_CRM", "Home_CR");
	System.out.println("Step4");
	soft.assertAll();
}
}
