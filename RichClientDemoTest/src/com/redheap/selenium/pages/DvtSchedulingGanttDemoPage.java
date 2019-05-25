package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfButton;
import com.redheap.selenium.component.AdfColumn;
import com.redheap.selenium.component.AdfCommandMenuItem;
import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfPanelGroupLayout;
import com.redheap.selenium.component.AdfPopup;
import com.redheap.selenium.component.DvtSchedulingGantt;
import com.redheap.selenium.dialogs.NewFileWindowDialog;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DvtSchedulingGanttDemoPage extends Page {

    public DvtSchedulingGanttDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "SchedulingGantt";
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
     * !-- START TAG DEMO -->
     * Methods to locate and interract with dvt:schedulingGantt demo component
    */

    static final String _schedulingGantSchedulingGantt = "demo:schedulingGant";

    public DvtSchedulingGantt find_schedulingGant() {
        return findAdfComponent(_schedulingGantSchedulingGantt);
    }

    static final String _ta1TimeAxis = "demo:schedulingGant:ta1";

    /*
     * If you need to interract with time axis in you test you need to implement classes for AdfTimeAxis in SeleniumTools set
    public DvtTimeAxis find_ta1() {
        return findAdfComponent(_ta1TimeAxis);
    }

    static final String _ta2TimeAxis = "demo:schedulingGant:ta2";

    public DvtTimeAxis find_ta2() {
        return findAdfComponent(_ta2TimeAxis);
    }
    */
    static final String _c1Column = "demo:schedulingGant:c1";

    public AdfColumn find_c1() {
        return findAdfComponent(_c1Column);
    }

    /*
     * Methods to locate stamped-components in the columns of SchedulingGantt
    */

    static final String resourceNameOutputTextId = "ot1";

    public static final String getresourceNameOutputTextId() {
        return resourceNameOutputTextId;
    }

    static final String _c2Column = "demo:schedulingGant:c2";

    public AdfColumn find_c2() {
        return findAdfComponent(_c2Column);
    }

    /*
     * Methods to locate stamped-components in the columns of SchedulingGantt
    */

    static final String departmentOutputTextId = "ot2";

    public static final String getdepartmentOutputTextId() {
        return departmentOutputTextId;
    }

}
