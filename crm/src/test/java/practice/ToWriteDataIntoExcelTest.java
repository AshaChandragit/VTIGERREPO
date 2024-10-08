package practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ToWriteDataIntoExcelTest {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		//Step1: Create an object of FIS
		FileInputStream fis=new FileInputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\testScriptData.xlsx");
		//Step2: Open the workbook in read mode
		Workbook wb = WorkbookFactory.create(fis);
		//Step3: Get the control of sheet and row
		Row r = wb.getSheet("Org").getRow(1);
		//Step4: call “createCell()”
		Cell c = r.createCell(4);
		//Step5: Specify the Datatype using “setCellType(cellType)” and pass the value using “setCellValue()”
		c.setCellType(CellType.STRING);
		c.setCellValue("FAIL");
		//Step6: Open the workbook in write mode
		FileOutputStream fos=new FileOutputStream("C:\\Users\\chand\\OneDrive\\Desktop\\Study\\Asha\\QSpider\\Adv_Selenium\\testScriptData.xlsx");
		wb.write(fos);
		//Step7: Close the workbook
		wb.close();
	}

}
