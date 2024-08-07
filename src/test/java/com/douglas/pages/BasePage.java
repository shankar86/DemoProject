package com.douglas.pages;

import com.douglas.selenium.action.SeleniumActions;
import com.douglas.stepdefs.TestContext;

public class BasePage {

	public static TestContext getContext() {
		return TestContext.getContext();
	}

	public static SeleniumActions getAction() {
		return getContext().getAction();
	}
}
