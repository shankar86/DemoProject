package com.douglas.stepdefs;

import com.douglas.pages.HomePage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefs {

	@Given("Navigate to {string}")
	public void navigate_to(String string) {
		HomePage.navigateToHomeScreen(string);
		
	}

	@When("Handle the cookie consent")
	public void handle_the_cookie_consent() throws InterruptedException{
		HomePage.handleTheCookieConsent();
	}

	@Then("Click on {string}")
	public void click_on(String string) {
		HomePage.clickOnItem(string);
		
	}

	@Then("List the products based on filters")
	public void list_the_products_based_on_filters(DataTable dataTable) throws InterruptedException {
		HomePage.listTheProductsBasedOnFilters(dataTable);
	}
	
	@Then("Close the browser")
	public void close_the_browser() {
		HomePage.closeTheBrowser();
	}
}
