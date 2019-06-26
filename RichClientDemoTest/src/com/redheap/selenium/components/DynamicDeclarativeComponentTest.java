package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfDynamicDeclarativeComponent;
import com.redheap.selenium.component.AdfInputListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfQuery;
import com.redheap.selenium.fragments.DynamicDeclarativeComponentExampleFragment;
import com.redheap.selenium.pages.DeclarativeComponentDemoPage;
import com.redheap.selenium.pages.InputListOfValuesDemoPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class DynamicDeclarativeComponentTest extends PageTestBase<DeclarativeComponentDemoPage> {

    @Test
    public void testInteractionWithDeclarativeComponent() {
        DeclarativeComponentDemoPage dcdPage = pages.goHome();
        dcdPage.find_it1().typeValue("testInteractionWithDeclarativeComponent");
        dcdPage.find_nameTypeChoice().clickItemByIndex(1);
        dcdPage.find_attributeUpdateButton().click();

        DynamicDeclarativeComponentExampleFragment lwdcFragment = dcdPage.get_lwdcDeclarativeComponentContent();

        assertEquals("Test value does not pass into Declarative Component", "testInteractionWithDeclarativeComponent",
                     lwdcFragment.find_lwdcInputText().getContent());
        assertEquals("Array item value does not pass to Declarative Component", "Item A",
                     lwdcFragment.find_itemsText(0).getContent());


    }


    public static void main(String[] args) {
        String[] args2 = { DynamicDeclarativeComponentTest.class.getName() };
        org.junit
           .runner
           .JUnitCore
           .main(args2);
    }

    @Override
    protected Class<DeclarativeComponentDemoPage> getPageClass() {
        return DeclarativeComponentDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "declarativeComponent.jspx";
    }
}
