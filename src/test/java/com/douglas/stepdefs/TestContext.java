package com.douglas.stepdefs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.douglas.selenium.action.SeleniumActions;
import com.douglas.utils.FrameworkProperties;

public class TestContext {
	private static WebDriver driver;
	public static ThreadLocal<TestContext> thread;
	private SeleniumActions action = null;
	public String browser = null;
	public ChromeOptions options;

	public TestContext() {
		browser = FrameworkProperties.SELENIUM_BROWSER;

		if (browser == null) {
			browser = "firefox";
		} else if (browser.equalsIgnoreCase("chrome")) {
			options = new ChromeOptions();
			options.addArguments("--disable-search-engine-choice-screen");
		}
		driver = browser.equalsIgnoreCase("chrome") ? new ChromeDriver(options) : new FirefoxDriver();
		action = new SeleniumActions(driver);
	}

	public SeleniumActions getAction() {
		return action;
	}

	public static TestContext getContext() {
		return thread.get();
	}

	public static void removeDriver() {
		thread.remove();
		driver.quit();
	}

	static {
		thread = new ThreadLocal<TestContext>() {
			protected TestContext initialValue() {
				return new TestContext();
			}
		};
	}

}
