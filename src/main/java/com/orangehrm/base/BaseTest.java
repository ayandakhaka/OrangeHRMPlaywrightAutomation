package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitUntilState;

public class BaseTest
{

	private static  Playwright playwright = null; 
	private static Browser browser = null;
	private static BrowserContext browserContext = null;
	protected static Page page;
	
	public static Properties prop;
	
	public BaseTest() {
		if(prop == null) {
			loadConfig();
		}
	}
	
	public void loadConfig() {  
		//ExtentManager.setExtent();
		DOMConfigurator.configure("log4j.xml");
		
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Configuration\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void launchPlaywrightBrowser(String browserName, boolean headless) {

        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
                break;
            case "edge":
                browser = playwright.chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless));
                break;
            case "firefox":
                browser = playwright.firefox()
                        .launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "webkit":
                browser = playwright.webkit()
                        .launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            default:
                throw new RuntimeException("Unknown browser: " + browserName);
        }

        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1400, 700));
        page = browserContext.newPage();  // ✅ Page tied to the context

        System.out.println("**** Browser Name and Version: " + browserName + " : " + browser.version());
    } 
	
	public static void launchApplication(String url) {
		
		page.navigate(url, new Page.NavigateOptions()
	            .setWaitUntil(WaitUntilState.NETWORKIDLE));
	    try {
	        page.waitForSelector("input[name='username']", 
	            new Page.WaitForSelectorOptions().setTimeout(100000));
	        System.out.println("Username field detected — login page ready.");
	    } catch (PlaywrightException e1) {
	        // Fallback: Check if older versions use 'txtUsername' ID
	        try {
	            page.waitForSelector("#txtUsername", 
	                new Page.WaitForSelectorOptions().setTimeout(100000));
	            System.out.println("Legacy login field detected — login page ready.");
	        } catch (PlaywrightException e2) {
	            // Fallback: Check login button as last resort
	            page.waitForSelector("button[type='submit'], .oxd-button", 
	                new Page.WaitForSelectorOptions().setTimeout(100000));
	            System.out.println("Login button detected — login page ready.");
	        }
	    }
	}
	
	public void closePlaywright() {
        page.close();
        browser.close();
        playwright.close();
    }
}
