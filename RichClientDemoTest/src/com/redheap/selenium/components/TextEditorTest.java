package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfTextEditor;
import com.redheap.selenium.pages.TextEditorDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

import org.openqa.selenium.Keys;

public class TextEditorTest extends PageTestBase<TextEditorDemoPage> {

    private static final String INITIAL_CONTENT =
        "<font color=\"blue\" face=\"Comic Sans MS,Comic Sans,cursive\" size=\"4\">Hello</font> world.<br>This <i>is</i> <b>for<sup>matt</sup>ed</b> text!!!";

    @Test
    public void testContentsTextEditor() {
        final AdfTextEditor texteditor = pages.goHome().findTextEditor();
        assertEquals("Rich text value", texteditor.getLabel());
        assertEquals(INITIAL_CONTENT, texteditor.getValue());

        texteditor.typeValue("");
        assertEquals("", texteditor.getValue());
        texteditor.typeValue("This is plain text");
        texteditor.sendKeys(Keys.ENTER);
        assertEquals("This is plain text<br>", texteditor.getValue());
        texteditor.typeValue("");
        texteditor.sendKeys("This is ");
        texteditor.findBoldButton().click();
        texteditor.sendKeys("BOLD");
        texteditor.tabNext();
        String value = (String) texteditor.getValue();
        if (value.contains("<span")) {
            // ADF 12.1.3.x uses <span> tags
            assertEquals("This is <span style=\"font-weight: bold;\">BOLD</span><br>", value);
        } else {
            assertEquals("This is <b>BOLD</b><br>", value);
        }

        texteditor.findEditorModeSourceButton().click();

        texteditor.typeValue("");
        assertEquals("", texteditor.getValue());
        texteditor.typeValue("Test54321");
        assertEquals("Test54321", texteditor.getValue());
    }

    @Test
    public void testToolbarButtons() {
        final AdfTextEditor texteditor = pages.goHome().findTextEditor();
        texteditor.findBoldButton().click();
        texteditor.findOrderedListButton().click();
    }

    public static void main(String[] args) {
        String[] args2 = { TextEditorTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<TextEditorDemoPage> getPageClass() {
        return TextEditorDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "richTextEditor.jspx";
    }
}
