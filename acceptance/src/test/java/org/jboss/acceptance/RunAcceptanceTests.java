package org.jboss.acceptance;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(
    format = {/*"html:target/cucumber-html-report", "json-pretty:target/cucumber-json-report.json"*/},
    glue="org.jboss.acceptance.steps", 
    tags="~@Ignore,@InDevelopment")
public class RunAcceptanceTests {

  @Test
  public void runAcceptanceTests(){
  }
}
