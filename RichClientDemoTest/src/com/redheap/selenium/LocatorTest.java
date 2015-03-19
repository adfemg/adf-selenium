package com.redheap.selenium;

import com.redheap.selenium.component.AdfDynamicDeclarativeComponent;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.DeclarativeComponentPage;
import com.redheap.selenium.pages.EditableTablePage;

import java.io.File;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Test component lookups by relative locators.
 */
public class LocatorTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<EditableTablePage> tablePage = new PageProvider(EditableTablePage.class, TABLE_PAGE, driver.getDriver());
    @Rule
    public PageProvider<DeclarativeComponentPage> declCompPage = new PageProvider(DeclarativeComponentPage.class, DECL_COMP_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    //private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo/faces/components/table/editableTable.jspx";
    private static final String TABLE_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/table/editableTable.jspx";
    private static final String DECL_COMP_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/declarativeComponent.jspx";
    private static final Logger logger = Logger.getLogger(EditableTableTest.class.getName());

    @Test
    public void testAbsoluteLocator() {
        EditableTablePage page = tablePage.goHome();
        AdfTable table = page.findTable();
        AdfInputText target = table.findAdfComponentByLocator(":dmoTpl:table1[0]:it2");
        assertEquals("dmoTpl:table1[0]:it2", target.getAbsoluteLocator());
        assertEquals("dmoTpl:table1:0:it2", target.getClientId());
    }

    @Test
    public void testRelativeLocatorFromNonNamingContainer() {
        EditableTablePage page = tablePage.goHome();
        AdfTable table = page.findTable();
        AdfInputText base = table.findAdfComponentByLocator(":dmoTpl:table1[0]:it2");
        AdfInputText target = base.findAdfComponentByLocator("it3");
        assertEquals("dmoTpl:table1[0]:it3", target.getAbsoluteLocator());
        assertEquals("dmoTpl:table1:0:it3", target.getClientId());
    }

    @Test
    public void testRelativeLocatorFromNamingContainer() {
        EditableTablePage page = tablePage.goHome();
        AdfTable table = page.findTable();
        AdfDynamicDeclarativeComponent namingContainer = table.findAdfComponentByLocator(":dmoTpl:gTools:glryFind");
        AdfInputText target = namingContainer.findAdfComponentByLocator("findIt");
        assertEquals("dmoTpl:gTools:glryFind:findIt", target.getAbsoluteLocator());
        assertEquals("dmoTpl:gTools:glryFind:findIt", target.getClientId());
    }

    @Test
    public void testRelativeDifferentIndexLocator() {
        EditableTablePage page = tablePage.goHome();
        AdfTable table = page.findTable();
        AdfInputText base = table.findAdfComponentByLocator(":dmoTpl:table1[0]:it2");
        AdfInputText target = base.findAdfComponentByLocator("::[1]:it3");
        assertEquals("dmoTpl:table1[1]:it3", target.getAbsoluteLocator());
        assertEquals("dmoTpl:table1:1:it3", target.getClientId());
    }

    @Test
    public void testIndexLocator() {
        EditableTablePage page = tablePage.goHome();
        AdfTable table = page.findTable();
        AdfInputText target = table.findAdfComponentByLocator("[2]:it2");
        assertEquals("dmoTpl:table1[2]:it2", target.getAbsoluteLocator());
        assertEquals("dmoTpl:table1:2:it2", target.getClientId());
    }

    @Test
    public void testNonExistingChild() {
        EditableTablePage page = tablePage.goHome();
        AdfTable table = page.findTable();
        assertNull(table.findAdfComponent("foo"));
    }

    @Test
    public void testDeclarativeComponentLocator() {
        DeclarativeComponentPage page = declCompPage.goHome();
        AdfDynamicDeclarativeComponent declComp = page.findDeclarativeComponent();
        AdfOutputText target = declComp.findAdfComponentByLocator("dcot1");
        assertEquals("dmoTpl:lwdc:dcot1", target.getAbsoluteLocator());
        assertEquals("dmoTpl:lwdc:dcot1", target.getClientId());
        // af:ierator does not exist at client, so simple iter:0:item and not iter[0]:item
        AdfInputText it0 = declComp.findAdfComponentByLocator("dci1:0:itemsText");
        assertEquals("dmoTpl:lwdc:dci1:0:itemsText", it0.getAbsoluteLocator());
        assertEquals("dmoTpl:lwdc:dci1:0:itemsText", it0.getClientId());
        AdfInputText it2 = declComp.findAdfComponentByLocator("dci1:2:itemsText");
        assertEquals("dmoTpl:lwdc:dci1:2:itemsText", it2.getAbsoluteLocator());
        assertEquals("dmoTpl:lwdc:dci1:2:itemsText", it2.getClientId());
        // facet content
        AdfInputText fit = declComp.findAdfComponentByLocator("facetInputText");
        assertEquals("dmoTpl:lwdc:facetInputText", fit.getAbsoluteLocator());
        assertEquals("dmoTpl:lwdc:facetInputText", fit.getClientId());
    }

    public static void main(String[] args) {
        String[] args2 = { LocatorTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}


