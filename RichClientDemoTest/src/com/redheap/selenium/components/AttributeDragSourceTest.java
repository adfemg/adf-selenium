package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.AttributeDragSourcePage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;


public class AttributeDragSourceTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<AttributeDragSourcePage> pages =
        new PageProvider(AttributeDragSourcePage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/attributeDragSource.jspx";

    @Test
    public void dragOutputToOutput() {
        AttributeDragSourcePage page = pages.goHome();
        AdfOutputText source = page.findOutputTextDragSource();
        AdfOutputText target = page.findOutputTextDropTarget();
        assertNotNull(source);
        assertNotNull(target);
        assertEquals("Drag Me!", source.getValue());
        assertEquals("Drop Here!", target.getValue());
        source.dragAndDropTo(target);
        assertEquals("Drag Me!", target.getValue());
    }

    @Test
    public void dragInputToOutput() {
        AttributeDragSourcePage page = pages.goHome();
        AdfInputText source = page.findInputTextDragSource();
        AdfOutputText target = page.findOutputTextDropTarget();
        assertNotNull(source);
        assertNotNull(target);
        assertEquals("Now Drag Me!", source.getValue());
        assertEquals("Drop Here!", target.getValue());
        source.dragAndDropTo(target);
        assertEquals("Now Drag Me!", target.getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { AttributeDragSourceTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
