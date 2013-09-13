Feature: example feature

     Scenario: example scenario
     Given the url "http://localhost:8080/jboss-brms/org.drools.guvnor.Guvnor/package/businessgrouping.servicegrouping/1.0.0-SNAPSHOT" returns an http "200"
     And set property "testservice.from" to "sftp://admin@localhost:10022/target/testservice/in" for service "org.jboss.fuse.properties"
     And set property "testservice.to" to "file://target/testservice/out" for service "org.jboss.fuse.properties"
     And a file is created:
	 |File                           |Name |isHuman |likesBeer |
	 |target/testservice/in/fred.txt |Fred |true    |false     |
     And the command "osgi:list -t 0" contains "service1"
     And a folder called "target/testservice/out" is created or exists
     Then wait for "10" seconds for file in "target/testservice/out" called "fred.txt" to contain:
       """
       {"human":true,"likesBeer":false,"name":"Bob","additionalProperties":{}}
       """

GET THIS REMOVED AND COMMITTED!

#Scenario: Successfully processing a single language GB Cerillion Bill XML file through to StreamServe.
#Given the invoice data service is deployed
#And a Cerillion billing XML file "BDD01-GB-120_130620_00001.xml" is created with Country Code "GB" and Language "en-gb"
#And a number shielding list contains:
#|Country |Number       |
#|GB      |447408901403 |
#When an localiseInvoiceData message is received containing:
#|xmlFilePath       |xmlFileName                  |billCycleCd |billDate            |billCount |countryCode |
#|target/invoice/in |BDD01-GB-120_130620_00001.xml|120         |1999-12-31T23:59:00 |19        |GB          |
#Then the bill file "BDD01-GB-120_130620_00001.xml" will be consumed
#And a single bill xml will be output to StreamServe directory containing:
#|xmlFilePath        |xmlFileName                   |billCycleCd |billDate           |billCount |countryCode |billLanguage|
#|target/invoice/out |BDD01-GB-120_130620_00001.xml |120         |1999-12-31T23:59:00|1         |GB          |en-gb       |



