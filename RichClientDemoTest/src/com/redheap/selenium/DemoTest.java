package com.redheap.selenium;

import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AutoSuggestBehavior;
import com.redheap.selenium.junit.FirefoxDriverResource;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.page.Page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.openqa.selenium.WebDriver;

/**
 * Simple test to demonstrate how easy it is to work with ADF Selenium Tools.
 */
public class DemoTest {

    @ClassRule // starts (and stops) webbrowser before and after test class
    public static WebDriverResource driver = new FirefoxDriverResource();
    @Rule // can be used to navigate to test page and instantiate page object
    public PageProvider<DragDropPage> dragDropPageProvider =
        new PageProvider<DragDropPage>(DragDropPage.class,
                                       "http://127.0.0.1:7101/faces-12.2.1.0.0/faces/components/attributeDragSource.jspx",
                                       driver.getDriver());
    @Rule // can be used to navigate to test page and instantiate page object
    public PageProvider<AutoSuggestPage> autoSuggestPageProvider =
        new PageProvider<AutoSuggestPage>(AutoSuggestPage.class,
                                       "http://127.0.0.1:7101/faces-12.2.1.0.0/faces/components/autoSuggestBehavior.jspx",
                                       driver.getDriver());

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

    /**
     * Page objects know how to locate ADF components on their page and can offer methods that perform
     * complex or compound interactions on the components of this page. This can hide component interaction
     * logic from the actual tests that simple use these Page Objects.
     */
    public static class DragDropPage extends Page {

        public DragDropPage(WebDriver driver) {
            super(driver);
        }

        public AdfInputText findInputTextDragSource() {
            return findAdfComponent("dmoTpl:inputDragSource");
        }

        public AdfOutputText findOutputTextDropTarget() {
            return findAdfComponent("dmoTpl:attributeDropTarget");
        }

        @Override
        protected String getExpectedTitle() {
            return "attributeDragSource demo";
        }
    }

    public static class AutoSuggestPage extends Page {

        public AutoSuggestPage(WebDriver driver) {
            super(driver);
        }

        public AutoSuggestBehavior<AdfInputText> findInputText() {
            return new AutoSuggestBehavior<AdfInputText>(this.<AdfInputText>findAdfComponent("dmoTpl:idInputText"));
        }

        @Override
        protected String getExpectedTitle() {
            return "autoSuggestBehavior Demo";
        }
    }

}
