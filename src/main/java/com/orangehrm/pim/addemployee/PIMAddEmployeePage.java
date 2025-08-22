package com.orangehrm.pim.addemployee;

import java.sql.SQLException;
import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.orangehrm.database.Employee;
import com.orangehrm.database.OrangeHRMDataStore;

public class PIMAddEmployeePage {

	private Page page = null;
	
	private String addEmployeeHeaderTextByXpath = "//h6[text()='Add Employee']";
	private String firstNameLocator = "//input[@placeholder='First Name']";
	private String middleNameLocator = "//input[@placeholder='Middle Name']";
	private String lastNameLocator = "//input[@placeholder='Last Name']";
	private String employeeIdLocator = "input[name='employeeId']";
	private String toggleSwitch = "span.oxd-switch-input";
	// These are locators for add PIM employee
	private String usernameEl = "//input[contains(@class,'oxd-input') and contains(@class,'oxd-input--active')]";
	private String passwordEl = "//input[@type='password' and contains(@class,'oxd-input')]";
	private String confirmPassword = "input[type='password'].oxd-input.oxd-input--active";
	private String statusEnabled = "input[type='radio'][value='1']";
	private String statusDisabled = "input[type='radio'][value='2']";
	private String switchToggle = "label:has-text('Create Login Details') >> input";
	
	
	
	private String employeeId = "input.oxd-input--error";
	private String saveButton = "button[type='submit']";
	private String savedEmployee = "h6:has-text('Khaka')";
	private Employee employee;
	private OrangeHRMDataStore orangeHRMDataStore;
	
	
	public PIMAddEmployeePage(Page page) {
		this.page = page;
		employee = new Employee();
		orangeHRMDataStore = new OrangeHRMDataStore();
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
	
//	public void addEmployee(
//			String firstName,
//			String middleName,
//			String lastName,
//			boolean createLoginDetailsEnabled,
//			String uname,
//			String password,
//			String confirmPass,
//			String status) {
//		String employeeId = String.valueOf(getEmployeeId());
//		page.waitForSelector("//input[contains(@class, 'oxd-input') and contains(@class, 'oxd-input--active')]", new Page.WaitForSelectorOptions().setTimeout(10000));
//		page.locator(firstNameLocator).fill(firstName);
//		page.locator(middleNameLocator).fill(middleName);
//		page.locator(lastNameLocator).fill(lastName);
//		// Wait and fill Employee ID
//	    Locator empIdInput = page.locator("//input[contains(@class, 'oxd-input') and contains(@class, 'oxd-input--active')]");
//	    empIdInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
//	    empIdInput.fill(employeeId);
//		boolean isActive = page.locator(switchToggle).getAttribute("Class").contains("oxd-switch-input--active");
//		if(createLoginDetailsEnabled && !isActive) {
//			page.locator(switchToggle).click(); // turn ON
//			createLoginDetails(uname,password,confirmPass,status);
//			page.locator(saveButton);
//		} else {
//			page.locator(saveButton);
//		}
//	}
	
	public void addEmployeeWithoutCreateLoginDetails(
			String firstName,
			String middleName,
			String lastName
			) throws SQLException 
	{
		page.locator(firstNameLocator).fill(firstName);
		page.locator(middleNameLocator).fill(middleName);
		page.locator(lastNameLocator).fill(lastName);
		// Wait and fill Employee ID
		Locator input = page.locator("div.oxd-input-group:has(label:has-text('Employee Id')) input");
		input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		input.fill("");
		System.out.println("Employee Id = " + String.valueOf(employee.getEmployeeId()));
		input.fill(String.valueOf(employee.getEmployeeId()));
		clickSaveOnAddEmployee();
	}
	
	public String getAddedEmployeeText() {
		System.out.println(page.locator(savedEmployee).textContent());
		return page.locator(savedEmployee).textContent();
	}
	
//	public int getEmployeeId() {
//		Random rand = new Random();
//		return rand.nextInt(300); 
//	}
//	
//	public String getRandomStringLetters() {
//		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//		StringBuilder sb = new StringBuilder();
//		Random random = new Random();
//		for(int i = 0; i<7; i++) {
//			 int index = random.nextInt(characters.length());
//	            sb.append(characters.charAt(index));
//		}
//		return sb.toString();
//	}
	
	public void clickSaveOnAddEmployee() {
		page.locator(saveButton).click();
	}
}
