import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import code_repo.repo_base;

public class shopping_cart extends repo_base{
	ArrayList<String> products = new ArrayList<String>();
	
	
	@Test(priority=1)
	public void validate_products() throws Exception {
		Hashtable tab = getdataMap();
		WebElement ele = driver.findElement(By.xpath("//div[contains(@class,'cart-left')]"));
		List<WebElement> list = ele.findElements(By.xpath("//div[contains(@class,'item-detail')]"));
		List<WebElement> total_price = ele.findElements(By.xpath("//td[@class='price cart-item col subtotal']"));
		int size = list.size()-1;
		for(int i=0;i<list.size();i++) {
			System.out.println(tab.get(size+"product_name"));
			String product_name = list.get(i).findElement(By.tagName("a")).getText();
			products.add(product_name);
			String pro_size = total_price.get(i).findElement(By.tagName("span")).getText();
			hash_put(product_name + "_total", pro_size.split(" ")[1]);
			//td[@class='price cart-item col subtotal']//span[@class='price']
			System.out.println(products.get(i));
			assertEquals(products.get(i), tab.get(size+"product_name"));
			size--;
		}
	}
	
	@Test(priority=2)
	public void discount_invalid() throws Exception {
		String[] expectedmessage =  {"The coupon code \"dis100\" is not valid."};
		String[] cancelcouponmessage =  {"You canceled the coupon code."};
		boolean data = driver.findElement(By.xpath("//input[@id='coupon_code']")).getText() !=null;
		if(data) {
			driver.findElement(By.xpath("//button[contains(@class,'apply-btn')]")).click();
			assertmultivalidate(ph.success_message,cancelcouponmessage);
			Thread.sleep(3000);
		}
		driver.findElement(By.xpath("//input[@id='coupon_code']")).sendKeys("dis100");
		driver.findElement(By.xpath("//button[contains(@class,'apply-btn')]")).click();
		assertmultivalidate(ph.errormsg,expectedmessage);
	}
	
	@Test(priority=3)
	public void discount_valid() throws Exception {
		String discount = "dis101";
		String[] expectedmessage =  {"You have successfully applied coupon code \""+discount+"\"."};
		driver.findElement(By.xpath("//input[@id='coupon_code']")).sendKeys(discount);
		driver.findElement(By.xpath("//button[contains(@class,'apply-btn')]")).click();
		assertmultivalidate(ph.success_message,expectedmessage);
		
	}
	
	@Test(priority=4)
	public void update_cart() throws Exception {
		String[] expectedmessage =  {"Cart has been updated successfully."};
		driver.findElement(By.xpath("//button[contains(text(),'Update Cart')]")).click();
		assertmultivalidate(ph.success_message,expectedmessage);
	}

	@Test(priority=5)
	public void delete_product() throws Exception {
		String name = "Frilled Frock";
		String expmessage = "Are you sure you would like to remove this item from the shopping cart?";
		WebElement ele = driver.findElement(By.xpath("//div[contains(@class,'cart-left')]"));
		List<WebElement> list = ele.findElements(By.xpath("//a[contains(@class,'cross-icon')]"));
		int list_size = list.size();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(products.get(i));
			if (products.get(i).equalsIgnoreCase(name)) {
				
				list.get(i).findElement(By.tagName("img")).click();
				model_box_manage(expmessage);
				Thread.sleep(9000);
				boolean exist = driver.findElements(By.xpath("//div[contains(@class,'item-detail')]")).size() != 0;
				if (exist) {
					List<WebElement> list2 = driver.findElements(By.xpath("//a[contains(@class,'cross-icon')]//img"));
					int updatesize = list2.size();
					System.out.println(updatesize);
					assertTrue(updatesize < list_size);
				}
				break;
			}
		}
	}
	
	@Test(priority=6)
	public void order_total() throws Exception {
		
		WebElement ele1 = driver.findElement(By.xpath("//div[contains(@class,'cart-left')]"));
		List<WebElement> list = ele1.findElements(By.xpath("//td[@class='price cart-item col subtotal']"));
		float amount = 0.0f;
		for(int i=0;i<list.size();i++) {
			String pro_amount = list.get(i).findElement(By.tagName("span")).getText().split(" ")[1].trim();
			amount += Float.parseFloat(pro_amount.replaceAll(",", ""));
		}
		
		WebElement ele = driver.findElement(By.xpath("//div[contains(@class,'cart-right')]"));
		list = ele.findElements(By.xpath("//div[contains(@class,'cart-totals')]"));
		list.get(0).findElement(By.xpath("//div[@id='headingTwo']")).click();
		Thread.sleep(6000);
		String total_amount = ele.findElement(By.xpath("//tr[@class='totals sub']/td[@class='amount']")).getText().split(" ")[1].trim();
		System.out.println(total_amount);
		float totalamount = Float.parseFloat(total_amount.replaceAll(",", ""));
		assertEquals(totalamount, amount);
		
	}
	
	@Test(priority = 7)
	public void proceed_to_checkout() throws Exception {
		Thread.sleep(3000);
		scroll();
		WebElement ele = driver.findElement(By.xpath("//div[contains(@class,'cart-right')]"));
		ele.findElement(By.xpath("//span[contains(text(),'Proceed to Checkout')]")).click();
		String title = driver.getTitle();
		System.out.println(title);
		if (title.contains("out")) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@id='shipping-method-buttons-container']")));
			assertEquals(title, "Checkout");
			Thread.sleep(1000);
		} else {
			assertTrue(false);
		}
		//driver.navigate().back();
		Thread.sleep(5000);
	}
	
	/*@Test(priority=8)
	public void clear_cart() throws Exception {
		String[] expectedmessage =  {"Clear cart successfully."};
		driver.findElement(By.xpath("//button[contains(text(),'Clear Cart')]")).click();
		String expmessage = "Are you sure you would like to remove this item from the shopping cart?";
		model_box_manage(expmessage);
		Thread.sleep(2000);
		assertmultivalidate(ph.success_message,expectedmessage);
		boolean exist = driver.findElements(By.xpath("//div[@class='cart-empty']")).size() != 0 ;
		assertTrue(exist);
	}*/
	
	@AfterSuite
	public void close() {
		driver.quit();
	}
}
//