package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCommandMenuItem;
import com.redheap.selenium.component.AdfMenu;
import com.redheap.selenium.pages.MenuDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class MenuTest extends PageTestBase<MenuDemoPage> {

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

    @Override
    protected Class<MenuDemoPage> getPageClass() {
        return MenuDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "menu.jspx";
    }
}
