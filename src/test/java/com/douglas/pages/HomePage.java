package com.douglas.pages;

import java.time.Duration;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.douglas.webelements.HomeWebElement;

import io.cucumber.datatable.DataTable;

public class HomePage extends BasePage {

	static Logger log = Logger.getLogger(HomePage.class.getName());
	private static int counts;

	public static void navigateToHomeScreen(String url) {
		getAction().navigateTo(url);
		getAction().driver.manage().window().maximize();
		getAction().verifyTitle("Online-Parfümerie ✔️ Parfum & Kosmetik kaufen | DOUGLAS");
	}

	public static void handleTheCookieConsent() throws InterruptedException {
//		String ele = null;
//		getAction().waitForElementToVisible(HomeWebElement.SHADOW_DOM_ROOT);
//		ele = getAction().driver.findElement(HomeWebElement.SHADOW_DOM_ROOT).getAttribute("data-created-at");
		WebDriverWait wait = new WebDriverWait(getAction().driver, Duration.ofSeconds(10));
		WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(HomeWebElement.SHADOW_DOM_ROOT));
		// Get shadow root
		JavascriptExecutor js = (JavascriptExecutor) getAction().driver;
		SearchContext shadowRoot = (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
		// Wait for an element within the shadow DOM
		WebElement shadowElement = wait.until(driver -> (WebElement) js
				.executeScript("return arguments[0].querySelector('.sc-dcJsrY.eIFzaz')", shadowRoot));
		shadowElement.click();
	}

	public static void clickOnItem(String string) {
		getAction().click(HomeWebElement.PERFUME_LINK);
		getAction().verifyTextByLocator("Parfüm & Düfte", HomeWebElement.PERFUME_PAGE_HEADLINE);
		getAction().scrollDown();
		String countOfItems = getAction().getText(HomeWebElement.TOTAL_COUNT_ITEMS);
		String[] items = countOfItems.split("\\(");
		String finalItem = items[1].replace(")", "").replace(".", "");
		counts = Integer.parseInt(finalItem);
		log.info("Total Items in Perfume Page" + counts);
	}

	public static void listTheProductsBasedOnFilters(DataTable dataTableValues) throws InterruptedException {
		List<List<String>> dataTable = dataTableValues.cells();
		for (int i = 0; i < dataTable.size() - 1; i++) {
			for (int j = 0; j < dataTable.get(i).size(); j++) {
				String filterType = dataTable.get(i).get(j).toString();
				String value = dataTable.get(1).get(j);
				if (value != null) {
					value = value.toString();
				}
				getAction().scrollDown();
				getAction().selectFilter(filterType, value);
			}
		}
	}

	public static void validateTheFilterResult() {
		List<WebElement> ele = getAction().driver.findElements(By.xpath(
				"//div[@class=\"product-grid__listing-content\"]/div[2]/div[@class=\"product-grid cms-component cms-component__margin cms-component__margin--default\"]/div[@class=\"row product-grid-row\"]/div[@class=\"product-grid-column col-sm-6 col-md-4 col-lg-3\"]"));
		int itemSize = ele.size();
		for (int i = 1; i <= itemSize; i++) {
			getAction().driver.findElement(By.xpath(
					"//div[@class=\"product-grid__listing-content\"]/div[2]/div[@class=\"product-grid cms-component cms-component__margin cms-component__margin--default\"]/div[@class=\"row product-grid-row\"]/div["
							+ i + "]" + "/div/a/div[3]/div[1]/div[1]/div[2]"))
					.getText();
		}
		Assert.assertTrue("Filtered Results are Not Zero", itemSize < counts);
	}

	public static void closeTheBrowser() {
		getAction().driver.quit();
	}
}
