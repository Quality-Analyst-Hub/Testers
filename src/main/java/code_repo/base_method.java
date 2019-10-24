package code_repo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class base_method {
	public static WebDriver driver;
	public static WebElement element;
	public static List<WebElement> List;
	public static WebDriverWait wait;
	public static path ph = new path();
	public static key_chord kc = new key_chord();
	public static JavascriptExecutor js = (JavascriptExecutor) driver;

//	Open an web application into an Browser.
	public void open_browser(String browsername, String url) {
		try {
			if (browsername.equals("Mozilla")) {
				System.setProperty("webdriver.gecko.driver",
						"F:\\selenium\\geckodriver-v0.24.0-win64\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browsername.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "E:\\amit Projects\\selenium\\chromedriver\\chromedriver.exe");
				driver = new ChromeDriver();
			} else {
				System.setProperty("webdriver.gecko.driver",
						"F:\\selenium\\geckodriver-v0.24.0-win64\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(url);
		} catch (Exception e) {

		}
	}

//	Open Chrome Headless Browser.	
	public void open_hlbrowser(String url) {
		try {
			System.setProperty("webdriver.chrome.driver", "F:\\selenium\\chromedriver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(capabilities);
			ChromeDriver driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.get(url);
		} catch (Exception e) {

		}
	}

//	Click Action on an element.
	public void click_element(By path) {
		try {
			driver.findElement(path).click();
		} catch (Exception e) {

		}
	}

//	Click Action for New Tab.	
	public void click_element_on_new_tab(By path) {
		try {
			driver.findElement(path).sendKeys(kc.newtab);
		} catch (Exception e) {

		}
	}

//	Scroll page at bottom
	public void scroll() {
		try {
			driver.findElement(By.cssSelector("body")).sendKeys(kc.page_down);
		} catch (Exception e) {

		}
	}

//  Insert data into the element.
	public void insertdata(By path, String data) throws IOException, InterruptedException {
		try {
			driver.findElement(path).clear();
			driver.findElement(path).sendKeys(data);
		} catch (Exception e) {

		}

	}

//	clear the fields data.
	public void clear(By path) {
		try {
			driver.findElement(path).clear();
		} catch (Exception e) {

		}
	}	

//	/	validate error message Via Excel file on the basis of row for a single 1st column.
	public ArrayList xls(String Url, String sheetname) throws Exception {
		FileInputStream fis = new FileInputStream(Url);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetname);
		int row_num = sheet.getLastRowNum(); // No of rows.
		int col_num = sheet.getRow(0).getLastCellNum();
		System.out.println(row_num);
		System.out.println(col_num);
		ArrayList str = new ArrayList();
		for (int i = 0; i <= row_num; i++) {
			for (int j = 0; j < col_num; j++) {
				Cell cell = sheet.getRow(i).getCell(j);
				String cellval = cell.getStringCellValue();
				str.add(cellval);
			}
		}
		return str;
	}

//	validate ALL Broken links into a page
	public void brokenlink() {
		String url = driver.getCurrentUrl();
		try {
			URL Url = new URL(url);
			HttpURLConnection httpURLConnect = (HttpURLConnection) Url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();
			if (httpURLConnect.getResponseCode() == 200) {
				System.out.println(url + " - " + httpURLConnect.getResponseMessage());
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println(
						url + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

//	Handel Multiple Windows and return their titles.
	public String window_handel(String winHandleBefore) throws Exception {
		String title = null;
		for (String winHandle : driver.getWindowHandles()) {
			title = driver.switchTo().window(winHandle).getTitle();
			System.out.println(title);			
		}
		Thread.sleep(1000);
		driver.close();
		driver.switchTo().window(winHandleBefore);
		return title;
	}

//	Validate message according to the messages in arraylist using assert.
	public void vali(String handeltitle, ArrayList arraylist) {
		ArrayList arr = arraylist;
		for(int j= 0; j<arr.size(); j++) {
			String ttl = (String) arr.get(j);
			if (handeltitle.contains(ttl)) {
				Assert.assertTrue(handeltitle.equalsIgnoreCase(ttl));
				System.out.println(ttl+" validate");
			}
		}
	}

//	Select element from drop-down list.
	public void select_data(By path, String value) throws IOException {
		Select s = new Select(driver.findElement(path));
		s.selectByVisibleText(value);
	}

//	Handel alert Window and return the text details.
	public String handel_alert() throws Exception {
//		Handel alert Window and return the text details.
		String dat=driver.switchTo().alert().getText();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		return dat;
	}
	
//	get the cart count details.	
	public int get_cart_value(By path) {
		int cart_count = Integer.parseInt(driver.findElement(path).getText());
		return cart_count ;
	}

// Still in development mode.
	public void sorting_result(String first, String second, String compare_type, String data_type) {
		if(data_type.equalsIgnoreCase("int")) {
			if(compare_type.equalsIgnoreCase("lessthanequalto")) {
				
			}
		}
		else if(data_type.equalsIgnoreCase("float")) {
			
		}
		else {
			
		}
		
	}
}
