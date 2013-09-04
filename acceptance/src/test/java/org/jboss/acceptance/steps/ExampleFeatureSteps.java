package org.jboss.acceptance.steps;

import static com.jayway.restassured.RestAssured.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.acceptance.tools.FreemarkerTemplate;
import org.jboss.acceptance.tools.MockFtpServer;
import org.jboss.acceptance.tools.ToHappen;
import org.jboss.acceptance.tools.Wait;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ExampleFeatureSteps {
  private static MockFtpServer ftp;
  static{
    ftp=new MockFtpServer(10022);
    ftp.start();
  }
  
  @Given("^the url \"([^\"]*)\" returns an http \"([^\"]*)\"$")
  public void the_url_returns_an_http(String url, String responseCode) throws Throwable {
    DefaultHttpClient client = new DefaultHttpClient();
    HttpResponse response=client.execute(new HttpGet(url));
    if (200!=response.getStatusLine().getStatusCode())
      System.out.println(IOUtils.toString(response.getEntity().getContent()));
    assertEquals(Integer.parseInt(responseCode), response.getStatusLine().getStatusCode());
  }
  
  @Given("^a file is created:$")
  public void aFileIsCreatedContaining2(List<Map<String, String>> data) throws FileNotFoundException, IOException{
    for(Map<String, String> row:data){
      File file=new File(row.get("File"));
      file.getParentFile().mkdirs();
      IOUtils.write("{\"name\":\""+row.get("Name")+"\",\"human\":"+row.get("isHuman")+",\"likesBeer\":"+row.get("likesBeer")+"}", new FileOutputStream(file));
      System.out.println("File ["+file.getCanonicalPath()+"] created.");
    }
  }
//  @Given("^a file is created in \"([^\"]*)\" called \"([^\"]*)\" with the content:$")
//  public void aFileIsCreatedContaining(String dir, String filename, String content) throws FileNotFoundException, IOException{
//    new File(dir).mkdirs();
//    File f=new File(dir, filename);
//    IOUtils.write(content, new FileOutputStream(f));
//    System.out.println("File ["+dir+File.separator+filename+"] created.");
//  }
  @Given("^the command \"([^\"]*)\" contains \"([^\"]*)\"$")
  public void the_fuse_command_contains(String command, String expected) throws Throwable {
    String result=executeCommand(command);
    assertTrue("command "+command+" contained ["+result+"] rather than the expected ["+expected+"]", result.contains(expected));
    assertTrue("fail...", result.contains(expected));
  }
  @Given("^a folder called \"([^\"]*)\" is created or exists$")
  public void folderCreatedOrExists(String folder){
    new File(folder).mkdirs();
  }
  @Then("^wait for \"([^\"]*)\" seconds for file in \"([^\"]*)\" called \"([^\"]*)\" to contain:$")
  public void checkFileContent(String wait, final String dir, final String filename, String expected) throws FileNotFoundException, IOException{
    Wait.For(Integer.parseInt(wait), new ToHappen() {public boolean hasHappened() {
        return new File(dir, filename).exists();
    }});
    String actual=IOUtils.toString(new FileInputStream(new File(dir, filename)));
    assertEquals(expected, actual);
  }
  
  @Given("^set property \"([^\"]*)\" to \"([^\"]*)\" for service \"([^\"]*)\"$")
  public void set_property_to_for_service(final String propertyName, final String value, final String servicePid) throws Throwable {
    Wait.For(10, new ToHappen() {public boolean hasHappened() {
    executeCommand("propset -p "+servicePid+" "+propertyName+" "+value);
      return executeCommand("config:list | grep \""+propertyName+" = "+value+"\"").length()>0;
    }});
  }
  
  
  //===========================
  //===========================
  
  
//  @Given("^brms is accessible on \"([^\"]*)\"$")
//  public void brmsIsUp(String brmsUrl) throws Exception{
//    HttpClient client=new DefaultHttpClient();
//    HttpGet get=new HttpGet(brmsUrl+"/rest/packages");
//    HttpResponse response=client.execute(get);
//    System.out.println("XXXXXXXX="+response.getStatusLine().getStatusCode());
//    assertEquals(response.getStatusLine().getStatusCode(), 200);
//  }
//  
  
  @Given("^wait for \"([^\"]*)\" to contain \"([^\"]*)\"$")
  public void wait_for_to_contain(final String command, final String toContain) throws Throwable {
    Wait.For(10, new ToHappen() {
      public boolean hasHappened() {
        return executeCommand(command).contains(toContain);
      }});
  }
  
  @Given("^the file \"([^\"]*)\" is copied to \"([^\"]*)\"$")
  public void copyFile(String src, String dest) throws FileNotFoundException, IOException{
    File from=new File(src);
    File to=new File(dest);
    if (!from.exists()) throw new RuntimeException("Unable to find source file");
    if (to.exists()) throw new RuntimeException("File already in that location ["+dest+"]");
    IOUtils.copy(new FileInputStream(from), new FileOutputStream(to));
  }
  
  public String executeCommand(String command){
    try{
      String FUSE_HOME="/home/mallen/jboss-fuse-6.0.0.redhat-024";
      System.out.println("Executing ["+FUSE_HOME+"/bin/client -u admin -p admin "+command+"]");
      Process p=Runtime.getRuntime().exec(new String[]{FUSE_HOME+"/bin/client","-u","admin","-p","admin", command});
      String result=IOUtils.toString(p.getInputStream());
      System.out.println("result = "+result);
      return result;
    }catch(Exception sink){
      return null;
    }
  }

}
