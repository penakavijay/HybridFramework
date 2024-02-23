package driverfactory;

import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfunction.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	public static WebDriver  driver;
	String inputpath="./FileInput/DataEngine.xlsx";
	String outputpath="./FileOutPut/Hybridresults.xlsx";
	ExtentReports  report;
	ExtentTest  logger;
	@Test
	public void starttest() throws Throwable {
		String Modulestatus="";
		//	create object for excelfile util class
		ExcelFileUtil  xl=new ExcelFileUtil(inputpath);
		String Testcases="MasterTestCases";
		//	iterate all rows in test case sheet
		for(int i=1;i<=xl.rowcount(Testcases);i++)
		{
			if (xl.getcelldata(Testcases, i, 2).equalsIgnoreCase("Y")) {
				//	read all the test cases or corrsponding sheets
				String TcModule=xl.getcelldata(Testcases, i, 1);
				report=new  ExtentReports("./target/Reports/"+TcModule+".html");
				logger=report.startTest(TcModule);
				//	iterate all rows in TcModule sheet
				for (int j = 1; j<=xl.rowcount(TcModule); j++) {
					//		read all cells in TcModule
					String Description=xl.getcelldata(TcModule, j, 0);
					String Object_Type=xl.getcelldata(TcModule, j, 1);
					String Locator_Type=xl.getcelldata(TcModule, j, 2);
					String Locator_Value=xl.getcelldata(TcModule, j, 3);
					String Test_Data=xl.getcelldata(TcModule, j, 4);
					try {
						if (Object_Type.equalsIgnoreCase("StartBrowser")) {
							driver=FunctionLibrary.StartBrowser();
							logger.log(LogStatus.INFO,Description );
						}
						if (Object_Type.equalsIgnoreCase("OpenUrl")) {
							FunctionLibrary.OpenUrl();
							logger.log(LogStatus.INFO, Description);
						}   
						if (Object_Type.equalsIgnoreCase("WaitForElement")) {
							FunctionLibrary.WaitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}      
						if (Object_Type.equalsIgnoreCase("TypeAction")) {
							FunctionLibrary.TypeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}        
						if (Object_Type.equalsIgnoreCase("ClickAction")) {
							FunctionLibrary.ClickAction(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						} 
						if (Object_Type.equalsIgnoreCase("ValidateTitle")) {
							FunctionLibrary.ValidateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						} 
						if (Object_Type.equalsIgnoreCase("CloseBrowser")) {
							FunctionLibrary.CloseBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("dropDownAction")) {
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("CaptureStockNum")) {
							FunctionLibrary.CaptureStockNum(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("stocktable")) {
							FunctionLibrary.stocktable();
							logger.log(LogStatus.INFO, Description);
							
						}
//						supplier data
						if (Object_Type.equalsIgnoreCase("capturesup")) {
							FunctionLibrary.capturesup(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("supplierTable")) {
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
//						customer data
						if (Object_Type.equalsIgnoreCase("capturecustomer")) {
						FunctionLibrary.capturecustomer(Locator_Type, Locator_Value, Test_Data)	;
						logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("customerTable")) {
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
						//       	write as pass into status cell in TcModule
						xl.setcelldata(TcModule, j, 5, "pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Modulestatus="True";
					} catch (Exception e) { 
						System.out.println(e.getMessage());
						//		write as Fail into status cell in TcModule	
						xl.setcelldata(TcModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Modulestatus="False";	
					} 
					if (Modulestatus.equalsIgnoreCase("True")) {
						//	write as pass in TestCases sheet
						xl.setcelldata(Testcases, i, 3, "pass", outputpath);
					}else {
						xl.setcelldata(Testcases, i, 3, "Fail", outputpath);
					}report.endTest(logger);
					report.flush();

				}	
			}	
			else {
				//		write as blocked into status cell for Flag N
				xl.setcelldata(Testcases, i, 3, "Blocked", outputpath);
			}	

		}






	}








































}
