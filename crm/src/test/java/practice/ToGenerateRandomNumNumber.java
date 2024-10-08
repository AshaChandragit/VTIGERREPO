package practice;

import java.util.Random;

public class ToGenerateRandomNumNumber {

	public static void main(String[] args) {
		//Step1: Create object of Random class
		Random ran=new Random();
		//Step2: Set the upper limit
		int random = ran.nextInt(1000);
		System.out.println(random);
		
	}

}
