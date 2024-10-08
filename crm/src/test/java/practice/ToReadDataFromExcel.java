package practice;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ToReadDataFromExcel {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
//Step1: Create the object of FIS
		FileInputStream fis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\practiceTestData.xlsx");
		
		//Step2: Open the workbook in read mode
		Workbook wb = WorkbookFactory.create(fis);
		
		Sheet sh = wb.getSheet("Sheet1");
	int rowCount = sh.getLastRowNum();
	for(int i=1;i<=rowCount;i++) {
		Row r=sh.getRow(i);
		String prodBrand=r.getCell(0).getStringCellValue();
		String prodName=r.getCell(1).getStringCellValue();
	System.out.println(prodBrand+" \t"+prodName);
	}
	
		wb.close();		
	}

}
