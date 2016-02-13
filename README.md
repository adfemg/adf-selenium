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
* Optionally change `com/redheap/selenium/components/PageTestBase.java`
  * Replace `new PhantomJSDriverResource()` with `new FirefoxDriverResource()` to use Firefox instead of PhantomJS if you want to see what is going on in the browser
