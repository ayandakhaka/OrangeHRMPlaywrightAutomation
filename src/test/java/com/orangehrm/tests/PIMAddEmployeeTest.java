package com.orangehrm.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.dashboard.OrangeHRMDashBoardPage;
import com.orangehrm.pages.dashboard.pim.ViewEmployeeListPage;
import com.orangehrm.pages.homepage.OrangeHRMHomePage;
import com.orangehrm.pim.addemployee.PIMAddEmployeePage;

public class PIMAddEmployeeTest extends BaseTest {
	
	String randomStr;

	private OrangeHRMHomePage orangeHrmHomePage;
	private OrangeHRMDashBoardPage orangeHRMDashBoardPage;
	private ViewEmployeeListPage viewEmployeeListPage;
	private PIMAddEmployeePage pimAddEmployeePage;

	@BeforeClass(groups = "Before Add PIM Employee")
	public void setup() {

		launchPlaywrightBrowser(prop.getProperty("chrome_browser"), false);
		launchApplication(prop.getProperty("orangehrm_url"));
		// Wait until page is fully loaded
		randomStr = UUID.randomUUID().toString();
		page.waitForLoadState();
		orangeHrmHomePage = new OrangeHRMHomePage(page);
		orangeHRMDashBoardPage = new OrangeHRMDashBoardPage(page);
		orangeHrmHomePage.orangeHRMLogin(prop.getProperty("username"), prop.getProperty("password"));
		orangeHRMDashBoardPage.clickPIMLink();

		viewEmployeeListPage = new ViewEmployeeListPage(page);
		pimAddEmployeePage = new PIMAddEmployeePage(page);

	}

	@Test(priority = 1, groups = "Before Add PIM Employee")
	public void verifyViewEmployeePageURL() {
		Assert.assertEquals(viewEmployeeListPage.getViewEmployeeListPageURL(),
				prop.getProperty("view_employee_page_url"));
	}

	@Test(priority = 2, groups = "Before Add PIM Employee")
	public void verifyViewEmployeePageHeaderText() {
		Assert.assertEquals(viewEmployeeListPage.getPIMHeaderText(),
				prop.getProperty("pim_header_text"));
	}

	@BeforeGroups(groups = "Click Add Button", dependsOnGroups = "Before Add PIM Employee")
	public void clickAddEmployee() {
		viewEmployeeListPage.clickAddButton();
	}
	
	@Test(priority = 3, groups = "Click Add Button", dependsOnGroups = "Before Add PIM Employee")
	public void verifyAddEmployeePageURL() {
		Assert.assertEquals(pimAddEmployeePage.getAddEmployeeURL(), prop.getProperty("add_employee_url"));
	}

	@Test(priority = 4, groups = "Click Add Button", dependsOnGroups = "Before Add PIM Employee")
	public void verifyAddEmployeeHeaderText() {
		Assert.assertEquals(pimAddEmployeePage.getAddEmployeeHeaderText(), prop.getProperty("add_employee"));
	}
	
	@BeforeGroups(groups = "Add Employee Details", dependsOnGroups = "Click Add Button")
	public void addEmployeeDetails() {
		
		pimAddEmployeePage.addEmployee(
				randomStr,
				prop.getProperty("middle_name"),
				prop.getProperty("last_name"),
				true,
				prop.getProperty("username"),
				prop.getProperty("password"), prop.getProperty("confirm_password"), 
				prop.getProperty("status")
				);
	}
	
	@Test(priority = 5, groups = "Add Employee Details", dependsOnGroups = "Click Add Button")
	public void verifyAddedEmployeeHeaderText() {
		String actualResults = randomStr + " " + prop.getProperty("middle_name");
		Assert.assertEquals(pimAddEmployeePage.getAddedEmployeeText(), actualResults);
	}
}
