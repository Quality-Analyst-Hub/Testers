import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import code_repo.repo_base;

public class cart_popup extends repo_base {
	
	@Test(priority = 1)
	public void open_empty_cart_popup() throws Exception {
		open_browser(ph.browsername, ph.URL);
		click_element(ph.login);
		insertdata(ph.username, "amitfullestop01@gmail.com");
		insertdata(ph.password, "amit1234@");
		click_element(ph.signin);
		click_element(By.xpath("//a[@class='navbar-brand']//img"));
		Thread.sleep(1000);
		click_element(By.xpath("//div[@class='minicart-wrapper']"));
		String display = driver.findElement(By.xpath(
				"//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front mage-dropdown-dialog']"))
				.getCssValue("display");
		assertEquals(display, "block");
		Thread.sleep(3000);
	}

	@Test(priority = 2)
	public void validate_empty_cart_popup() throws Exception {
		String[] expectedmessage = { "You have no items in your shopping cart." };
		boolean exist = driver
				.findElements(By.xpath("//div[@id='minicart-content-wrapper']/div[@class='block-content']/strong"))
				.size() != 0;
		if (exist) {
			By pass = By.xpath("//div[@id='minicart-content-wrapper']/div[@class='block-content']/strong");
			assertmultivalidate(pass, expectedmessage);
		}
	}

	@Test(priority = 3)
	public void validate_products_in_cart_popup() throws Exception {
		WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
		List<WebElement> list = ele.findElements(By.xpath("//li[@data-role='product-item']"));
		int size = list.size();
		System.out.println(size);
		String items = ele.findElement(By.xpath("//div[@class='items-total']")).getText().split(" ")[0].trim();
		int item = Integer.parseInt(items);
		System.out.println(item);
		Thread.sleep(3000);
		assertEquals(item, size);
	}

	@Test(priority = 4)
	public void validate_products_amount_in_cart_popup() throws Exception {
		WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
		List<WebElement> list = ele.findElements(By.xpath("//div[@class='product-item-pricing']"));
		System.out.println(list.size());
		float total_price = 0.0f;
		for (int i = 0; i < list.size(); i++) {
			String product_price = list.get(i).findElement(By.tagName("span")).getText().split(" ")[1].trim();
			String product_qty = list.get(i).findElement(By.tagName("input")).getAttribute("data-item-qty");
			int quantity = Integer.parseInt(product_qty);
			float item_price = Float.parseFloat(product_price.replaceAll(",", ""));
			total_price += (quantity * item_price);
		}
		System.out.println(total_price);
		String price_txt = driver
				.findElement(By.xpath("//div[@class='amount price-container']//span[@class='price-wrapper']/span"))
				.getText().trim();
		System.out.println(price_txt);
		String price = price_txt.split(" ")[1].trim();
		float item = Float.parseFloat(price.replaceAll(",", ""));
		tab.put("total_amount", item);
		System.out.println(item);
		Thread.sleep(3000);
		assertEquals(item, total_price);
	}

	@Test(priority = 5)
	public void update_product_quantity_in_cart_popup() throws Exception {
		String name = "test product 11";
		String qnty = "5";
		String size = "150";
		String color = "Red";
		String product_size;
		String product_color;
		String message = "We don't have as many \"" + name + "-" + size + "-" + color + "\" as you requested.";
		WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
		List<WebElement> list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
		for (int i = 0; i < list.size(); i++) {
			String product_name = list.get(i).findElement(By.tagName("strong")).getText();
			boolean product_info = list.get(i).findElements(By.xpath("//div[contains(@class,'product options')]"))
					.size() != 0;
			if (product_info) {
				list.get(i).findElement(By.xpath("//div[contains(@class,'product options')]")).click();
				String pro_details = list.get(i).findElement(By.xpath("//ul[@class='product options list struct-opt']"))
						.getText();
				String pro_size = pro_details.split("\n")[0];
				String pro_color = pro_details.split("\n")[1];
				product_size = pro_size.split(" ")[1];
				product_color = pro_color.split(" ")[1];
			}
			if (product_name.equalsIgnoreCase(name) ) {
				String product_qty = list.get(i).findElement(By.tagName("input")).getAttribute("data-item-qty");
				if (product_qty != qnty) {
					list.get(i).findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), qnty);
					list.get(i).findElement(By.className("update-cart-item")).click();

					boolean exist = driver.findElements(By.xpath("//aside[contains(@class,'_show')]")).size() != 0;
					if (exist) {
						String a = model_box_manage(message);
						click_element(By.xpath("//div[@class='minicart-wrapper']"));
						Thread.sleep(500);
					} else {
						list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
						String qty = list.get(i).findElement(By.tagName("input")).getAttribute("data-item-qty");
						assertEquals(qty, qnty);
						// assertmultivalidate(ph.success_message, expectedmessage);
						break;
					}
				}
			}
		}
	}

	@Test(priority = 8)
	public void view_shopping_cart() throws Exception {
		Thread.sleep(200);
		boolean exist = driver.findElements(By.xpath("//div[@id='minicart-content-wrapper']")).size() != 0;
		if (exist) {
			String parent = driver.getWindowHandle();
			WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
			List<WebElement> list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
			for (int i = 0; i < list.size(); i++) {
				String product_name = list.get(i).findElement(By.tagName("strong")).getText();
				hash_put(i+"product_name", product_name);
				boolean product_info = list.get(i).findElements(By.xpath("//div[contains(@class,'product options')]"))
						.size() != 0;
				if (product_info) {
					list.get(i).findElement(By.xpath("//div[contains(@class,'product options')]")).click();
					String pro_details = list.get(i).findElement(By.xpath("//ul[@class='product options list struct-opt']"))
							.getText();
					String pro_size = pro_details.split("\n")[0];
					String pro_color = pro_details.split("\n")[1];
					hash_put(product_name+"_size", pro_size.split(" ")[1]);
					hash_put(product_name+"_place",  pro_color.split(" ")[1]);
				}
			}
			
			driver.findElement(By.xpath("//a[contains(@class,'action viewcart')]")).sendKeys(kc.newtab);
			for (String winHandle : driver.getWindowHandles()) {
				String title = driver.switchTo().window(winHandle).getTitle();
				System.out.println(title);
				if (title.contains("Cart")) {
					assertEquals(title, "Shopping Cart");
					break;
				}
			}
			/*Thread.sleep(1000);
			driver.close();
			driver.switchTo().window(parent);*/
		}
	}

	@Test(priority = 6)
	public void delete_product_from_cart_popup() throws Exception {
		String name = "tshirts for girls combo of 4";
		String expmessage = "Are you sure you would like to remove this item from the shopping cart?";
		boolean exist = driver
				.findElements(By.xpath("//div[@class='minicart-items-wrapper overflowed']/ol[@class='minicart-items']"))
				.size() != 0;
		System.out.println("boolean " + exist);
		if (!exist) {
			click_element(By.xpath("//div[@class='minicart-wrapper']"));
			Thread.sleep(500);
		}
		WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
		List<WebElement> list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
		int list_size = list.size();
		for (int i = 0; i < list.size(); i++) {
			String product_name = list.get(i).findElement(By.tagName("strong")).getText();
			if (product_name.equalsIgnoreCase(name)) {
				List<WebElement> list2 = list.get(i).findElements(By.xpath("//div[@class='product actions']//div[@class='secondary']"));
				list2.get(i).findElement(By.tagName("a")).click();
				String a = model_box_manage(expmessage);
				Thread.sleep(9000);
				if (exist) {
					List<WebElement> list0 = ele.findElements(By.xpath("//div[@class='product-item-details']"));
					int updatesize = list0.size();
					System.out.println(updatesize);
					assertTrue(updatesize < list_size);
				}
				break;
			}
		}
	}

	@Test(priority = 7)
	public void proceed_to_checkout() throws Exception {
		Thread.sleep(3000);
		String exist = driver.findElement(By.xpath("//div[@role='dialog']")).getCssValue("display");
		if (exist.equalsIgnoreCase("none")) {
			click_element(By.xpath("//div[@class='minicart-wrapper']"));
			Thread.sleep(500);
		}
		driver.findElement(By.xpath("//button[@id='top-cart-btn-checkout']")).click();
		String title = driver.getTitle();
		Thread.sleep(6000);
		driver.navigate().back();
		System.out.println(title);
		if (title.contains("out")) {
			assertEquals(title, "Checkout");
			
		} else {
			assertTrue(false);
		}
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.ALT,Keys.ARROW_LEFT);
		//driver.navigate().back();
//		String url = driver.getCurrentUrl();
//		driver.get(url);
		

	}

/*	@Test(priority = 8)
	public void close_cart_popup() throws Exception {
		click_element(By.xpath("//button[@id='btn-minicart-close']"));
		Thread.sleep(2000);
		String display = driver.findElement(By.xpath("//div[@role='dialog']")).getCssValue("display");
		assertEquals(display, "none");
	}
*/
	
}
//The coupon code "dis100" is not valid.
//Cart has been updated successfully.
