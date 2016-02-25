package com.redheap.selenium.components;

import com.redheap.selenium.component.uix.UixInput;
import com.redheap.selenium.domain.PageMessageWrapper;
import com.redheap.selenium.pages.InputTextDemoPage;

import java.text.MessageFormat;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Class containing tests related to {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper}.
 * <p>
 * These tests are about handling facesmessages and the convenience methods.
 */
public class PageMessageWrapperTest extends PageTestBase<InputTextDemoPage> {

    /**
     * General test dealing with basic message handling.
     */
    @Test
    public void testGetMessages() {
        InputTextDemoPage page = pages.goHome();
        PageMessageWrapper pageMessageWrapper = page.getAllMessages();
        assertTrue("No messages when page loaded", !pageMessageWrapper.hasMessages());

        //Generate some errors
        UixInput numberInputText = page.findNumberInputText();
        numberInputText.typeValue("Text should give an error");
        UixInput messageExampleInputText = page.findMessageExampleInputText();
        messageExampleInputText.typeValue("fatal");
        // Get the messages again
        pageMessageWrapper = page.getAllMessages();
        assertTrue("Text should give an error", pageMessageWrapper.hasMessages());
        assertTrue("Error text should be 'The number is not a whole number.'",
                   numberInputText.hasMessage("The number is not a whole number."));
        final String ASSERT_HAS_ERROR_TEXT = "Error text should be {0}";
        final String FATAL_ERROR_SUMMARY = "Fatal message SUMMARY text.";
        assertTrue(MessageFormat.format(ASSERT_HAS_ERROR_TEXT, FATAL_ERROR_SUMMARY),
                   messageExampleInputText.hasMessage("oeps" + FATAL_ERROR_SUMMARY));
    }

    public static void main(String[] args) {
        String[] args2 = { PageMessageWrapperTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<InputTextDemoPage> getPageClass() {
        return InputTextDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "inputText.jspx";
    }
}
