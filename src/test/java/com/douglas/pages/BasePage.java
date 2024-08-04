package com.douglas.pages;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void navigateTo(String url) {
		driver.get(url);
	}

	public void click(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void type(By locator, String text) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
	}

	public String getText(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}

	public boolean isDisplayed(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
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

	public void verifyTextByLocator(String expectedText, By locator) {
		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		String actualText = ele.getText();
		System.out.println("Actual text is: " + actualText);

		if (!expectedText.contains(actualText)) {
			Assert.fail(
					"Expected and Actual text is different :" + "Expected :" + expectedText + "Actual :" + actualText);
		}
	}

	public void verifyTitle(String expectedTitle) {
		String actualTitle = driver.getTitle();
		System.out.println("Actual Title is: " + actualTitle);
		Assert.assertEquals("The Actual Title is Not Matching with Expected Title", expectedTitle, actualTitle);
	}

	public void selectFilter(String filterType, String args) throws InterruptedException {
		driver.findElement(By.xpath("//div[contains(text(),'" + filterType + "')]")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//div[@class='facet-option__label']//div[contains(text(),'" + args + "')]"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[contains(text(),'" + filterType + "')]")).click();
		Thread.sleep(1000);
	}
}
