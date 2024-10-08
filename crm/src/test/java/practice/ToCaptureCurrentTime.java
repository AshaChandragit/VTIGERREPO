package practice;

import java.util.Date;

public class ToCaptureCurrentTime {
	public static void main(String[] args) {
		
		String date=new Date().toString();
		System.out.println(date);
		String date1=new Date().toString().replace(" ", "_").replace(":", "_");
		System.out.println(date1);
		
		// another way to capture current date and time for screenshots
		Date d=new Date();
		System.out.println(d);
		String date2[]=d.toString().split(" ");
		String date3=date2[2];
		String month=date2[1];
		String year=date2[5];
		String time=date2[3].replace(":", "_");
		String finalDate=date3+" "+month+" "+year+" "+time;
		System.out.println(finalDate);
	}
}