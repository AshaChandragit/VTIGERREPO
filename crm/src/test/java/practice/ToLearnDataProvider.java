package practice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ToLearnDataProvider {
@Test(dataProvider = "getDetails")
public void empDetails(String fName, String lName, int emp) {
	System.out.println("First Name: "+fName+" Last Name: "+lName+" emp Id: "+emp);
}
@DataProvider
public Object[][] getDetails() {
	Object[][] objArr=new Object[4][2];
	objArr[0][0]="Srinivasa";
	objArr[0][1]="Murthy";
	objArr[0][2]=123;
	objArr[1][0]="Venkata";
	objArr[1][1]="Sravani";
	objArr[1][2]=124;
	objArr[2][0]="Shilpa";
	objArr[2][1]="J";
	objArr[2][2]=125;
	objArr[3][0]="Vipendra";
	objArr[3][1]="Mishra";
	objArr[3][2]=126;
	return objArr;
}

}
