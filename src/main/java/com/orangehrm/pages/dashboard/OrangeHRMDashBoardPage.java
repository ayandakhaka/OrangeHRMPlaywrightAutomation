package com.orangehrm.pages.dashboard;

import com.microsoft.playwright.Page;
import com.orangehrm.pages.dashboard.pim.PIMPage;

public class OrangeHRMDashBoardPage {

	private Page page;
	private String dashBoard = "a[href='/web/index.php/dashboard/index']";
	private String dashoardText = "//*[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']";
	private String menuToggleButtonXPath = "//button[contains(@class, 'oxd-main-menu-button')]";
	private String pimElement = "a[href='/web/index.php/pim/viewPimModule']";

	public OrangeHRMDashBoardPage(Page page) {
		this.page = page;
	}
	
	/**
     * Wait until the dashboard is fully loaded (URL + element check).
     */
    public void waitForDashboardToLoad() {
        // ✅ Wait for URL to contain "dashboard"
        page.waitForURL("**/dashboard/**", new Page.WaitForURLOptions().setTimeout(10000));
        // ✅ Wait for a stable dashboard element to appear
        page.waitForSelector(dashoardText, 
            new Page.WaitForSelectorOptions().setTimeout(10000));
    }

	public boolean isDashboardHighlited() {
		waitForDashboardToLoad();
		return page.locator(dashBoard).getAttribute("class").contains("active");
	}

	public String getDashboardHeaderText() {
		waitForDashboardToLoad();
		return page.locator(dashoardText).textContent();
	}

	public String getOrangeHRMDashboardPageURL() {
		waitForDashboardToLoad();
		return page.url();
	}

	public String getOrangeHRMDashboardPageTitle() {
		waitForDashboardToLoad();
		return page.title();
	}

	public PIMPage clickPIMLink() {
		page.locator(pimElement).click();
		return new PIMPage(page);
	}





}
