export JAVA_HOME="C:\Program Files\Java\jdk-17"
mvn test "-Dtest=test.RunAllTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false"
# mvn test "-Dtest=test.RunLoginTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 
# mvn test "-Dtest=test.RunUserTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 