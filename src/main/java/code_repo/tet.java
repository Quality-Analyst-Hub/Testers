package code_repo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class tet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//If we want to launch Firefox Browser.
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.google.com");
		
		//If we want to launch Chrome Browser.
		WebDriverManager.chromedriver().setup();
		WebDriver driver1 = new ChromeDriver();
		driver.get("https://www.google.com");
	}

}
