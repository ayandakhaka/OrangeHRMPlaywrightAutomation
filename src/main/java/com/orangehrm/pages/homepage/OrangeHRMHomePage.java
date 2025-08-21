package com.orangehrm.pages.homepage;

import com.microsoft.playwright.Page;
import com.orangehrm.pages.dashboard.OrangeHRMDashBoardPage;

public class OrangeHRMHomePage {

	private final Page page;

	private String usernameBox = "input[name='username']";
	private String passwordBox = "input[name='password']";
	private String loginButton = "button[type='submit']";

	private String orangeHrmLogo = "img[alt='company-branding']";
	private String loginHeaderTextByCSSSelector = ".orangehrm-login-title";

	private String invalidLoginErrorText = ".oxd-text oxd-text--p oxd-alert-content-text";

	public OrangeHRMHomePage(Page page) {
		this.page = page;
	}

	public OrangeHRMDashBoardPage orangeHRMLogin(String username, String password) {
		page.fill(usernameBox, username);
		page.fill(passwordBox, password);
		page.click(loginButton);
		return new OrangeHRMDashBoardPage(page);
	}

	public boolean isOrangeHRMLogoVisible() {
		if(page.locator(orangeHrmLogo).isVisible()) {
			return true;
		} else {
			return false;
		}
	}

	public String getLoginOrangeHRMHeaderText() {
		return page.locator(loginHeaderTextByCSSSelector).textContent();
	}

	public String getOrangeHRMHomePageURL() {
		return page.url();
	}

	public String getOrangeHRMHomePageTitle() {
		return page.title();
	}

	public String getErrorLoginText() {
		return page.locator(invalidLoginErrorText).textContent();
	}
}
