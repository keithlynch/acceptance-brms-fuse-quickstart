package org.jboss.service1.test;

import java.util.Dictionary;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Assert;
import org.junit.Test;

public class TestRoute extends CamelBlueprintTestSupport{
  
  @Override
  protected String getBlueprintDescriptor() {
    return "OSGI-INF/blueprint/properties.xml,OSGI-INF/blueprint/routes.xml";
  }
  
  @Override @SuppressWarnings({ "rawtypes", "unchecked" })
  protected String useOverridePropertiesWithConfigAdmin(Dictionary props) {
    props.put("service1.from", "direct:from");
    props.put("service1.to", "mock:to");
    props.put("brms-server", "../../brms/business-rules/src/main/resources/rules");
    return "org.jboss.fuse.properties";
  }
  
  @Test
  public void test(){
    String in="{\"name\":\"Fred\",\"human\":true,\"likesBeer\":false}";
    template.sendBody("{{service1.from}}", in);
    String result=getMockEndpoint("{{service1.to}}").getExchanges().get(0).getIn().getBody(String.class);
    Assert.assertTrue("name was not converted to Bob by the rules", result.contains("name\":\"Bob"));
  }

}
