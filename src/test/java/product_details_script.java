import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.gargoylesoftware.css.parser.Locatable;

import code_repo.repo_base;

public class product_details_script extends repo_base {

	public String product_name;

	@Test(priority = 1)
	public void open_product_details() throws Exception {
		WebElement ele = driver.findElement(By.xpath("//ul[@class='products list items product-items']/li"));
		List<WebElement> list = ele.findElements(By.xpath("//div//div//div//p"));
		System.out.println(list.size());
		int i=0;
		while(i<list.size()) {
			product_name = list.get(i).getText();
			if(product_name.contains("Girls Frilled Frock")) {
				break;
			}
			i++;
		}
		list.get(i).click();
		Thread.sleep(2000);
	}
	
	// without selecting option validate error.
	@Test(priority = 2)
	public void product_add() throws Exception {
		String[] expectedmessage = { "This is a required field.", "This is a required field." };
		Thread.sleep(3000);
		String product = driver.findElement(By.xpath("//div[@class='product-info-main rightpart']/h1")).getText();
		assertTrue(product_name.equalsIgnoreCase(product));
		boolean exists = driver.findElements(By.xpath("//div[@id='product-options-wrapper']")).size() != 0;
		if (exists) {
			click_element(By.xpath("//button[@id='product-addtocart-button']"));
			Thread.sleep(800);
			assertmultivalidate(ph.err, expectedmessage);
		}
	}

	// select the options tag
	@Test(priority = 3)
	public void product_options() throws Exception {
		insertdata(ph.product_pincode, "302002");
		String psize = "140";
		String pcolor = "red";
		boolean exists = driver.findElements(By.xpath("//div[@id='product-options-wrapper']")).size() != 0;
		if (exists) {
			WebElement ele = driver.findElement(
					By.xpath("//div[@class='swatch-attribute size']/div[@class='swatch-attribute-options clearfix']"));
			List<WebElement> li = ele.findElements(By.xpath("//div[@class='swatch-option text']"));
			System.out.println(li.size());
			select_data_from_list(li, psize, "size");
			Thread.sleep(1200);
			WebElement ele2 = driver.findElement(By
					.xpath("//div[@class='swatch-attribute color']//div[@class='swatch-attribute-options clearfix']"));
			List<WebElement> li2 = ele2.findElements(By.xpath("//div[@class='swatch-option color']"));
			System.out.println(li2.size());
			select_data_from_list(li2, pcolor, "color");
		}
	}

	// validate the quantity error message.
	@Test(priority = 4)
	public void validate_qty() throws Exception {
		String[] expectedmessage = { "Please enter a value less than or equal to 9" };
		insertdata(ph.quantity, "65");
		assertmultivalidate(ph.quantity_err, expectedmessage);
		Thread.sleep(2000);
	}

	// insert the desire quantity using buttons.
	@Test(priority = 5)
	public void insert_qyt() throws Exception {
		int qty = 2;
		int qt = Integer.parseInt(driver.findElement(ph.quantity).getAttribute("value"));
		System.out.println(qt);
		if (qt < qty) {
			for (int i = qt + 1; i <= qty; i++) {
				click_element(ph.quantity_plus);
				Thread.sleep(500);
			}
		} else {
			for (int i = qt; i > qty; i--) {
				click_element(ph.quantity_minus);
				Thread.sleep(500);
			}
		}
	}

	// click on add to cart
	@Test(priority = 6)
	public void add_product() throws Exception {
		int cart_item = Integer
				.parseInt(driver.findElement(By.xpath("//a[@class='action showcart']/span/b")).getText());
		String[] expectedmessage = { "You have successfully added " + product_name + " to your shopping cart." };
		Thread.sleep(1000);
		click_element(By.xpath("//button[@id='product-addtocart-button']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-ui-id='message-success']")));
		Thread.sleep(800);
		assertmultivalidate(ph.success_message, expectedmessage);
		int update_cart_item = Integer
				.parseInt(driver.findElement(By.xpath("//a[@class='action showcart']/span/b")).getText());
		Assert.assertTrue(update_cart_item > cart_item);
	}

	// click on wishlist
	@Test(priority = 7)
	public void wishList() throws Exception {
		String[] expectedmessage = {
				product_name + " has been added to your Wish List. Click here to continue shopping." };
		Thread.sleep(1000);
		click_element(By.xpath("//a[@class='custom_action add-wishlist wishlist-on-image']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-ui-id='message-success']")));
		assertmultivalidate(ph.success_message, expectedmessage);
		driver.navigate().back();
	}

	// clicking the all tabs
	@Test(priority = 8)
	public void tab_intend() throws Exception {
		Thread.sleep(1000);
		List<WebElement> li = driver.findElements(By.xpath("//ul[@class='resp-tabs-list nav nav-tabs']/li"));
		int rating = 4;
		for (int i = 0; i < li.size(); i++) {
			li.get(i).click();
			Thread.sleep(500);
		}
	}

	// validate the review error message.
	@Test(priority = 9)
	public void review_blank() throws Exception {
		scroll();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@class='resp-tabs-list nav nav-tabs']//li[@id='tab-label-reviews']")).click();
		Thread.sleep(2000);
		boolean exist = driver
				.findElements(By
						.xpath("//div[@class='resp-tabs-container']/div[@class='message notlogged']"))
				.size() != 0;
		if (exist) {
			String contant = driver
					.findElement(By.xpath(
							"//div[@class='resp-tabs-container']/div[@class='reviews itemsdata test-review-form']"))
					.findElement(By.tagName("p")).getText();
			System.out.println(contant);
			if (contant.contains("Only registered users can write reviews")) {
				click_element(By.linkText("Sign in"));
				Thread.sleep(1000);
				insertdata(ph.username, "amitfullestop01@gmail.com");
				insertdata(ph.password, "amit1234@");
				click_element(ph.remembar);
				click_element(ph.signin);
				Thread.sleep(1000);
			}
		}
		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"Please select the ratings above." };
		Thread.sleep(2000);
		click_element(By.xpath("//button[@class='btn-global block-btn submit primary']"));
		assertmultivalidate(ph.err, expectedmessage);
	}

	// make an product review and validate it.
	@Test(priority = 10)
	public void review() throws Exception {
		String[] expectedmessage = { "Review has been submitted for moderation." };
		insertdata(By.xpath("//input[@id='summary_field']"), "Nyc Product");
		insertdata(By.xpath("//textarea[@id='review_field']"), "Nyc Product");
		WebElement star = driver
				.findElement(By.xpath("//div[@class='control review-control-vote']//label[@id='Ratings_1_label']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", star);
		click_element(By.xpath("//button[@class='btn-global block-btn submit primary']"));
		Thread.sleep(2000);
		assertmultivalidate(ph.success_message, expectedmessage);
		Thread.sleep(3000);
	}
	
	
}
