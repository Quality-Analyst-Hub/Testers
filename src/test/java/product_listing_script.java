import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import code_repo.repo_base;

public class product_listing_script extends repo_base{
	
	ArrayList arr = new ArrayList();
	ArrayList product_price = new ArrayList();
	
	@Test(priority=1, description = "test case for sort product list")
	public void sort_data() throws Exception {
		
		Thread.sleep(1000);
		click_element(ph.product_open);
		String sortBy = "Price - High To Low";
		Thread.sleep(500);
		ArrayList Final_product_price = new ArrayList();
		select_data(ph.sort_by, sortBy);
		WebElement element1 = driver.findElement(By.xpath(
				"//div[@class='listing-right']/div[@class=' list-product products wrapper grid products-grid']/ul"));
		int count2 = element1.findElements(By.tagName("li")).size();
		System.out.println(count2);
		for (int j = 0; j < count2; j++) {
			String title = element1
					.findElements(By.xpath("//ul[@class='products list items product-items']/li/div/div/div/p")).get(j)
					.getText();
			String price = element1
					.findElements(By.xpath("//ul[@class='products list items product-items']/li/div/div/div/span[2]"))
					.get(j).getText();
			arr.add(title);
		//	product_price.add(price);
			String rep_price = price.replaceAll("SAR ", "");
			String final_rep_price = rep_price.replaceAll(",", "");
			float lowerYear = Float.parseFloat(final_rep_price.split(" ")[0]);
			Final_product_price.add(lowerYear);
		}
		if(sortBy.contains("Low To High")) {
			sorting_validate(Final_product_price, "assending");	
		}
		else if(sortBy.contains("High To Low")) {
			sorting_validate(Final_product_price, "decending");	
		}
		
		
	}

}
