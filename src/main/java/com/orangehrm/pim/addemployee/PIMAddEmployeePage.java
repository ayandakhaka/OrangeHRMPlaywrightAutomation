package com.orangehrm.pim.addemployee;

import java.sql.SQLException;
import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PIMAddEmployeePage {

	private Page page = null;
	
	private String addEmployeeHeaderTextByXpath = "//h6[text()='Add Employee']";
	private String firstNameLocator = "//input[@placeholder='First Name']";
	private String middleNameLocator = "//input[@placeholder='Middle Name']";
	private String lastNameLocator = "//input[@placeholder='Last Name']";
	// These are locators for add PIM employee
	private String usernameEl = "//input[contains(@class,'oxd-input') and contains(@class,'oxd-input--active')]";
	private String passwordEl = "//input[@type='password' and contains(@class,'oxd-input')]";
	private String confirmPassword = "input[type='password'].oxd-input.oxd-input--active";
	private String statusEnabled = "input[type='radio'][value='1']";
	private String statusDisabled = "input[type='radio'][value='2']";
	
	private String saveButton = "button[type='submit']";
	private String savedEmployee = "h6:has-text('Khaka')";
	private String SwitchCreateLoginDetailsToggle = "span.oxd-switch-input";
	
	
	public PIMAddEmployeePage(Page page) {
		this.page = page;
	}
	
	public String getAddEmployeeURL() {
		return page.url();
	}
	
	public String getAddEmployeeHeaderText() {
		return page.locator(addEmployeeHeaderTextByXpath).textContent();
	}
	
	public void clickSwitchToggle() {
		page.locator(SwitchCreateLoginDetailsToggle);
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
		setEmployeeStatus(status);		
	}
	
	public void addEmployeeWithoutCreateLoginDetails(
			String firstName,
			String middleName,
			String lastName,
			int employeeId
			) throws SQLException 
	{
		page.locator(firstNameLocator).fill(firstName);
		page.locator(middleNameLocator).fill(middleName);
		page.locator(lastNameLocator).fill(lastName);
		// Wait and fill Employee ID
		Locator input = page.locator("div.oxd-input-group:has(label:has-text('Employee Id')) input");
		input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		input.fill("");
		System.out.println("Employee Id = " + String.valueOf(employeeId));
		input.fill(String.valueOf(employeeId));
		clickSaveOnAddEmployee();
	}
	
	public void setEmployeeWithCreateLoginDetails() {
		
	}
	
	public String getAddedEmployeeText() {
		System.out.println(page.locator(savedEmployee).textContent());
		return page.locator(savedEmployee).textContent();
	}
	
	public int getEmployeeId() {
		Random rand = new Random();
		return rand.nextInt(300); 
	}

	
	public void clickSaveOnAddEmployee() {
		page.locator(saveButton).click();
	}
}
