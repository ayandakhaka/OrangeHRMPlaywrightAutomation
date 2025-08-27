package com.orangehrm.pim.employeelist;

import java.nio.file.Paths;
import com.microsoft.playwright.Page;

public class EmployeeListPage {

	private Page page = null;
	
	public EmployeeListPage(Page page) {
		this.page = page;
	}
	
	private String contactDetailsLinkText = "//a[contains(@href,'contactDetails') and text()='Contact Details']";
	private String street1 = "//label[text()='Street 1']/following::input[1]";
	private String street2 = "//label[text()='Street 2']/following::input[1]";
	private String city = "//label[text()='City']/following::input[1]";
	private String stateOrProvince = "//label[text()='State/Province']/following::input[1]";  
	private String zipOrPostalCode = "//label[text()='Zip/Postal Code']/following::input[1]";
	private String dropdown = "div.oxd-select-text-input";
	private String countryDropDown = "//div[@role='option']//span[text()='South Africa']";
	private String telephoneHome = "//label[text()='Home']/following::input[1]";
	private String telephoneMobile = "//label[text()='Mobile']/following::input[1]";
	private String telephoneWork = "//label[text()='Work']/following::input[1]";
	private String emailWorkEmail = "//label[text()='Work Email']/following::input[1]";
	private String emailOtherEmail = "//label[text()='Other Email']/following::input[1]";
	private String saveButton = "//button[text()='Save']";
	private String addAttachementButton = "//button[normalize-space()='Add']";
	private String fileUpload = "input[type='file']";
	private String commentField = "textarea[placeholder='Type comment here']";
	private String saveAttachment = "//button[normalize-space()='Save']";
	
	public void fillContactDetails() {
		page.locator("//a[text()='Contact Details']").click();
		page.locator(street1).fill("4888 Tswelopele");
		page.locator(street2).fill("Thembisa");
		page.locator(city).fill("Johannesburg");
		page.locator(stateOrProvince).fill("Gauteng");
		page.locator(zipOrPostalCode).fill("1432");
		page.locator(dropdown).click();
		page.locator(countryDropDown).click();
		page.locator(telephoneHome).fill("083 932 1234");
		page.locator(telephoneMobile).fill("011 111 3333");
		page.locator(telephoneWork).fill("074 000 3333");
		page.locator(emailWorkEmail).fill("sdsdd@derivco.co.za");
		page.locator(emailOtherEmail).fill("sdsad@gmail.com");
		page.locator(saveButton).click();
		page.locator(addAttachementButton).click();
		page.setInputFiles(fileUpload, Paths.get("C:\\Users\\USER\\Downloads\\picture.png"));
		page.locator(commentField).fill("This is a comment");
		page.locator(saveAttachment).click();
	}
	
	public void clickContactDetailsLinkText() {
		page.locator(contactDetailsLinkText);
	}
}
