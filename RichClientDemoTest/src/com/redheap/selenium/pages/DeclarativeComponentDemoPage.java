package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCommandButton;
import com.redheap.selenium.component.AdfDynamicDeclarativeComponent;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfSelectOneChoice;
import com.redheap.selenium.fragments.DynamicDeclarativeComponentExampleFragment;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

/**
 * Java representation of the adf-richclient-demo/faces/components/declarativeComponent.jspx page.
 */
public class DeclarativeComponentDemoPage extends Page {

    public DeclarativeComponentDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "Dynamic Declarative Component Demo";
    }

    /*
     * XSLT Generated
     * This is Page Object Model for the tested ADF Page.
     * The model includes java code for those of Adf Faces Tag which are listed xsl:template match=. You can change this list according to your needs and re-generate code for this class.
     * Generated code includes:
     *      - String constant with ADF component AbsoluteId value. This constant then is used to locate component by corresponding findAdfComponent() method of SeleniumTools library.
     *      - find-method which returm object instance representing ADF component.
     * См.
     * https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/oracle/adf/view/js/base/AdfPage.html#findComponentByAbsoluteId_String_
     * https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/oracle/adf/view/js/component/AdfUIComponent.html#findComponent_String__Boolean_
    */


    /*
     * Methods to locate and interract with components in the section f:facet name="first"
    */

    static final String _it1InputText = "dmoTpl:it1";

    public AdfInputText find_it1() {
        return findAdfComponent(_it1InputText);
    }

    static final String _nameTypeChoiceSelectOneChoice = "dmoTpl:nameTypeChoice";

    public AdfSelectOneChoice find_nameTypeChoice() {
        return findAdfComponent(_nameTypeChoiceSelectOneChoice);
    }

    static final String _attributeUpdateButtonCommandButton = "dmoTpl:attributeUpdateButton";

    public AdfCommandButton find_attributeUpdateButton() {
        return findAdfComponent(_attributeUpdateButtonCommandButton);
    }

    static final String _lwdcDeclarativeComponent = "dmoTpl:lwdc";

    public AdfDynamicDeclarativeComponent find_lwdc() {
        return findAdfComponent(_lwdcDeclarativeComponent);
    }

    public DynamicDeclarativeComponentExampleFragment get_lwdcDeclarativeComponentContent() {
        //Add here code for fragment's initialization action on you page. Like: findOrderListTab().click();
        //If declarative component rendered with load page itself then nothing additional code is needed 
        return new DynamicDeclarativeComponentExampleFragment(find_lwdc());
    }

}
