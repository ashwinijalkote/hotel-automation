##Hotel Automation

This is standalone maven application.

1. Application takes input from file (src/main/resources/inputFile.txt)
2. Sensor input for different floors are executed concurrently and for same floor are executed sequentialy.

Steps to run :
1. mvn clean package
2. java -jar hotel-automation-1.0-SNAPSHOT.jar

To run tests:
1. mvn clean test 
