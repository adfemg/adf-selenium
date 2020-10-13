# adf-selenium
Library for easy testing of Oracle ADF applications with Selenium.

## Example Test
Just a quick example to demonstrate how easy it is to test an ADF application with these tools:
```
@Test
public void testDragAndDrop() throws Exception {
  DragDropPage page = dragDropPageProvider.goHome(); // navigate to page
  AdfInputText source = page.findInputTextDragSource(); // easily locate components on the page
  AdfOutputText target = page.findOutputTextDropTarget();
  assertEquals("Now Drag Me!", source.getValue()); // easily get value from adf component
  assertEquals("Drop Here!", target.getValue());
  source.dragAndDropTo(target); // easily interact with components (here we do drag-and-drop)
  assertEquals("Now Drag Me!", target.getValue()); // assert the value of the drop component has changed
}
@Test
public void testAutoSuggest() {
  AutoSuggestPage page = autoSuggestPageProvider.goHome();
  AutoSuggestBehavior<AdfInputText> inputtext = page.findInputText(); // find item with auto-suggest behavior
  assertFalse(inputtext.isPopupVisible()); // easily check status of auto-suggest-behavior
  inputtext.typeAndWait("Bla"); // type text in field and wait for auto-suggest to kick in
  assertTrue(inputtext.isPopupVisible()); // auto-suggest should now be shown
  assertEquals(5, inputtext.getSuggestItems().size()); // verify auto-suggest items
  assertEquals("Blake3         Technician", inputtext.getSuggestItems().get(0));
  inputtext.clickSuggestItem(2); // click an item in the auto-suggest list
  assertEquals("Blake81", inputtext.getComponent().getValue()); // af:inputText itself is updated
}
```
Notice there is no low level interaction with html elements. You just write tests against ADF Component Classes provided by this toolkit. For the full example class, see the [original source code](../blob/master/RichClientDemoTest/src/com/redheap/selenium/DemoTest.java)

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

## Running RichClientDemoTest with ADF Faces Component Demo
* This JDeveloper workspace contains a second project; RichClientDemoTest. This is used to test the library itself against the Oracle ADF Faces Component Demo
* Download and alter Oracle's Rich Client Demo application to be used for automated tests. For details see the blog post at http://www.redheap.com/2015/02/adf-faces-12c-components-demo-test-automation.html or simply download and run the `public_html/index.jspx` in these fixed versions from within JDeveloper:
  * [Component demo for 12.1](https://drive.google.com/open?id=0B0EvDYuyTjZzYnlVSjFlYnhucFE)
  * [Component demo for 12.2](https://drive.google.com/open?id=0B0EvDYuyTjZzeHByNW1hZVQtaE0)
* Run individual tests from the RichClientDemoTest project within JDeveloper or run the `junit` ant task in the build.xml to run all tests
  * the ant build task uses a system property to tell it where you have deployed the ADF Faces Component Demo. You might need to change that. All output is written to the `reports` subdirectory in the RichClientDemoTest project. A html report is also included in `reports/html`
  * when running individuel tests within JDeveloper it uses the system property set in the run profiles. Change the settings of the Default Run Profile of the RichClientDemoTest project to the URL where you have deployed the ADF Faces Component Demo

## Using Maven
**WARNING:** this is not completed yet. Still under development
Unfortunately the richclient-automation library needed for testing ADF is not available on a maven repository, not even maven.oracle.com. So the best thing you can do is install this in your local repository:
```
cd $ORACLE_HOME/oracle_common/modules/oracle.adf.view
$ORACLE_HOME/oracle_common/modules/org.apache.maven_3.2.5/bin/mvn install:install-file \
    -Dfile=adf-richclient-automation-11.jar -DgroupId=com.oracle.adf \
    -DartifactId=richclient-automation -Dversion=12.2 -Dpackaging=jar
```

## Rebuild adf-richclient-automation-11.jar with Selenium 3.3.1 and higher versions
Unfortunately the richclient-automation library distributed with JDeveloper does not work with Selenium 3.3.1 and higher versions.

See the problems detales in this stackoverlow question [how-to-get-adf-richclient-automation-11-jar-compatible-with-the-latest-selenium](https://stackoverflow.com/questions/52042307/how-to-get-adf-richclient-automation-11-jar-compatible-with-the-latest-selenium).

So for now you need manualy repackage `adf-richclient-automation-11.jar` with Selenium 3.3.1 and higher versions.

This repository [adf-richclient-automation-selenium-3-rebuild](https://github.com/EgorBEremeev/adf-richclient-automation-selenium-3-rebuild) contains Eclipse Java Project and complete instruction to repackage richclient-automation library with preferable version of Selenium libs.
