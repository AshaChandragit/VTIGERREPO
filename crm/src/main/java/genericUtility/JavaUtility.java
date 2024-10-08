package genericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
//Generate Random number
	public int getRandomNumber() {
		Random random=new Random();
		int r=random.nextInt(5000);
		return r;
	}
	
	//capture the system date
	
	public String getSystemDateyyyyMMdd() {
		Date dateObj=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String date=sim.format(dateObj);
		return date;
	}
	
	
	//Get Required data/ Date
	public String getRequiredDateyyyyMMdd(int days) {
		Date dateObj=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		sim.format(dateObj);
		Calendar cal=sim.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);
		String reqDate=sim.format(cal.getTime());
		return reqDate;
	}	
		
	//ToCapture current date and time to attach to the screenshot
	public String  getCurrentDateAndTimeForScreenshot() {
		String date=new Date().toString().replace(" ", "_").replace(":", "_");
				return date;
	}
}
