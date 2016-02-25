package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.pages.NoteWindowDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteWindowTest extends PageTestBase<NoteWindowDemoPage> {

    @Test
    public void testHoverNoteWindow() {
        NoteWindowDemoPage demoPage = pages.goHome();
        AdfCommandLink link = demoPage.findScavengingLink();
        assertNotNull(demoPage.findScavengingPopup());
        assertFalse(demoPage.findScavengingPopup().isPopupVisible());

        link.hover();
        assertTrue(demoPage.findScavengingPopup().isPopupVisible());
    }

    public static void main(String[] args) {
        String[] args2 = { NoteWindowTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<NoteWindowDemoPage> getPageClass() {
        return NoteWindowDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "noteWindow.jspx";
    }
}
