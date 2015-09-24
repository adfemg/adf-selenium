package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.pages.AttributeDragSourcePage;

import static org.junit.Assert.*;
import org.junit.Test;


public class AttributeDragSourceTest extends PageTestBase<AttributeDragSourcePage> {

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

    @Override
    protected Class<AttributeDragSourcePage> getPageClass() {
        return AttributeDragSourcePage.class;
    }

    @Override
    protected String getJspxName() {
        return "attributeDragSource.jspx";
    }
}
