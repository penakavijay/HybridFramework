package utilities;

import java.awt.image.IndexColorModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook  wb;
	//constructor for reading Excel file
	public ExcelFileUtil(String Excelpath) throws Throwable {

		FileInputStream  f=new  FileInputStream(Excelpath);
		wb=WorkbookFactory.create(f);	
	}

	//Method for counting no.of rows in sheeet
	public int rowcount(String sheetname) {

		return wb.getSheet(sheetname).getLastRowNum();
	}
	//Method for reading cell data from sheet
	public String getcelldata(String sheetname,int row,int column) {
		String data=" ";
		if (wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC) {
			int celldata=(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celldata);
		} else {
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;	
	}
	//Methods for writing status
	public void setcelldata(String sheetname,int row,int column,String status,String writeExcel) throws Throwable {
		Sheet ws=wb.getSheet(sheetname);
		Row rownum=ws.getRow(row);
		Cell cell=rownum.createCell(column);
		cell.setCellValue(status);
		if (status.equalsIgnoreCase("pass")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BRIGHT_GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		} else if (status.equalsIgnoreCase("Fail")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.DARK_RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		} else if (status.equalsIgnoreCase("Blocked")) {
			CellStyle style=wb.createCellStyle();
			Font  font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		FileOutputStream  f0=new  FileOutputStream(writeExcel);
		wb.write(f0);
	}





}



























