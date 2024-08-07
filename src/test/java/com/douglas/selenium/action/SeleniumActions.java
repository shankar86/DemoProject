package com.douglas.selenium.action;

import java.time.Duration;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.douglas.pages.BasePage;

public class SeleniumActions extends BasePage {

	static Logger log = Logger.getLogger(SeleniumActions.class.getName());
	public WebDriver driver;
	public WebDriverWait wait;

	public SeleniumActions(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	public void navigateTo(String url) {
		driver.get(url);
	}

	public void click(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible -->" + locator);
			e.printStackTrace();
		} catch (ElementNotInteractableException e) {
			Assert.fail("Web Element is not intractable -->" + locator);
			e.printStackTrace();
		} catch (StaleElementReferenceException e) {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}

	}

	public void type(By locator, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible -->" + locator);
			e.printStackTrace();
		}
	}

	public String getText(By locator) {
		String actualText = null;
		try {
			actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible -->" + locator);
			e.printStackTrace();
		}
		return actualText;
	}

	public boolean isDisplayed(By locator) {
		boolean value = false;
		try {
			value = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible -->" + locator);
			e.printStackTrace();
		}
		return value;
	}

	public void scrollDownBy(By locator) {
		WebElement ele = driver.findElement(locator);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(0, 350)", "");
	}

	public void verifyTitle(String expectedTitle) {
		String actualTitle = driver.getTitle();
		Assert.assertEquals("The Actual Title is Not Matching with Expected Title", expectedTitle, actualTitle);
	}

	public void verifyTextByLocator(String expectedText, By locator) {
		String actualText = null;
		try {
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			actualText = ele.getText();
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible -->" + locator);
			e.printStackTrace();
		}

		if (!expectedText.contains(actualText)) {
			Assert.fail(
					"Expected and Actual text is different :" + "Expected :" + expectedText + "Actual :" + actualText);
		}
	}

	public void selectFilter(String filterType, String args) {
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + filterType + "')]")));
			driver.findElement(By.xpath("//div[contains(text(),'" + filterType + "')]")).click();
			if (args != null) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[@class='facet-option__label']//div[contains(text(),'" + args + "')]")));
				driver.findElement(
						By.xpath("//div[@class='facet-option__label']//div[contains(text(),'" + args + "')]")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[aria-checked='true']")));
			}
			driver.findElement(By.xpath("//div[contains(text(),'" + filterType + "')]")).click();
		} catch (StaleElementReferenceException e) {
			Assert.fail("Element not available in DOM: " + e.getMessage());
		}
	}

	public void waitForElementToVisible(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible: " + locator);
			e.printStackTrace();
			e.getMessage();
		} catch (TimeoutException e) {
			Assert.fail("Driver Timeout Exception for: " + locator);
			e.printStackTrace();
		}

	}

	public void waitForElementToVisible(By locator, SearchContext shadow) {
		try {
			wait.until(ExpectedConditions.visibilityOf(shadow.findElement(locator)));
		} catch (NoSuchElementException e) {
			Assert.fail("Web Element is not visible: " + locator);
			e.printStackTrace();
			e.getMessage();
		} catch (TimeoutException e) {
			Assert.fail("Driver Timeout Exception for: " + locator);
			e.printStackTrace();
		}

	}
}
