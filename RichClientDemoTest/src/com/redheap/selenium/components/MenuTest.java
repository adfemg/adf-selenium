package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCommandMenuItem;
import com.redheap.selenium.component.AdfMenu;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;

import com.redheap.selenium.pages.MenuDemoPage;

import java.io.File;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

public class MenuTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<MenuDemoPage> pages = new PageProvider(MenuDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/menu.jspx";

    @Test
    public void testDisabledMenu() {
        AdfMenu menu = pages.goHome().findDisabledMenu();
        assertTrue(menu.isDisabled());
    }

    @Test
    public void testFileMenu() {
        MenuDemoPage page = pages.goHome();
        AdfMenu menu = page.findFileMenu();
        assertTrue(!menu.isDisabled());
        assertEquals("File", menu.getElement().getText());
        menu.click();
        AdfCommandMenuItem closeItem = page.findCloseItem();
        assertEquals("Close Ctrl+W", closeItem.getElement().getText());
    }

    @Test
    public void testSubMenuNew() {
        MenuDemoPage page = pages.goHome();
        AdfMenu menu = page.findFileMenu();
        menu.click();
        AdfMenu newMenu = page.findNewSubMenu();
        assertEquals("New", newMenu.getElement().getText());
    }
}
