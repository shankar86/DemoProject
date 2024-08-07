package com.douglas.stepdefs;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "rerun.txt",
    glue = "com.douglas.stepdefs",
    plugin = {"pretty", "json:target/cucumber-reports/Cucumber-rerun.json", "html:target/rerun.html"}
)

public class RerunFailedTest {

}
