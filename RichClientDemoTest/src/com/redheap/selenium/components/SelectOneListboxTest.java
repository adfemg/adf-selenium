package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectOneListbox;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;

import com.redheap.selenium.pages.SelectOneListboxDemoPage;

import java.io.File;

import static org.junit.Assert.assertEquals;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class SelectOneListboxTest {
    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<SelectOneListboxDemoPage> pages = new PageProvider(SelectOneListboxDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/selectOneListbox.jspx";

    @Test
    public void testItemLabels() {
        AdfSelectOneListbox listbox = pages.goHome().findDrinksSelectOneListbox();
        assertEquals("coffee", listbox.getItemLabel(0));
        assertEquals("tea;green", listbox.getItemLabel(1));
        //disabled items below.
        assertEquals("orange juice", listbox.getItemLabel(2));
        assertEquals("wine", listbox.getItemLabel(3));
        assertEquals("milk;skim", listbox.getItemLabel(4));

    }

    @Test
    public void testLabelLookup() {
        AdfSelectOneListbox choice = pages.goHome().findDrinksSelectOneListbox();
        assertEquals(0, choice.getValueByLabel("coffee"));
        assertEquals(1, choice.getValueByLabel("tea;green"));
        assertEquals(2, choice.getValueByLabel("orange juice"));
        assertEquals(3, choice.getValueByLabel("wine"));
        assertEquals(4, choice.getValueByLabel("milk;skim"));
    }

    @Test
    public void testSetValue() {
        AdfSelectOneListbox listbox = pages.goHome().findDrinksSelectOneListbox();
        listbox.clickItemByIndex(1);
        assertEquals("tea;green", listbox.getValueLabel());
        assertEquals("1", listbox.getValue());
        listbox.clickItemByLabel("milk;skim");
        assertEquals("milk;skim", listbox.getValueLabel());
        assertEquals("4", listbox.getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { SelectOneListboxTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }
}
