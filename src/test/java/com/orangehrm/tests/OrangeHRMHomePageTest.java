package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.homepage.OrangeHRMHomePage;

public class OrangeHRMHomePageTest extends BaseTest {
	
	private OrangeHRMHomePage orangeHrmHomePage;
	
	@BeforeClass
	public void setup() {
		
		launchPlaywrightBrowser(prop.getProperty("chrome_browser"), true);
        launchApplication(prop.getProperty("orangehrm_url"));
        orangeHrmHomePage = new OrangeHRMHomePage(page);
	}
	
	@Test(priority = 1)
	public void verifyOrangeHRMHomePageTitle() {
		Assert.assertEquals(orangeHrmHomePage.getOrangeHRMHomePageTitle(),
				prop.get("orangehrm_homepage_title"),
				"Orange HRM Home Page URL is not found");
	}
	
	@Test(priority = 2)
	public void verifyOrangeHRMHomePageURL() {
		Assert.assertEquals(orangeHrmHomePage.getOrangeHRMHomePageURL(),
				prop.getProperty("orangehrm_homepage_url"),
				"Orange HRM Home Page URL is not found");
	}

	@Test(priority = 3)
	public void verifyOrangeHRMHomePageHeaderText() {
		Assert.assertEquals(orangeHrmHomePage.getLoginOrangeHRMHeaderText(),
				prop.getProperty("login_headertext"),
				"Orange HRM Home Page Header text is not found");
	}

	@Test(priority = 4)
	public void verifyOrangeHRMHomePageLogo() {
		Assert.assertEquals(orangeHrmHomePage.isOrangeHRMLogoVisible(),
				true ,
				"Orange HRM Home Page Logo is not found");
	}
	
	@AfterClass
	public void closeBrowser() {
		closePlaywright();
	}

}
