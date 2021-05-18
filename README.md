##Hotel Automation

This is standalone maven application.

1. Application takes input from file (src/main/resources/inputFile.txt)
2. Sensor input for different floors are executed concurrently and for same floor are executed sequentialy.

Steps to run :
1. mvn clean package
2. java -jar hotel-automation-1.0-SNAPSHOT.jar

To run tests:
1. mvn clean test 


Problem:
---------
A very prestigious chain of hotels is facing a problem of huge consumption of electricity bills for
its electronic equipment. The common equipment, like lights, ACs, etc are currently controlled
manually, by the hotel staff, using manual switches. Hotel Management wants to optimise the
usage of electricity consumption and also ensure that there is no inconvenience caused to the
guests and staff. So, it has installed Motion Sensors at appropriate places and has approached
you to program a Controller which takes inputs from these sensors and controls various
equipment.
The way the hotel equipment are organised and the requirements for the Controller are listed
below:

● A Hotel can have multiple floors

● Each floor can have multiple main corridors and sub corridors

● Both main corridor and sub corridor have one light each

● Both main and sub corridor lights consume 5 units of power when ON

● Both main and sub corridor have independently controllable ACs

● Both main and sub corridor ACs consume 10 units of power when ON

● All the lights in all the main corridors need to be switched ON between 6 PM to 6 AM,
which is the Night Time slot

● By default, all ACs are switched ON, all the time

● When a motion is detected in one of the sub corridors the corresponding lights need to
be switched ON between 6 PM to 6 AM (Night Time slot)

● The total power consumption of all the ACs and lights combined should not exceed
(Number of Main corridors * 15) + (Number of sub corridors * 10) units of per floor. Sub
corridor AC could be switched OFF to ensure that the power consumption is not more
than the specified maximum value

● When there is no motion for more than a minute the sub corridor lights should be
switched OFF and AC needs to be switched ON
Motion in sub-corridors is input to the controller, which needs to keep track and optimise the power consumption.
