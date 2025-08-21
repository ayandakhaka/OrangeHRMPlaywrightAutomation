package com.orangehrm.pages.dashboard.pim;

import com.microsoft.playwright.Page;
import com.orangehrm.pim.addemployee.PIMAddEmployeePage;

public class ViewEmployeeListPage {

	private Page page = null;

	private String addButton = "button:has-text('Add')";
	private String pimHeaderTextByXpath = "//h6[text()='PIM']";
	private String addEmployeeInformationTextByXpath = "//h5[text()='Employee Information']";

	public ViewEmployeeListPage(Page page) {
		this.page = page;
	}

	public PIMAddEmployeePage clickAddButton() {
		page.locator(addButton).click();
		return new PIMAddEmployeePage(page);
	}
	
	public String getViewEmployeeListPageURL() {
		return page.url();
	}
	
	public String getPIMHeaderText() {
		return page.locator(pimHeaderTextByXpath).textContent();
	}
	
	public String getEmployeeInformationText() {
		return page.locator(addEmployeeInformationTextByXpath).textContent();
	}
}
