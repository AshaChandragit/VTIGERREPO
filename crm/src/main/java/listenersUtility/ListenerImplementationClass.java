package listenersUtility;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import genericUtility.JavaUtility;
import genericUtility.WebDriverUtility;

public class ListenerImplementationClass implements ITestListener, ISuiteListener {
public ExtentReports report;
public ExtentTest
	@Override
	public void onStart(ISuite suite) {
		System.out.println("Execution Started and Report Configuration");
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Execution completed and Report Backup");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+"==START==");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+"==SUCCESS==");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+"==FAILED==");
		WebDriverUtility wutil=new WebDriverUtility();
		JavaUtility jutil=new JavaUtility();
		String dateAndTime = jutil.getCurrentDateAndTimeForScreenshot();
		//try and catch method
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+"==SKIPPED==");
	}	
}
