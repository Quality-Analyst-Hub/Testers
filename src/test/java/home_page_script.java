import code_repo.repo_base;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class home_page_script extends repo_base {

	@BeforeClass
	public void open() {
		open_browser(ph.browsername, ph.URL);
	}

//WebElement footer_part;
	@Test(priority = 1)
	public void top_head() throws Exception {
		WebElement heading = null;

		heading = driver.findElement(By.xpath("//div[@class='page-wrapper']//header"));
		WebElement top_head = heading
				.findElement(By.xpath("//div[contains(@class,'top-head')]/div[contains(@class,'container')]"));
		List<WebElement> list = top_head.findElements(By.tagName("a"));
		String winHandleBefore1 = driver.getWindowHandle();
		for (int i = 0; i < list.size(); i++) {
			String display = list.get(i).findElement(By.xpath("..//..")).getCssValue("display");
			if (display.contains("none"))
				continue;
			else {
				list.get(i).sendKeys(kc.newtab);
				window_handel(winHandleBefore1);
				// vali(title1);
				// act.moveToElement(top_head).build().perform();
			}
		}

	}

	@Test(priority = 2)
	public void container() throws Exception {
		WebElement heading = null;
		heading = driver.findElement(By.xpath("//div[@class='page-wrapper']//header"));
		WebElement container = heading.findElement(By.xpath("//div[contains(@class,'header-main')]"));
		List<WebElement> list1 = container.findElements(By.tagName("a"));
		System.out.println(list1.size());
		String winHandleBefore1 = driver.getWindowHandle();
		for (int i = 0; i < list1.size(); i++) {

			String contain = list1.get(i).getAttribute("title");
			// System.out.println(contain);
			if (contain.equalsIgnoreCase("senskids")) {
				// System.out.println("aa gya");
				String img_alt = list1.get(i).findElement(By.tagName("img")).getAttribute("alt");
				assertEquals(img_alt, "Senskids");
			} else {
				if (i != 1) {
					list1.get(i).sendKeys(kc.newtab);
					if (i < (list1.size() - 1))
						window_handel(winHandleBefore1);
				}
				// vali(title1);
			}

		}

	}

	@Test(priority = 3)
	public void head_menu() throws Exception {
		Thread.sleep(3000);
		WebElement heading = null;
		String parent_window = driver.getWindowHandle();
		heading = driver.findElement(By.xpath("//div[@class='page-wrapper']//header"));
		WebElement headermenu = heading
				.findElement(By.xpath("//div[contains(@class,'header-menu')]/div[contains(@class,'container')]"));
		Actions a = new Actions(driver);
		List<WebElement> headerlinks = headermenu.findElements(By.xpath("//div[@id='navbarText']/ul/li"));
		headerlinks.get(0).click();
		int count = headerlinks.size();
		System.out.println(count);
		for (int i = 0; i < count; i++) {
			String text = headerlinks.get(i).getText();
			System.out.println(text);
			if (text.contentEquals("What's New")) {
				WebElement element1 = headermenu.findElement(By.linkText(text));
				a.moveToElement(element1).build().perform();
				Thread.sleep(2000);
				WebElement element = element1.findElement(By.xpath("//div[@id='navbarText']/ul/li[1]/div/div/div"));
				open_links(element, parent_window);
			} else if (text.contentEquals("Exclusive")) {
				WebElement element1 = headermenu.findElement(By.linkText(text));
				a.moveToElement(element1).build().perform();
				Thread.sleep(2000);
				WebElement element = element1.findElement(By.xpath("//div[@id='navbarText']/ul/li[2]/div/div/div"));
				open_links(element, parent_window);
			} else {
				a.moveToElement(headermenu).build().perform();
				String clickonlinktab = Keys.chord(Keys.CONTROL, Keys.ENTER);
				headerlinks.get(i).findElement(By.tagName("a")).sendKeys(clickonlinktab);
				String title = get_handelwindow_focus(parent_window);
				System.out.println(title);
			}
		}
	}

	@Test(priority = 5)
	public void search_field() throws Exception {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.PAGE_UP));
		WebElement heading = null;
		heading = driver.findElement(By.xpath("//div[@class='page-wrapper']//header"));
		insertdata(By.xpath("//input[@id='search']"), "frilled frock");
		Thread.sleep(2000);
		String winHandleBefore1 = driver.getWindowHandle();
		click_element(By.xpath("//button[contains(@class,'action search btn btn-outline-success')]"));
		Thread.sleep(5000);
		String searchdata = driver.findElement(By.xpath("//div[@class='list-heading-inner top-toolbar']/h2")).getText();
		System.out.println(searchdata);
		if (searchdata.equalsIgnoreCase("SEARCH RESULTS FOR: 'frilled frock'")) {
			assertTrue(true);
			window_handel(winHandleBefore1);
		} else
			assertTrue(false);
	}

	@Test(priority = 4)
	public void footer() throws Exception {
		scroll();
		WebElement footer_part = null;
		footer_part = driver.findElement(By.xpath("//footer[@class='page-footer']"));
		WebElement head_menu = footer_part
				.findElement(By.xpath("//div[contains(@class,'footer')]/div[contains(@class,'container')]"));
		List<WebElement> list = head_menu.findElements(By.tagName("a"));
		System.out.println(list.size());
		// String winHandleBefore1 = driver.getWindowHandle();
		// String winHandleBefore = driver.getWindowHandle();
		for (int i = 0; i < list.size(); i++) {
			String winHandleBefore1 = driver.getWindowHandle();
			WebElement ele = head_menu.findElements(By.tagName("a")).get(i);
			moveing(ele);
			String display = list.get(i).findElement(By.xpath("..//..//..")).getAttribute("class");
			String href = list.get(i).getAttribute("href");
			System.out.println(href);
			if (display.contains("footer-contact quick-link"))
				continue;
			else {
				list.get(i).sendKeys(kc.newtab);
			}

			window_handel(winHandleBefore1);
		}
	}

	@Test(priority = 7)
	public void newsletter() throws Exception {
		String[] expectedmessage = {"Thank you for your subscription."};
		insertdata(ph.newsletter, "test@mailinator.com");
		click_element(ph.newsletter_button);
		assertmultivalidate(ph.success_message, expectedmessage);
	}
	
	@Test(priority = 8)
	public void newsletter_with_already_registered_email() throws Exception {
		String[] expectedmessage = {"There was a problem with the subscription: This email address is already subscribed."};
		insertdata(ph.newsletter, "test@mailinator.com");
		click_element(ph.newsletter_button);
		assertmultivalidate(ph.errormsg, expectedmessage);
	}
	
	@Test(priority = 6)
	public void newsletter_with_wrong_email() throws Exception {
		String[] expectedmessage = {"Please enter a valid email address (Ex: johndoe@domain.com)."};
		insertdata(ph.newsletter, "test");
		click_element(ph.newsletter_button);
		assertmultivalidate(ph.err, expectedmessage);
	}
	//
	
	@AfterClass
	public void close_browser() {
		driver.close();
	}
}
