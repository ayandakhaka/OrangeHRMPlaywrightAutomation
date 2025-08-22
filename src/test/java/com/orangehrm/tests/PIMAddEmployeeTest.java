package com.orangehrm.tests;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.database.Employee;
import com.orangehrm.database.OrangeHRMDataStore;
import com.orangehrm.pages.dashboard.OrangeHRMDashBoardPage;
import com.orangehrm.pages.dashboard.pim.ViewEmployeeListPage;
import com.orangehrm.pages.homepage.OrangeHRMHomePage;
import com.orangehrm.pim.addemployee.PIMAddEmployeePage;

public class PIMAddEmployeeTest extends BaseTest {

	private OrangeHRMHomePage orangeHrmHomePage;
	private OrangeHRMDashBoardPage orangeHRMDashBoardPage;
	private ViewEmployeeListPage viewEmployeeListPage;
	private PIMAddEmployeePage pimAddEmployeePage;
	private String employeeName;
	private OrangeHRMDataStore orangehrmDataStore;
	Employee emp;

	@BeforeClass(groups = "Before Add PIM Employee")
	public void setup() throws Exception {
		
		OrangeHRMDataStore.init();
		launchPlaywrightBrowser(prop.getProperty("chrome_browser"), false);
		launchApplication(prop.getProperty("orangehrm_url"));
		// Wait until page is fully loaded
		String username = OrangeHRMDataStore.getUsername();
		String password = OrangeHRMDataStore.getPassword();
		page.waitForLoadState();
		orangeHrmHomePage = new OrangeHRMHomePage(page);
		orangeHRMDashBoardPage = new OrangeHRMDashBoardPage(page);
		orangeHrmHomePage.orangeHRMLogin(username, password);
		orangeHRMDashBoardPage.clickPIMLink();

		viewEmployeeListPage = new ViewEmployeeListPage(page);
		pimAddEmployeePage = new PIMAddEmployeePage(page);
		emp = OrangeHRMDataStore.insertEmployee();

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
	public void addEmployeeDetails() throws URISyntaxException, SQLException {
		
		employeeName = emp.getFirstName();
		pimAddEmployeePage.addEmployeeWithoutCreateLoginDetails(
				emp.getFirstName(),
				emp.getMiddleName(),
				emp.getLastName());
		//pimAddEmployeePage.clickSaveOnAddEmployee();
		String currentURL = page.url();
		System.out.println("currentURL = " + currentURL);
		URI uri = new URI(currentURL);
		String path = uri.getPath();
		System.out.println("Path = " + path);
		
	}
	
	@Test(priority = 5, groups = "Add Employee Details", dependsOnGroups = "Click Add Button")
	public void verifyAddedEmployeeHeaderText() throws URISyntaxException {
		String actualResults = employeeName + " " + emp.getLastName();
		Assert.assertEquals(pimAddEmployeePage.getAddedEmployeeText(), actualResults);
	}
}
