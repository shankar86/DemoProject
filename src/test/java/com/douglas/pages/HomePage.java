package com.douglas.pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;

import com.douglas.utils.FrameworkProperties;
import io.cucumber.datatable.DataTable;

public class HomePage {

	private static WebDriver driver;
	private static BasePage basePage;
	private static double counts;
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
		basePage.scrollDown();
		String countOfItems = basePage.getText(By.cssSelector(".product-overview__headline-wrapper"));
		System.out.println(countOfItems);
		String[] items = countOfItems.split("\\(");
		String item1 = items[1];
		System.out.println(item1);
		String finalItem = item1.replace(")", "");
		System.out.println(finalItem);
		counts = Double.parseDouble(finalItem);
		System.out.println("Count of Items in Perfum Page is: "+ counts);
	}

	public static void listTheProductsBasedOnFilters(DataTable dataTableValues) throws InterruptedException {
		List<List<String>> dataTable = dataTableValues.cells();
		System.out.println("Size of dataTable Row Size is: " + dataTable.size());
		for (int i = 0; i < dataTable.size() - 1; i++) {
			System.out.println("Size of dataTable Column Size is: " + dataTable.get(i).size());
			for (int j = 0; j < dataTable.get(i).size(); j++) {
				String filterType = dataTable.get(i).get(j).toString();
				String value = dataTable.get(1).get(j).toString();
				basePage.scrollDown();
				basePage.selectFilter(filterType, value);
			}
		}
	}

	public static void validateTheFilterResult() {
		List<WebElement> ele = driver.findElements(By.xpath(
				"//div[@class=\"product-grid__listing-content\"]/div[2]/div[@class=\"product-grid cms-component cms-component__margin cms-component__margin--default\"]/div[@class=\"row product-grid-row\"]/div[@class=\"product-grid-column col-sm-6 col-md-4 col-lg-3\"]"));
		int itemSize = ele.size();
		System.out.println("ItemSize Is: " + itemSize);
		for (int i = 1; i <= itemSize; i++) {
			String productName = driver.findElement(By.xpath(
					"//div[@class=\"product-grid__listing-content\"]/div[2]/div[@class=\"product-grid cms-component cms-component__margin cms-component__margin--default\"]/div[@class=\"row product-grid-row\"]/div["
							+ i + "]" + "/div/a/div[3]/div[1]/div[1]/div[2]"))
					.getText();
			System.out.println("Product Name is: " + productName);
		}
		Assert.assertTrue("Filtered Results are Not Zero", itemSize<counts*1000);
	}

	public static void closeTheBrowser() {
		driver.quit();
	}
}
