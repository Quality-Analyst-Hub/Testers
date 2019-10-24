package code_repo;

import org.openqa.selenium.By;

//Xpath of http://senskids.mag2.demo321.com/test/ elements.
public class path {

	public String browsername = "Chrome";
	public String URL = "http://senskids.mag2.demo321.com/test/";
	public String xls_file = "F:\\Text.xlsx";
	public String sheet = "Sheet1";

// login page xpath
	public By login = By.xpath("//li[@class='user']/a");
	public By register = By.xpath("//a[@class='action create primary']");
	public By username = By.xpath("//input[@id='email dynamic-label-input']");
	public By password = By.xpath("//input[@id='pass dynamic-label-input']");
	public By signin = By.xpath("//fieldset[@class='fieldset login']//button[@id='send2']");
	public By remembar = By.xpath("//input[@id='login_remember']");
	public By errormsg = By.xpath("//div[@class='message-error error message hideMe']/div");
	public By success_message = By.xpath("//div[@class='message-success success message hideMe']/div");
	public By err = By.xpath("//div[@class='mage-error']");
	public By forget = By.xpath("//a[@class='action remind']");
	public By forget_email = By.xpath("//input[@id='email_address']");
	public By forget_reset_button = By.xpath("//button[@class='action submit primary btn']");
	// You did not sign in correctly or your account is temporarily disabled.
	// public By myaccount = By.xpath("//a[@class='order']");

// registration page xpath
	public By firstname = By.xpath("//input[@id='firstname']");
	public By lastname = By.xpath("//input[@id='lastname']");
	public By phone = By.xpath("//input[@id='telephone']");
	public By email = By.xpath("//input[@id='email_address']");
	public By reg_pass = By.xpath("//input[@id='password']");
	public By reg_cpass = By.xpath("//input[@id='password-confirmation']");
	public By signup = By.xpath("//button[@class='action submit primary btn']");
	public By fax = By.xpath("//input[@id='fax']");
	public By address = By.xpath("//input[@id='street_1']");
	public By drop_down = By.xpath("//select[@id='region_id']");
	public By city = By.xpath("//input[@id='city']");
	public By country_dropdown = By.xpath("//select[@id='country']");
	public By pincode = By.xpath("//input[@id='zip']");

	// Header and footer section xpath
	public By newsletter = By.xpath("//input[@id='newsletter']");
	public By newsletter_button = By.xpath("//button[@class='action subscribe primary btn btn-outline-success']");

	public By footer = By.xpath("//div[@class='footer-link-quick']");
	public By header = By.xpath("//ul[@id='mega-menu-header-menu']");
	public By header_menu = By.xpath("//div[@class='header-menu-bar']");
	public By search_field = By.xpath("//input[@type='search']");
	public By search_btn = By.xpath("//button[@class='btn btn-primary cstm-pro-search-submit']");
	public By search_notfound = By.xpath("//p[@class='woocommerce-info']");
	public By category_dropdown = By.xpath("//select[@name='product_cat']");

	// product listing.
	public By product_open = By.xpath("//ul[contains(@class,'navbar-nav')]//a[contains(text(),'Girls')]");
	public By product = By.xpath(
			"//div[@class='product-event']/div[@id='myTabContent']/ul[@id='home_tab_1']/li[1]/div[1]/div[2]/a[1]");
	public By sort_by = By.xpath("//select[@id='sorter']");

	// product details.
	public By product_pincode = By.xpath("//input[@placeholder='Enter Your Pincode']");
	public By quantity_minus = By.xpath("//a[@class='increased change-qtys minus-price']");
	public By quantity_plus = By.xpath("//a[@class='increase decrease change-qtys plus-price']");
	public By quantity = By.xpath("//input[@id='qty']");
	public By quantity_err = By.xpath("//div[@id='qty-error-cls']");
}
