package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectManyChoice;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.SelectManyChoiceDemoPage;

import java.io.File;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class SelectManyChoiceTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<SelectManyChoiceDemoPage> pages =  new PageProvider(SelectManyChoiceDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/selectManyChoice.jspx";

    @Test
    public void testItemLabels() {
        AdfSelectManyChoice choice = pages.goHome().findDrinksSelectManyChoice();
        assertEquals("coffee", choice.getItemLabel(0));
        assertEquals("tea", choice.getItemLabel(1));
        // item 2 is rendered=false
        assertEquals("fizz", choice.getItemLabel(3));
        // item 4 is rendered=false
        assertEquals("beer", choice.getItemLabel(5));
        assertEquals("lemonade", choice.getItemLabel(6));
    }

    @Test
    public void testSetValue() {
        AdfSelectManyChoice choice = pages.goHome().findDrinksSelectManyChoice();
        choice.clickItemsByIndices(new int[] {1,3});
        choice.click();
        ArrayList clickedItems = (ArrayList)choice.getValue();
        assertEquals(2, clickedItems.size());
        assertEquals("1", clickedItems.get(0));
        assertEquals("3", clickedItems.get(1));
    }

    public static void main(String[] args) {
        String[] args2 = { SelectManyChoiceTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
