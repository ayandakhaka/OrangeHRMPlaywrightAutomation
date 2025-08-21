package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.dashboard.OrangeHRMDashBoardPage;
import com.orangehrm.pages.homepage.OrangeHRMHomePage;

public class OrangeHRMDashboardPageTest extends BaseTest {

private OrangeHRMHomePage orangeHrmHomePage;
private OrangeHRMDashBoardPage orangeHRMDashBoardPage;
	
	@BeforeClass
	public void setup() {
		
		launchPlaywrightBrowser(prop.getProperty("chrome_browser"), false);
        launchApplication(prop.getProperty("orangehrm_url"));
        // Wait until page is fully loaded
        page.waitForLoadState();
        orangeHrmHomePage = new OrangeHRMHomePage(page);
        orangeHRMDashBoardPage = new OrangeHRMDashBoardPage(page);
        orangeHrmHomePage.orangeHRMLogin(prop.getProperty("username"), prop.getProperty("password"));   
        
	}
	
	@Test(priority = 1)
	public void verifyOrangeHRMDashboardPageURL() {
		Assert.assertEquals(orangeHRMDashBoardPage.getOrangeHRMDashboardPageURL(), prop.getProperty("orangehrm_dashboard_url"));
	}
	
	@Test(priority = 2)
	public void verifyOrangeHRMDashboardPageTitle() {
		Assert.assertEquals(orangeHRMDashBoardPage.getOrangeHRMDashboardPageTitle(), prop.getProperty("orangehrm_homepage_title"));
	}
	
	@Test(priority = 3)
	public void verifyOrangeHRMDashboardPageDashboardHeaderText() {
		Assert.assertEquals(orangeHRMDashBoardPage.getDashboardHeaderText(), prop.getProperty("dashboard_header_text"));
	}
	
	@Test(priority = 4)
	public void verifyOrangeHRMDashboardPageDashboardIsActiveFromMenu() {
		Assert.assertEquals(orangeHRMDashBoardPage.isDashboardHighlited(), true);
	}
	
	@AfterClass
	public void closeBrowser() {
		closePlaywright();
	}
}
