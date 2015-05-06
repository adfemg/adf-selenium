package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfTextEditor;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.TextEditorDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class TextEditorTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    private static final String INITIAL_CONTENT =
        "<font color=\"blue\" face=\"Comic Sans MS,Comic Sans,cursive\" size=\"4\">Hello</font> world.<br>This <i>is</i> <b>for<sup>matt</sup>ed</b> text!!!";
    @Rule
    public PageProvider<TextEditorDemoPage> pages =
        new PageProvider(TextEditorDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/richTextEditor.jspx";

    @Test
    public void testContentsTextEditor() {
        AdfTextEditor texteditor = pages.goHome().findTextEditor();
        assertEquals("Rich text value", texteditor.getLabel());
        assertEquals(INITIAL_CONTENT, texteditor.getValue());
    }

    @Test
    public void testToolbarButtons() {
        AdfTextEditor texteditor = pages.goHome().findTextEditor();
        texteditor.findBoldButton().click();
        texteditor.findOrderedListButton().click();
    }

    public static void main(String[] args) {
        String[] args2 = { TextEditorTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
