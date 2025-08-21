package com.orangehrm.pim.addemployee;

import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PIMAddEmployeePage {

	private Page page = null;
	
	private String addEmployeeHeaderTextByXpath = "//h6[text()='Add Employee']";
	private String firstNameLocator = "input[name='firstName']";
	private String middleNameLocator = "input[name='middleName']";
	private String lastNameLocator = "input[name='lastName']";
	private String employeeIdLocator = "input.oxd-input--active";
	private String toggleSwitch = "span.oxd-switch-input";
	// These are locators for add PIM employee
	private String usernameEl = "//input[contains(@class,'oxd-input') and contains(@class,'oxd-input--active')]";
	private String passwordEl = "//input[@type='password' and contains(@class,'oxd-input')]";
	private String confirmPassword = "input[type='password'].oxd-input.oxd-input--active";
	private String statusEnabled = "input[type='radio'][value='1']";
	private String statusDisabled = "input[type='radio'][value='2']";
	
	Locator switchToggle = page.locator("span.oxd-switch-input");
    boolean isActive = switchToggle.getAttribute("class").contains("oxd-switch-input--active");
	
	private String employeeId = "input.oxd-input--error";
	private String saveButton = "button[type='submit']";
	private String savedEmployee = "h6.oxd-text.oxd-text--h6.--strong";
	
	
	public PIMAddEmployeePage(Page page) {
		this.page = page;
	}
	
	public String getAddEmployeeURL() {
		return page.url();
	}
	
	public String getAddEmployeeHeaderText() {
		return page.locator(addEmployeeHeaderTextByXpath).textContent();
	}
	
	public void setEmployeeStatus(String status) {
		
		switch(status) {
		case "Enabled":
			page.locator(statusEnabled).check();
			break;
		case "Disabled":
			page.locator(statusDisabled).check();
			default:
				throw new IllegalArgumentException("Invalid status: " + status);	
		}
	}
	
	public void createLoginDetails(
			String username,
			String password,
			String confirmPass,
			String status
			) {
		page.locator(usernameEl).fill(username);
		page.locator(passwordEl).fill(password);
		page.locator(confirmPassword).fill(confirmPass);
		//page.locator(employeeId).fill(id);
		setEmployeeStatus(status);
		//page.locator(saveButton).click();
		
	}
	
//	public void setSwitchStateCreateLoginDetails(boolean enable) {
//		Locator switchToggle = page.locator("span.oxd-switch-input");
//	    boolean isActive = switchToggle.getAttribute("class").contains("oxd-switch-input--active");
//	    if (enable && !isActive) {
//	        switchToggle.click(); // turn ON
//	    } else if (!enable && isActive) {
//	        switchToggle.click(); // turn OFF
//	    }
//	}
	
	public void addEmployee(
			String firstName,
			String middleName,
			String lastName,
			boolean createLoginDetailsEnabled,
			String uname,
			String password,
			String confirmPass,
			String status) {
		String number = String.valueOf(getEmployeeId());
		page.locator(firstNameLocator).fill(firstName);
		page.locator(middleNameLocator).fill(lastName);
		page.locator(lastNameLocator).fill(lastName);
		page.locator(employeeIdLocator).fill(number);
		if(createLoginDetailsEnabled && !isActive) {
			switchToggle.click(); // turn ON
			createLoginDetails(uname,password,confirmPass,status);
			page.locator(saveButton);
		} else {
			page.locator(saveButton);
		}
	}
	
	public String getAddedEmployeeText() {
		return page.locator(savedEmployee).textContent();
	}
	
	public int getEmployeeId() {
		Random rand = new Random();
		return rand.nextInt(300); 
	}
}
