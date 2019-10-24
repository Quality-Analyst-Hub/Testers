package code_repo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class repo_base {

	public static WebDriver driver;
	public static WebElement element;
	public static List<WebElement> List;
	public static path ph = new path();
	public static key_chord kc = new key_chord();
	public static JavascriptExecutor js = (JavascriptExecutor) driver;
	public static Actions act;
	public static WebDriverWait wait ;
//	open browser
	public static void open_browser(String browsername, String url) {
		try {
			String path = System.getProperty("user.dir");
			if (browsername.equals("Mozilla")) {
				System.setProperty("webdriver.gecko.driver",
						path+"\\geckodriver\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browsername.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver", path+"\\chromedriver\\chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("incognito");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(options); 
			} else {
				System.setProperty("webdriver.gecko.driver",
						path+"\\geckodriver-v0.24.0-win64\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(url);
			wait = new WebDriverWait(driver, 200);
		} catch (Exception e) {

		}
	}

//	open headless browser
	public static void open_hlbrowser(String url) {
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

//	Click on a element
	public static void click_element(By path) {
		try {
			driver.findElement(path).click();
		} catch (Exception e) {

		}
	}

//
	public static void click_element_on_new_tab(By path) {
		try {
			driver.findElement(path).sendKeys(kc.newtab);
		} catch (Exception e) {

		}
	}

//	Scroll page at bottom
	public static void scroll() {
		try {
			driver.findElement(By.cssSelector("body")).sendKeys(kc.page_down);
		} catch (Exception e) {

		}
	}

//  Insert data into the element.
	public static void insertdata(By path, String data) throws IOException, InterruptedException {
		try {
			driver.findElement(path).clear();
			driver.findElement(path).sendKeys(Keys.chord(Keys.CONTROL, "a"), data);
		} catch (Exception e) {

		}

	}
	
	public String validate_attribute(By path) {

		element = driver.findElement(path);
		String validationMessage = element.getAttribute("validationMessage");
		return (validationMessage);
	}

	
	public String getUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}
	
//	clear the fields data.
	public static void clear(By path) {
		try {
			driver.findElement(path).clear();
		} catch (Exception e) {

		}
	}

//	validate error message
	public static void errormsg1(By path) {
		try {
			String errormsg = driver.findElement(path).getAttribute("innerHTML");
			String errormsg2 = "The password field is empty.";
			if (errormsg.contains(errormsg2)) {
				System.out.println("Error Message Validate for Valid Username and blank Password");
			} else {
				System.out.println("Test Case Failed");
			}
		} catch (Exception e) {

		}
	}

//	validate error message Via Excel file on the basis of row for a single 1st column.
	public static ArrayList xls(String Url, String sheetname) throws Exception {
//I have added test data in the first cell of A1. Cell A1 = row [0] and column [0]. It reads first row as 0 and Column A as 0.

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

//	validate page redirection message
	public static void validate(String expectedmessage) {
		try {
			String actualTitle = driver.getTitle();
			String expectedTitle = expectedmessage;
			assertEquals(expectedTitle, actualTitle);
		} catch (Exception e) {

		}
	}

//	validate Broken links
	public static void brokenlink() {
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

//  Handles all links in new tab and validate with excel .
	public static void handle_multiple_windowlink(By path, String Excel_sheet_name) throws Exception {

		element = driver.findElement(path);
		List = element.findElements(By.tagName("a"));
		System.out.println(List.size());
		ArrayList arr = xls(ph.xls_file, Excel_sheet_name);
		String title = driver.getTitle();
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(title);
		System.out.println(winHandleBefore);
		for (int k = 0; k < List.size(); k++) {
			// System.out.println(k);
			List.get(k).sendKeys(kc.newtab);
			for (String winHandle : driver.getWindowHandles()) {
				String handeltitle = driver.switchTo().window(winHandle).getTitle();
				if (handeltitle.equals(title)) {
					continue;
				} else {
					
					for(int j= 0; j<arr.size();j++) {
						String ttl = (String) arr.get(j);
						if (handeltitle.equals(ttl)) {
							Assert.assertTrue(handeltitle.contains(ttl));
						}
					}
					
					// System.out.println(ttl);
				}
			}
			Thread.sleep(1000);
			driver.close();
			driver.switchTo().window(winHandleBefore);
		}
	}

	// validate error message via assert and Excel sheet
	public static void assertvalid(String Url, String sheetname, String rowname, By path) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(Url);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetname);
			List<WebElement> actualmessage = driver.findElements(path);
			int row_num = sheet.getLastRowNum(); // No of rows.
			for (int i = 0; i < row_num; i++) {
				Cell cell = sheet.getRow(i).getCell(0);
				String cellval = cell.getStringCellValue();
				if (cellval.contains(rowname)) {
					Cell scell = sheet.getRow(i).getCell(1);
					String scellval = scell.getStringCellValue();
					String[] expectedmessage = { scellval };
					int j = 0;
					for (WebElement mymessage : actualmessage) {

						if (mymessage.getText().equals("")) {
//							System.out.println("continue");
							continue;
						} else {
//							System.out.println("message");
//						System.out.println(expectedmessage[j]);
							String validationMessage = element.getAttribute("validationMessage");
							System.out.println(validationMessage);
							Assert.assertTrue(mymessage.getText().contains(expectedmessage[j]));
						}
						j++;
					}

				}
			}
		} catch (Exception e) {

		}
	}

//  validate error message via assert when we have multiple validation on a page on a single click. 
	public static void assertmultivalidate(By path, String[] expectedmessage) throws Exception {
		try {
			List<WebElement> actualmessage = driver.findElements(path);
			
			String[] expectedmsg = expectedmessage;
			
			int x = actualmessage.size();
			System.out.println(x);
			//Assert.assertEquals(x, errcount);
			int i = 0;
			for (WebElement mymessage : actualmessage) {

				System.out.println("**** " + mymessage.getText());
				
				if (mymessage.getText().equals("")) {
					continue;
				}

				else {
					Assert.assertTrue(mymessage.getText().equals(expectedmsg[i]));
					i++;
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//  Validate disable fields.
	public static void assertdisable(By path) {
		WebElement button = driver.findElement(path);
		Assert.assertFalse(button.isEnabled(), "Button is disabled.");
	}

//	Select element from drop-down list.
	public static void select_data(By path, String value) throws IOException {
		Select s = new Select(driver.findElement(path));
		s.selectByVisibleText(value);
//				s.selectByIndex(3);
//				s.selectByValue(value);
		/*
		 * List = s.getOptions(); // this code is used for displayed the all listing
		 * details of that drop-down list. for(int i=0;i<List.size();i++) {
		 * System.out.println(List.get(i).getText()); }
		 */
	}

//	open products on new tab from product listing.
	public static void focous_on_new_tab() throws Exception {
		FileInputStream fis = new FileInputStream("F:\\Text.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet2");
		int row_num = sheet.getLastRowNum(); // No of rows.
		System.out.println(row_num);
		element = driver.findElement(By.xpath("//div[@class='footer-link-quick']"));
		List = element.findElements(By.tagName("a"));
		ArrayList str = new ArrayList();
		/*
		 * for (int j = 0; j < List.size(); j++) {
		 * System.out.println(List.get(j).getText()); }
		 */
		for (int i = 0; i <= row_num; i++) {
			Cell cell = sheet.getRow(i).getCell(0);
			String cellval = cell.getStringCellValue();
			str.add(cellval);
		}
		/*
		 * for (int i = 0; i < row_num; i++) { System.out.println(str.get(i));
		 * //str.add(cellval); }
		 */
//		System.out.println(str.size());
//		System.out.println(str.get(13));
		System.out.println(List.size());
		String title = driver.getTitle();
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(title);
		System.out.println(winHandleBefore);
		for (int k = 0; k < List.size(); k++) {
			System.out.println(k);
			List.get(k).sendKeys(kc.newtab);
			for (String winHandle : driver.getWindowHandles()) {
				String handeltitle = driver.switchTo().window(winHandle).getTitle();

				if (handeltitle.equals(title)) {
					continue;
				} else {
					// print expected message.tomorrow
					String ttl = (String) str.get(k);
					Assert.assertTrue(handeltitle.contains(ttl));
					System.out.println(ttl);
				}
			}
//							System.out.println(title);
			Thread.sleep(1000);

			driver.close();
			driver.switchTo().window(winHandleBefore);
		}
	}
	
	public String handel_alert() throws Exception {
		String dat=driver.switchTo().alert().getText();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		return dat;
	}
	
	public static void moveing(WebElement element) {
		act= new Actions(driver);
		act.moveToElement(element).build().perform();
	}
	

	public static void window_handel(String winHandleBefore) throws Exception {
	//	List.get(k).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
		String title = null;
		for (String winHandle : driver.getWindowHandles()) {
			title = driver.switchTo().window(winHandle).getTitle();
			System.out.println(title);			
			// System.out.println(ttl);
			
		}
	
		Thread.sleep(1000);
		driver.close();
		driver.switchTo().window(winHandleBefore);
		
	}
	
	public static void open_links(WebElement ele, String parent_window) throws Exception {
		int count1 = ele.findElements(By.tagName("a")).size();
				System.out.println(count1);
				for (int j = 0; j < count1; j++) {
					ele.findElements(By.tagName("a")).get(j).sendKeys(Keys.CONTROL, Keys.ENTER);
					String title = get_handelwindow_focus(parent_window);
					System.out.println(title);
				}
		}
	
	public static String get_handelwindow_focus(String parent_window) throws Exception {
		Set<String> set = driver.getWindowHandles();
		Iterator<String> it = set.iterator();
		String title = null;
		while(it.hasNext()) {
			String data = it.next();
			if(data.equals(parent_window)) {
				continue;
			}else {
				System.out.println(data);
				driver.switchTo().window(data);
				title = driver.getTitle();
				Thread.sleep(2000);
				driver.close();
			}
			driver.switchTo().window(parent_window);	
		}
		
		return title;
	}
	
	public static void sorting_validate(ArrayList Final_product_price, String type) {
		for (int i = 0; i < (Final_product_price.size()) - 1; i++) {
			String product_pricing = String.valueOf(Final_product_price.get(i));
			float first_price1 = Float.parseFloat(product_pricing);
			for (int j = i + 1; j < (Final_product_price.size()); j++) {
				String product_pricing2 = String.valueOf(Final_product_price.get(i));
				float second_price2 = Float.parseFloat(product_pricing);
				if(type.equalsIgnoreCase("assending")) {
					assertTrue(first_price1 <= second_price2);
				}else if(type.equalsIgnoreCase("decending")) {
					assertTrue(first_price1 >= second_price2);
				}
			}
		}
	}
	
	public static void select_data_from_list(List<WebElement> li, String data, String type) throws Exception {
		for (int i = 0; i < li.size(); i++) {
			String size;
			if(type.equalsIgnoreCase("color"))
				size = li.get(i).getAttribute("option-label");	
			else
				size = li.get(i).getText();
			if (size.equalsIgnoreCase(data)) {
				li.get(i).click();
				//click_element((By) li.get(i));
				Thread.sleep(2000);
				break;
			}
		}
	}
}
