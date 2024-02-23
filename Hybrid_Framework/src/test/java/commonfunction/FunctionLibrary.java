package commonfunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;

	// Method for launch browser
	public static WebDriver StartBrowser() throws Throwable {

		conpro = new Properties();
		
		conpro.load(new FileInputStream("D:\\Live_project\\Hybrid_Framework\\PropertyFile\\Environment.properties"));

		if (conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (conpro.getProperty("Browser").equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		} else {
			Reporter.log("Browser value is not matching", true);
		}
		return driver;
	}

	// method for Launching url
	public static void OpenUrl() {
		driver.get(conpro.getProperty("Url"));
	}

	// method for waitforelement

	public static void WaitForElement(String Locator_Type, String Locator_Value, String Test_Data) {
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));

		if (Locator_Type.equalsIgnoreCase("Xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}

		if (Locator_Type.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}

		if (Locator_Type.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value))); 
		}
	}

	// method for type action used to perform action in text boxes
	public static void TypeAction(String Locator_Type, String Locator_Value, String Test_Data) {
		if (Locator_Type.equalsIgnoreCase("Xpath")) {
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		if (Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		if (Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}

	// method for clickaction to perform action on
	// buttons,imagees,links,radiobuttons,checkboxes
	public static void ClickAction(String Locator_Type, String Locator_Value) {
		if (Locator_Type.equalsIgnoreCase("Xpath")) {
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		if (Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).click();
		}
		if (Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
	}

	// method for validation of title
	public static void ValidateTitle(String Expected_Title) {
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title, "Title is not matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}

	// method for close browser
	public static void CloseBrowser() {
		driver.quit();
	}
//	method for dropdownaction
	public static void dropDownAction(String Locator_Type, String Locator_Value, String Test_Data) {
	if (Locator_Type.equalsIgnoreCase("Xpath")) {
		int value=Integer.parseInt(Test_Data);
		Select  element=new  Select(driver.findElement(By.xpath(Locator_Value)));
		element.selectByIndex(value);
	}	
	if (Locator_Type.equalsIgnoreCase("name")) {
		int value=Integer.parseInt(Test_Data);
		Select  element=new  Select(driver.findElement(By.name(Locator_Value)));
		element.selectByIndex(value);
	}	
	if (Locator_Type.equalsIgnoreCase("id")) {
		int value=Integer.parseInt(Test_Data);
		Select element=new Select(driver.findElement(By.id(Locator_Value)));
		element.selectByIndex(value);
	}
	}
//	method for writing stocknumber
	public static void CaptureStockNum(String Locator_Type, String Locator_Value) throws Throwable {
		String stocknum="";
		if (Locator_Type.equalsIgnoreCase("name")) {
		stocknum=driver.findElement(By.name(Locator_Value))	.getAttribute("value");
		}
		if (Locator_Type.equalsIgnoreCase("id")) {
		stocknum=driver.findElement(By.id(Locator_Value)).getAttribute("value")	;
		}
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			stocknum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		FileWriter  fw=new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(stocknum);
		bw.flush();
		bw.close();
	}
//method for stock table
	public static void   stocktable() throws Throwable { 
		
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data= br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button") )).click();
		Thread.sleep(4000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
		Assert.assertEquals(Exp_Data, Act_Data,"Stock Number Not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
//method for writing supplier number
	public static void capturesup(String Locator_Type, String Locator_Value, String Test_Data) throws Throwable {
	String suppliernum="";
	if (Locator_Type.equalsIgnoreCase("name")) {
		suppliernum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	if (Locator_Type.equalsIgnoreCase("xpath")) {
		suppliernum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}	
	if (Locator_Type.equalsIgnoreCase("id")) {
	suppliernum=driver.findElement(By.id(Locator_Value)).getAttribute("value")	;
	}	
	FileWriter  fw=new  FileWriter("./CaptureData/suppliernum.txt")	;
	BufferedWriter  bw=new  BufferedWriter(fw);
	bw.write(suppliernum);
	bw.flush();
	bw.close();
	}
//method for supplier table
	public static void supplierTable() throws Throwable {
	FileReader  fr=new  FileReader("./CaptureData/suppliernum.txt");	
	BufferedReader  br=new BufferedReader(fr) ;
	String exp_data=br.readLine();
	if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
	driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	String act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(exp_data+"    "+act_data);
	try {
	Assert.assertEquals(exp_data, act_data,"supplier number not matching")	;
		
	}catch(AssertionError e) {
	System.out.println(e.getMessage());}
	}	
	}
	//method for writing customer number
		public static void capturecustomer(String Locator_Type, String Locator_Value, String Test_Data) throws Throwable {
		String customernum="";
		if (Locator_Type.equalsIgnoreCase("name")) {
			customernum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			customernum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}	
		if (Locator_Type.equalsIgnoreCase("id")) {
			customernum=driver.findElement(By.id(Locator_Value)).getAttribute("value")	;
		}	
		FileWriter  fw=new  FileWriter("./CaptureData/customernum.txt")	;
		BufferedWriter  bw=new  BufferedWriter(fw);
		bw.write(customernum);
		bw.flush();
		bw.close();
		}
		//method for customer table
		public static void customerTable() throws Throwable {
		FileReader  fr=new  FileReader("./CaptureData/customernum.txt");	
		BufferedReader  br=new BufferedReader(fr) ;
		String exp_data=br.readLine();
		if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		String act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(exp_data+"    "+act_data);
		try {
		Assert.assertEquals(exp_data, act_data,"supplier number not matching")	;
			
		}catch(AssertionError e) {
		System.out.println(e.getMessage());}
		}	
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
