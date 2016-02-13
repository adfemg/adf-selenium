# adf-selenium
Library for easy testing of Oracle ADF applications with Selenium.

## Example Test
Just a quick example to demonstrate how easy it is to test an ADF application with these tools:


## Getting Started
* be sure the JUnit Extension is installed in your JDeveloper (use Help > Check for Update)
* download or clone this git repository
* open the project in JDeveloper 12.1 or 12.2
  * right click the SeleniumTools project and select Deploy, then adf-selenium-tools to create the JAR
  * add the SeleniumTools/deploy/adf-selenium-tools.jar to your own project as library
* download the latest Java Selenium Client from http://www.seleniumhq.org/download/ (this was tested with v2.52)
  * unzip the selenium zip and add the selenium JAR and all JARs from the lib subdirectory to your own project as library
* add JDEV_HOME/oracle_common/modules/oracle.adf.view/adf-richclient-automation-11.jar to your own project as library. This contains Oracle's own ADF extensions for automated testing.
* start writing your tests. You can look at the RichClientDemoTest included with this project that contains tests running against Oracle's ADF Component Demo.
