package com.douglas.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.douglas.utils.FrameworkProperties;
import io.cucumber.datatable.DataTable;

public class HomePage {

	private static WebDriver driver;
	private static BasePage basePage;
	public static final By SHADOW_DOM_ROOT = By.cssSelector("#usercentrics-root");
	public static final By CONSENT_ACCEPT = By.cssSelector(".sc-dcJsrY.eIFzaz");
	public static final By PERFUME_LINK = By
			.cssSelector(".link.link--nav-heading.navigation-main-entry__link[href='/de/c/parfum/01']");
	public static final By PERFUME_PAGE_HEADLINE = By.cssSelector(".headline-bold.product-overview__headline");

	public static void navigateToHomeScreen(String url) {
		if (FrameworkProperties.SELENIUM_BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
			basePage = new BasePage(driver);
		} else if (FrameworkProperties.SELENIUM_BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
			basePage = new BasePage(driver);
		} else {
			driver = new FirefoxDriver();
			basePage = new BasePage(driver);
		}
		basePage.navigateTo(url);
		driver.manage().window().maximize();
		basePage.verifyTitle("Online-Parfümerie ✔️ Parfum & Kosmetik kaufen | DOUGLAS");
	}

	public static void handleTheCookieConsent() throws InterruptedException {
		// This Element is inside single shadow DOM.
		Thread.sleep(5000);
		SearchContext shadow = driver.findElement(SHADOW_DOM_ROOT).getShadowRoot();
		Thread.sleep(1000);
		shadow.findElement(CONSENT_ACCEPT).click();
	}

	public static void clickOnItem(String string) {
		basePage.click(PERFUME_LINK);
		basePage.verifyTextByLocator("Parfüm & Düfte", PERFUME_PAGE_HEADLINE);
	}

	public static void listTheProductsBasedOnFilters(DataTable dataTableValues) throws InterruptedException {
		List<List<String>> dataTable = dataTableValues.cells();
		System.out.println("Size of dataTable is: " + dataTable.size());
		for (int i = 0; i < dataTable.size()-1; i++) {
			System.out.println("Size of dataTable is: " + dataTable.get(i).size());
			for (int j = 0; j < dataTable.get(i).size(); j++) {
				String filterType = dataTable.get(i).get(j).toString();
				String value = dataTable.get(1).get(j).toString();
				basePage.scrollDown();
				basePage.selectFilter(filterType, value);
			}
		}
	}
	

	public static void closeTheBrowser() {
		driver.quit();
	}
}
