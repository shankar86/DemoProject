package com.douglas.stepdefs;

import org.apache.log4j.Logger;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseStep {
	static Logger log = Logger.getLogger(BaseStep.class.getName());
	
	@Before
	public void beforeScenario(Scenario scenario) throws Exception {		
		log.info("-------------NEW SCENARIO STARTED-----------------------");
		log.info("SCENARIO NAME : " + scenario.getName());
	}
	
	@After
	public void close_the_browser() {
	   TestContext.removeDriver();
	}

}
