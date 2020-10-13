package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputComboboxListOfValues;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

/**
 * Java representation of the adf-richclient-demo/faces/components/inputComboboxListOfValues.jspx page.
 */
public class InputComboboxListOfValuesDemoPage extends Page {

    public InputComboboxListOfValuesDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "inputComboboxListOfValues Demo";
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
     * Methods to locate and interract with components in section f:facet name="seriesComponent"
    */  

    static final String _idInputComboboxListOfValues = "dmoTpl:idInputComboboxListOfValues";

    public AdfInputComboboxListOfValues find_idInputComboboxListOfValues () {
            return findAdfComponent(_idInputComboboxListOfValues);
    }
       

    /*
     * Methods to locate and interract with components in section f:facet name="seriesBelow"
    */  

    static final String _comboLOVwithlistener = "dmoTpl:comboLOVwithlistener";

    public AdfInputComboboxListOfValues find_comboLOVwithlistener () {
            return findAdfComponent(_comboLOVwithlistener);
    }
       
    static final String _idInputComboboxListOfValues2 = "dmoTpl:idInputComboboxListOfValues2";

    public AdfInputComboboxListOfValues find_idInputComboboxListOfValues2 () {
            return findAdfComponent(_idInputComboboxListOfValues2);
    }
       
    static final String _comboWithResultsTableFacet = "dmoTpl:comboWithResultsTableFacet";

    public AdfInputComboboxListOfValues find_comboWithResultsTableFacet () {
            return findAdfComponent(_comboWithResultsTableFacet);
    }

}
