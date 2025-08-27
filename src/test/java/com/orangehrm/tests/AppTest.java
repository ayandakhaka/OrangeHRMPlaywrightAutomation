package com.orangehrm.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.orangehrm.base.BaseTest;
import com.orangehrm.database.Employee;
import com.orangehrm.database.OrangeHRMDataStore;
import com.orangehrm.pages.dashboard.OrangeHRMDashBoardPage;
import com.orangehrm.pages.homepage.OrangeHRMHomePage;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTest
{
	private static OrangeHRMHomePage orangeHrmHomePage;
	private static OrangeHRMDashBoardPage orangeHRMDashBoardPage;
	
	private static Employee emp;
	private static  OrangeHRMDataStore orangehrmDataStore;

	public static void main(String [] args) throws Exception {

//		Playwright playwright = Playwright.create(); 
//		Browser browser = playwright.chromium().launch(
//				new BrowserType.LaunchOptions().setHeadless(false));
//		BrowserContext browserContext = browser.newContext();   
//		
//		Page page = browserContext.newPage();
		OrangeHRMDataStore.init();
		
		emp = OrangeHRMDataStore.insertEmployee();
		orangehrmDataStore = new OrangeHRMDataStore();
		
		String employeeFirstName = emp.getFirstName();
		String employeeMiddleName = emp.getMiddleName();
		String employeeLastName = emp.getLastName();
		int employeeId = orangehrmDataStore.getEmployeeId();
		
		System.out.println("Employee Name : " + employeeFirstName);
		
		System.out.println("Middle Name : " + employeeMiddleName);
		
		System.out.println("Last Name : " + employeeLastName);
		
		System.out.println("employeeId : " + employeeId);
		
		
		
		

		//Locators
		//page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		
		//browser.close();
	}


}
