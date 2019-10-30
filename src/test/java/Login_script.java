import code_repo.path;
import code_repo.repo_base;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Login_script extends repo_base {
	
	// check validation When email id and password is blank.
	@Test(priority=1, description = "test case with blank data")
	public void Blank_data() throws Exception {
		click_element(ph.login);
		String[] expectedmessage = { "This is a required field.", "This is a required field." };
		click_element(ph.signin);
		assertmultivalidate(ph.err,expectedmessage);
		//assertvalid(ph.xls_file, ph.sheet, "Blank", ph.errormsg);
		}
	
	// check validation When valid email id and password is blank.
	@Test(priority=2, description = "test case with valid username and blank password")
	public void valid_username_and_blank_password() throws Exception {
		String[] expectedmessage = { "This is a required field." };
		insertdata(ph.username, "amitfullestop01@gmail.com");
		click_element(ph.signin);
		assertmultivalidate(ph.err,expectedmessage);
		//xls(ph.xls_file, ph.sheet, "Blank_Pass", ph.errormsg);
	}
	
	// check validation When Invalid email id and password is blank.
	@Test(priority=3, description = "test case with Invalid username and blank password")
	public void Invalid_username_and_blank_password() throws Exception {
		String[] expectedmessage = { "Please enter a valid email address (Ex: johndoe@domain.com).", "This is a required field." };
		insertdata(ph.username, "com");
		click_element(ph.signin);
		assertmultivalidate(ph.err,expectedmessage);
		//xls(ph.xls_file, ph.sheet, "worng_message", ph.errormsg);
	}
	
	// Check validation when email id is blank and password is filled.
	@Test(priority=4, description ="test case with blank username and valid password")
	public void blank_username_and_valid_password() throws Exception {
		String[] expectedmessage = { "This is a required field." };
		insertdata(ph.username, "");
		insertdata(ph.password, "amit1234@");
		click_element(ph.signin);
		assertmultivalidate(ph.err,expectedmessage);
		//xls(ph.xls_file, ph.sheet, "worng_email", ph.errormsg);
	}
	
	// check validation When Wrong email id and Wrong password.
	@Test(priority=5, description = "test case with Wrong username and wrong password")
	public void wrong_username_and_wrong_password() throws Exception {
		String[] expectedmessage = { "You did not sign in correctly or your account is temporarily disabled." };
		insertdata(ph.username, "sharma01@mailinator.com");
		insertdata(ph.password, "System@123sam1");
		click_element(ph.signin);
		assertmultivalidate(ph.errormsg,expectedmessage);
		//xls(ph.xls_file, ph.sheet, "worng_pass", ph.errormsg);
	}
	
	// check validation When Valid email id and Wrong password.
	@Test(priority=6, description = "test case with Valid username and wrong password")
	public void valid_username_and_wrong_password() throws Exception {
		String[] expectedmessage = { "You did not sign in correctly or your account is temporarily disabled." };
		insertdata(ph.username, "amitfullestop01@gmail.com");
		insertdata(ph.password, "System@123sam");
		click_element(ph.signin);
		assertmultivalidate(ph.errormsg,expectedmessage);
		//validate();
	}
	
	// check validation When Invalid email at forget_password page.
	@Test(priority=7, description = "test case with invalid email at forget password page")
	public void invalid_email_forget_password() throws Exception {
			String username = "amitfullestop01@gmail.com";
			String[] expectedmessage =  {"If there is an account associated with "+username+" you will receive an email with a link to reset your password."};
			String expectedmsg =  "Forgot Your Password";
			click_element(ph.forget);
			validate(expectedmsg);
			Thread.sleep(2000);
			insertdata(ph.forget_email, username);
			click_element(ph.forget_reset_button);
			Thread.sleep(2000);
			assertmultivalidate(ph.success_message,expectedmessage);
		}
	
	// check validation When Valid email id and Valid password.
	@Test(priority=8, description = "test case with valid username and valid password")
	public void valid_username_and_valid_password() throws Exception {
		String expectedmessage =  "My Account";
		insertdata(ph.username, "amitfullestop01@gmail.com");
		insertdata(ph.password, "amit1234@");
		click_element(ph.remembar);
		click_element(ph.signin);
		Thread.sleep(1000);
		validate(expectedmessage);
	}

}
