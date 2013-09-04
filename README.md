acceptance-brms-fuse-quickstart
===============================


To start:
=========

# obtain the dependant external modules that are not in any externally accesible maven repo
cd tools
git clone https://github.com/matallen/maven-guvnor-bulk-importer
git clone https://github.com/matallen/maven-fusecontainer-plugin
git clone https://github.com/matallen/jboss-fuse-drools
cd ..

# to build the business-rules module + the guvnor-import.xml inside the jar so it's versioned in maven repo
mvn clean install

# to run acceptance tests
cd acceptance
mvn clean cargo:run -Dcargo

# alternatively, "head pom.xml" in acceptance for a number of mvn commands for quick reference
