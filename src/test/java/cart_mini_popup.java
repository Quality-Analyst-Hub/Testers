import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import code_repo.repo_base;

public class cart_mini_popup extends repo_base {
	boolean pro;

	@Test(priority = 1)
	public void open_empty_cart_popup() throws Exception {
		/*open_browser(ph.browsername, ph.URL);
		click_element(ph.login);
		insertdata(ph.username, "amitfullestop01@gmail.com");
		insertdata(ph.password, "amit1234@");
		click_element(ph.signin);
		Thread.sleep(10000);*/
		click_element(By.xpath("//div[@class='minicart-wrapper']"));
		String display = driver.findElement(By.xpath(
				"//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front mage-dropdown-dialog']"))
				.getCssValue("display");
		assertEquals(display, "block");
		Thread.sleep(2000);
	}

	@Test(priority = 2)
	public void validate_empty_cart_popup() throws Exception {
		String[] expectedmessage = { "You have no items in your shopping cart." };
		boolean exist = driver.findElements(By.xpath(
				"//div[@id='minicart-content-wrapper']/div[@class='block-content']//strong[@class='subtitle empty']"))
				.size() != 0;
		if (exist) {
			By pass = By.xpath("//div[@id='minicart-content-wrapper']/div[@class='block-content']/strong");
			assertmultivalidate(pass, expectedmessage);
		} else {
			pro = true;
		}
	}

	@Test(priority = 3)
	public void validate_products_in_cart_popup() throws Exception {
		if (pro) {
			WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
			List<WebElement> list = ele.findElements(By.xpath("//li[@data-role='product-item']"));
			int size = list.size();
			System.out.println(size);
			String items = ele.findElement(By.xpath("//div[@class='items-total']")).getText().split(" ")[0].trim();
			int item = Integer.parseInt(items);
			System.out.println(item);
			Thread.sleep(3000);
			assertEquals(item, size);
		} else {
			System.out.println("tata");
		}
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
		System.out.println(item);
		Thread.sleep(3000);
		assertEquals(item, total_price);
	}

	@Test(priority = 5)
	public void update_product_quantity_in_cart_popup() throws Exception {
		String name = "Girls Frilled Frock";
		String qnty = "6";
		String size = "140";
		String color = "Red";
		String product_size;
		String product_color;
		String message, message2;
		if (!size.equals("") || !color.equals("")) {
			message2 = "We don't have as many \"" + name + "-" + size + "-" + color + "\" as you requested.";
			message = "We don't have as many \"" + name + "-" + color + "-" + size + "\" as you requested.";
		} else {
			message = "We don't have as many \"" + name + "\" as you requested.";
		}
		WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
		List<WebElement> list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
		for (int i = 0; i < list.size(); i++) {
			String product_name = list.get(i).findElement(By.tagName("strong")).getText();
			boolean product_info = list.get(i).findElements(By.xpath("//span[contains(text(),'See Details')]"))
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
			if (product_name.equalsIgnoreCase(name)) {
				String product_qty = list.get(i).findElement(By.tagName("input")).getAttribute("data-item-qty");
				if (product_qty != qnty) {
					list.get(i).findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), qnty);
					list.get(i).findElement(By.className("update-cart-item")).click();
					Thread.sleep(2000);
					boolean exist = driver.findElements(By.xpath("//aside[contains(@class,'_show')]")).size() != 0;
					if (exist) {
						model_box_manage(message);
						click_element(By.xpath("//div[@class='minicart-wrapper']"));
						Thread.sleep(5500);
					} else {
						Thread.sleep(2000);
						list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
						String qty = list.get(i).findElement(By.tagName("input")).getAttribute("data-item-qty");
						assertEquals(qty, qnty);
						// assertmultivalidate(ph.success_message, expectedmessage);
					}
				}
				break;
			}
		}
	}

	@Test(priority = 6)
	public void delete_product_from_cart_popup() throws Exception {
		String name = "Stylish Top";
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
				List<WebElement> list2 = list.get(i)
						.findElements(By.xpath("//div[@class='product actions']//div[@class='secondary']"));
				list2.size();
				list2.get(i).findElement(By.tagName("a")).click();
				model_box_manage(expmessage);
				Thread.sleep(5000);
				click_element(By.xpath("//div[@class='minicart-wrapper']"));
				exist = driver
						.findElements(By
								.xpath("//div[@class='minicart-items-wrapper overflowed']/ol[@class='minicart-items']"))
						.size() != 0;
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
		String home = driver.getCurrentUrl();
		driver.findElement(By.xpath("//button[@id='top-cart-btn-checkout']")).click();
		String title = driver.getTitle();
		System.out.println(title);
		Thread.sleep(6000);
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.ALT, Keys.ARROW_LEFT);
		if (title.contains("out")) {
			assertEquals(title, "Checkout");
			// driver.navigate().back();
			driver.get(home);

		} else {
			assertTrue(false);
		}
	}

	@Test(priority = 8)
	public void view_shopping_cart() throws Exception {
		Thread.sleep(200);
		String element = driver.findElement(By.xpath("//div[@role='dialog']")).getCssValue("display");
		if (element.equalsIgnoreCase("none")) {
			click_element(By.xpath("//div[@class='minicart-wrapper']"));
			Thread.sleep(500);
		}
		boolean exist = driver.findElements(By.xpath("//div[@id='minicart-content-wrapper']")).size() != 0;
		if (exist) {
			WebElement ele = driver.findElement(By.xpath("//div[@id='minicart-content-wrapper']"));
			List<WebElement> list = ele.findElements(By.xpath("//div[@class='product-item-details']"));
			//float total_price = 0.0f;
			for (int i = 0; i < list.size(); i++) {
				String product_name = list.get(i).findElement(By.tagName("strong")).getText();
				hash_put(i + "product_name", product_name);
				boolean product_info = list.get(i).findElements(By.xpath("//div[contains(@class,'product options')]"))
						.size() != 0;
				if (product_info) {
					list.get(i).findElement(By.xpath("//div[contains(@class,'product options')]")).click();
					String pro_details = list.get(i)
							.findElement(By.xpath("//ul[@class='product options list struct-opt']")).getText();
					String pro_size = pro_details.split("\n")[0];
					String pro_color = pro_details.split("\n")[1];
					hash_put(product_name + "_size", pro_size.split(" ")[1]);
					hash_put(product_name + "_place", pro_color.split(" ")[1]);
				}
				/*String product_price = list.get(i).findElement(By.tagName("span")).getText().split(" ")[1].trim();
				String product_qty = list.get(i).findElement(By.tagName("input")).getAttribute("data-item-qty");
				int quantity = Integer.parseInt(product_qty);
				float item_price = Float.parseFloat(product_price.replaceAll(",", ""));
				total_price += (quantity * item_price);*/
			}
			//tab.put("total_amount", total_price);
			driver.findElement(By.xpath("//a[contains(@class,'action viewcart')]")).click();
//			for (String winHandle : driver.getWindowHandles()) { switchTo().window(winHandle).
			String title = driver.getTitle();
			System.out.println(title);
			if (title.contains("Cart")) {
				assertEquals(title, "Shopping Cart");
			}
		}
	}

	
}
