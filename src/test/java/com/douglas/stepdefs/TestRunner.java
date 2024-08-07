package com.douglas.stepdefs;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/TestCases",
		glue="com.douglas.stepdefs",
		tags="@test",
		plugin= {"pretty","html:target/CucumberTestReport.html","json:target/cucumber-reports/CucumberTestReport.json", "rerun:rerun.txt"})

public class TestRunner {

}
