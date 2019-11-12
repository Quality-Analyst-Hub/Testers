import code_repo.path;
import code_repo.repo_base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class signup_script extends repo_base {

	@BeforeSuite
	public void initialize() throws Exception {
		open_browser(ph.browsername, ph.URL);
		Thread.sleep(1000);
		click_element(ph.login);
		click_element(ph.register);
		validate("Create New Customer Account");
	}

	// To check Blank Data
	@Test(priority = 1, description = "test case with blank data")
	public void with_blank_fields() throws Exception {
		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"This is a required field.", "This is a required field.", "This is a required field.",
				"This is a required field.", "Please select an option.", "This is a required field.",
				"This is a required field.", "This is a required field.", "This is a required field." };
		click_element(ph.signup);
		assertmultivalidate(ph.err, expectedmessage);
	}

	// To check Invalid Phone Number
	@Test(priority = 2, description = "test case with blank data")
	public void Phone_no() throws Exception {
		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"Please enter a valid phone number. For example (123) 456-7890 or 123-456-7890 or +1-541-754-3010.",
				"This is a required field.", "This is a required field.", "This is a required field.",
				"Please select an option.", "This is a required field.", "This is a required field.",
				"This is a required field.", "This is a required field." };
		insertdata(ph.phone, "12345");
		click_element(ph.signup);
		assertmultivalidate(ph.err, expectedmessage);
	}

	// To check validation message for wrong emailid.
	@Test(priority = 3, description = "test case with invalid email details.")
	public void invalid_email() throws Exception {
		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"Please enter a valid phone number. For example (123) 456-7890 or 123-456-7890 or +1-541-754-3010.",
				"This is a required field.", "This is a required field.", "This is a required field.",
				"Please select an option.", "This is a required field.",
				"Please enter a valid email address (Ex: johndoe@domain.com).", "This is a required field.",
				"This is a required field." };
		insertdata(ph.email, "nagar");
		click_element(ph.signup);
		assertmultivalidate(ph.err, expectedmessage);
	}

	// To check Invalid password message
	@Test(priority = 4, description = "test case with invalid password details.")
	public void invalid_password() throws Exception {

		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"Please enter a valid phone number. For example (123) 456-7890 or 123-456-7890 or +1-541-754-3010.",
				"This is a required field.", "This is a required field.", "This is a required field.",
				"Please select an option.", "This is a required field.",
				"Please enter a valid email address (Ex: johndoe@domain.com).",
				"Your password must be at least 8 characters long and contain at least one number,one special symbol and have a mixture of uppercase and lowercase letters.",
				"This is a required field." };
		insertdata(ph.reg_pass, "1234");
		click_element(ph.signup);
		assertmultivalidate(ph.err, expectedmessage);
	}

	// To check confirm password length and password match
	@Test(priority = 5, description = "test case for confirm password details.")
	public void confirm_password() throws Exception {

		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"Please enter a valid phone number. For example (123) 456-7890 or 123-456-7890 or +1-541-754-3010.",
				"This is a required field.", "This is a required field.", "This is a required field.",
				"Please select an option.", "This is a required field.",
				"Please enter a valid email address (Ex: johndoe@domain.com).", "Please enter the same value again." };
		insertdata(ph.reg_pass, "amit1234@");
		insertdata(ph.reg_cpass, "1234@");
		click_element(ph.signup);
		assertmultivalidate(ph.err, expectedmessage);
	}

	@Test(priority = 6, description = "test case for Fax No details.")
	public void fax_no() throws Exception {

		String[] expectedmessage = { "This is a required field.", "This is a required field.",
				"Please enter a valid phone number. For example (123) 456-7890 or 123-456-7890 or +1-541-754-3010.",
				"Please enter a valid fax number. For example (123) 456-7890 or 123-456-7890 or +1-541-754-3010.",
				"This is a required field.", "This is a required field.", "Please select an option.",
				"This is a required field.", "Please enter a valid email address (Ex: johndoe@domain.com).",
				"Please enter the same value again." };
		insertdata(ph.fax, "amit1234");
		click_element(ph.signup);
		assertmultivalidate(ph.err, expectedmessage);
	}

	@Test(priority = 7, description = "test case for Already registered email id details.")
	public void registered_email() throws Exception {

		String[] expectedmessage = {"There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account."};
		insertdata(ph.firstname, "Amit");
		insertdata(ph.lastname, "Agarwal");
		insertdata(ph.phone, "6549873210");
		insertdata(ph.fax, "6549873210");
		insertdata(ph.address, "Jaipur");
		insertdata(ph.city, "Ajmer");
		select_data(ph.country_dropdown, "India");
		select_data(ph.drop_down, "Rajasthan");
		insertdata(ph.pincode, "302002");
		insertdata(ph.email, "amitfullestop01@gmail.com");
		insertdata(ph.reg_pass, "amit1234@");
		insertdata(ph.reg_cpass, "amit1234@");
		click_element(ph.signup);
		assertmultivalidate(ph.errormsg, expectedmessage);
		// assertmultivalidate(ph.err, expectedmessage);

		//
	}
	
	
	@Test(priority = 8, description = "test case for Valid data details.")
	public void valid_data() throws Exception {

		String[] expectedmessage = {"You must confirm your account. Please check your email for the confirmation link or click here for a new link."};
		insertdata(ph.firstname, "Amit");
		insertdata(ph.lastname, "Agarwal");
		insertdata(ph.phone, "6549873210");
		insertdata(ph.fax, "6549873210");
		insertdata(ph.address, "Jaipur");
		insertdata(ph.city, "Ajmer");
		select_data(ph.country_dropdown, "India");
		select_data(ph.drop_down, "Rajasthan");
		insertdata(ph.pincode, "302002");
		insertdata(ph.email, "amifullstop@mailinator.com");
		insertdata(ph.reg_pass, "amit1234@");
		insertdata(ph.reg_cpass, "amit1234@");
		click_element(ph.signup);
		assertmultivalidate(ph.success_message, expectedmessage);
		// assertmultivalidate(ph.err, expectedmessage);

		//
	}

	
}
