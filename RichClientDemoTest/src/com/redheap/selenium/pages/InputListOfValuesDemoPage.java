package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputComboboxListOfValues;
import com.redheap.selenium.component.AdfInputListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

/**
 * Java representation of the adf-richclient-demo/faces/components/inputListOfValues.jspx page.
 */
public class InputListOfValuesDemoPage extends Page {

    public InputListOfValuesDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "inputListOfValues Demo";
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
     * Methods to locate and interract with components in the section f:facet name="seriesComponent"
    */  

    static final String enameInputListOfValues = "dmoTpl:idInputText";

    public AdfInputListOfValues findEname () {
            return findAdfComponent(enameInputListOfValues);
    }
       

    /*
     * Methods to locate and interract with components in the section f:facet name="seriesBelow" -> af:panelFormLayout id="pfl1"
    */  

    static final String empnoInputText = "dmoTpl:it1";

    public AdfInputText findEmpno () {
            return findAdfComponent(empnoInputText);
    }
       
    static final String deptnoInputText = "dmoTpl:it2";

    public AdfInputText findDeptno () {
            return findAdfComponent(deptnoInputText);
    }
       
    static final String hireDateInputText = "dmoTpl:it3";

    public AdfInputText findHireDate () {
            return findAdfComponent(hireDateInputText);
    }
       
    static final String managerInputText = "dmoTpl:it4";

    public AdfInputText findManager () {
            return findAdfComponent(managerInputText);
    }
       
    static final String salaryInputText = "dmoTpl:it5";

    public AdfInputText findSalary () {
            return findAdfComponent(salaryInputText);
    }
       
    static final String commisionInputText = "dmoTpl:it6";

    public AdfInputText findCommision () {
            return findAdfComponent(commisionInputText);
    }
    
}
