import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import code_repo.repo_base;

public class cart_popup extends repo_base {

	@BeforeClass
	public void browser() {
		open_browser(ph.browsername, "http://senskids.mag2.demo321.com/test/tshirts-for-boys-combo-of-3.html");
	}

	/*
	 * @Test(priority = 1) public void open_empty_cart_popup() throws Exception {
	 * click_element(By.xpath("//div[@class='minicart-wrapper']")); String display =
	 * driver.findElement(By.xpath(
	 * "//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front mage-dropdown-dialog']"
	 * )) .getCssValue("display"); assertEquals(display, "block");
	 * Thread.sleep(3000); }
	 * 
	 * @Test(priority = 2) public void validate_empty_cart_popup() throws Exception
	 * { String[] expectedmessage = { "You have no items in your shopping cart." };
	 * By pass = By.xpath(
	 * "//div[@id='minicart-content-wrapper']/div[@class='block-content']/strong");
	 * System.out.println(pass); assertmultivalidate(pass, expectedmessage); }
	 * 
	 * @Test(priority = 3) public void close_cart_popup() throws Exception {
	 * click_element(By.xpath("//button[@id='btn-minicart-close']"));
	 * Thread.sleep(2000); String display = driver.findElement(By.xpath(
	 * "//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front mage-dropdown-dialog']"
	 * )) .getCssValue("display"); assertEquals(display, "none"); }
	 */
	@Test(priority = 4)
	public void add_cart_popup() throws Exception {
		Thread.sleep(5000);
		int cart_item = Integer
				.parseInt(driver.findElement(By.xpath("//a[@class='action showcart']/span/b")).getText());
		// System.out.println(cart_item);
		click_element(By.xpath("//button[@id='product-addtocart-button']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-ui-id='message-success']")));

		click_element(By.xpath("//button[@id='product-addtocart-button']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-ui-id='message-success']")));
		Thread.sleep(5000);
		int update_cart_item = Integer
				.parseInt(driver.findElement(By.xpath("//a[@class='action showcart']/span/b")).getText());
		// System.out.println(update_cart_item);
		Assert.assertTrue(update_cart_item > cart_item);
	}

	@Test(priority = 5)
	public void open_nonempty_cart_popup() throws Exception {
		click_element(By.xpath("//div[@class='minicart-wrapper']"));
		String display = driver.findElement(By.xpath(
				"//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front mage-dropdown-dialog']"))
				.getCssValue("display");
		assertEquals(display, "block");
		Thread.sleep(3000);
	}

	@Test(priority = 6)
	public void validate_products_in_cart_popup() throws Exception {
		WebElement ele = driver.findElement(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"));
		List<WebElement> list = ele.findElements(By.tagName("li"));
		int size = list.size();
		// System.out.println(size);
		String items = driver.findElement(By.xpath("//div[@class='items-total']/span[@class='count']")).getText()
				.trim();
		int item = Integer.parseInt(items);
		// System.out.println(items);
		Thread.sleep(3000);
		assertEquals(item, size);
	}

	@Test(priority = 7)
	public void validate_products_amount_in_cart_popup() throws Exception {
		WebElement ele = driver.findElement(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"));
		List<WebElement> list = ele.findElements(By.tagName("li"));
		float total_price = 0.0f;
		for (int i = 0; i < list.size(); i++) {
			String qty = list.get(i).findElement(By.xpath("//div[@class='details-qty qty']/input"))
					.getAttribute("data-item-qty");
			int quantity = Integer.parseInt(qty);
			// System.out.println(qty);
			String price = list.get(i).findElement(By.xpath("//span[@class='minicart-price']//span[@class='price']"))
					.getText().split(" ")[1].trim();
			float item = Float.parseFloat(price.replaceAll(",", ""));
			// System.out.println(price);
			total_price += quantity * item;
		}
		String price_txt = driver
				.findElement(By.xpath("//div[@class='amount price-container']//span[@class='price-wrapper']/span"))
				.getText().trim();
		System.out.println(price_txt);
		String price = price_txt.split(" ")[1].trim();
		float item = Float.parseFloat(price.replaceAll(",", ""));
		System.out.println(item);
		Thread.sleep(3000);
		assertEquals(item, total_price);

	}

	@Test(priority = 8)
	public void update_product_quantity_in_cart_popup() throws Exception {
		String name = "Tshirts For Boys Combo Of 3";
		String qnty = "5";
		WebElement ele = driver.findElement(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"));
		List<WebElement> list = ele.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {

			String product_name = list.get(i).findElement(By.xpath("//div[@class='product-item-details']/strong/a"))
					.getText();
			if (product_name.equalsIgnoreCase(name)) {
				insertdata(By.xpath("//div[@class='details-qty qty']/input"), qnty);
				click_element(By.xpath("//span[contains(text(),'Update')]"));
				Thread.sleep(16000);
				list = ele.findElements(By.tagName("li"));
				String qty = list.get(i)
						.findElement(By.xpath("//div[@class='details-qty qty']/input[@class='item-qty cart-item-qty']"))
						.getAttribute("data-item-qty");

				assertEquals(qty, qnty);
				// assertmultivalidate(ph.success_message, expectedmessage);
				//
			}
		}
	}

	@Test(priority = 10)
	public void delete_product_from_cart_popup() throws Exception {
		String name = "Tshirts For Boys Combo Of 3";
		String expmessage = "Are you sure you would like to remove this item from the shopping cart?";
		WebElement ele = driver.findElement(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"));
		List<WebElement> list = ele.findElements(By.tagName("li"));
		int list_size = list.size();
		for (int i = 0; i < list.size(); i++) {
			String product_name = list.get(i).findElement(By.xpath("//div[@class='product-item-details']/strong/a"))
					.getText();
			if (product_name.equalsIgnoreCase(name)) {
				list.get(i).findElement(By.xpath("//a[@class='action delete']")).click();
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//aside[contains(@class,'_show')]")));
				WebElement popup = driver.findElement(
						By.xpath("//aside[contains(@class,'_show')]//div[contains(@class,'modal-inner-wrap')]"));
				List<WebElement> list1 = popup.findElements(By.tagName("div"));
				System.out.println(list1.size());
				String a = list1.get(i).getText();
				assertEquals(a, expmessage);
				List<WebElement> modellist = popup.findElements(By.tagName("footer"));
				modellist.get(0).findElement(By.xpath("//button[contains(@class,'action-primary action-accept')]"))
						.click();
				Thread.sleep(9000);
				System.out.println(modellist.size());
				boolean exist = driver.findElements(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"))
						.size() != 0;
				if (exist) {
					WebElement ele2 = driver
							.findElement(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"));
					list = ele2.findElements(By.tagName("li"));
					int updatesize = list.size();
					assertTrue(updatesize < list_size);
				}
			}
		}
	}

	@Test(priority = 9)
	public void view_shopping_cart() throws Exception {
		Thread.sleep(200);
		boolean exist = driver.findElements(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"))
				.size() != 0;
		if (exist) {
			driver.findElement(By.xpath("//a[contains(@class,'action viewcart')]")).click();
			String title = driver.getTitle();
			assertEquals(title, "Shopping Cart");
			driver.navigate().back();
		}
	}
	
	@Test(priority = 11)
	public void proceed_to_checkout() throws Exception {
		boolean exist = driver.findElements(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol"))
				.size() != 0;
		if (!exist) {
			click_element(By.xpath("//div[@class='minicart-wrapper']"));
			Thread.sleep(500);
		}
		driver.findElement(By.xpath("//button[@id='top-cart-btn-checkout']")).click();
		String title = driver.getTitle();
		assertEquals(title, "Checkout");
		driver.navigate().back();	
		
	}
	@AfterClass
	public void close() {
		driver.quit();
	}
}
//You have no items in your shopping cart.
