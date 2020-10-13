package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.component.AdfTreeTable;
import com.redheap.selenium.component.DvtSchedulingGantt;
import com.redheap.selenium.component.RowInfo;
import com.redheap.selenium.pages.DvtSchedulingGanttDemoPage;
import com.redheap.selenium.pages.TableDetailStampDemoPage;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import org.junit.Test;

import org.openqa.selenium.interactions.Actions;


public class DvtSchedulingGanttTest extends PageTestBase<DvtSchedulingGanttDemoPage> {

    /**Test interractions with sub-components of Scheduling Gantt.
     *  - context menus
     *  - customer list (DataTreeTable)
     *  - tasks (ChartTreeTable, taskbar)
     */
    @Test
    public void testGantt () {
        
        DvtSchedulingGantt gantt = pages.goHome().find_schedulingGant();
        AdfTreeTable dtt=gantt.findDataTreeTable(); // customer list
        AdfTreeTable ctt=gantt.findChartTreeTable(); // tasks chart
        
        //customer row and columns
        dtt.findAdfComponent(DvtSchedulingGanttDemoPage.getdepartmentOutputTextId(), 2).getElement().getText();
        dtt.findAdfComponent(DvtSchedulingGanttDemoPage.getdepartmentOutputTextId(), 2).contextClick();
        
        //experimental. maybe it is possible to use 
//        RowInfo cttRowInfo = ctt.getRowInfo("0");
//        new Actions(driver.getDriver()).contextClick(cttRowInfo.getBlock()).perform();    //падает внутри webdriver
        
        //tasks and task's context menu
        //gantt.findTaskBarElement(0, "5731921");
        gantt.taskbarContextClick(0, "t11");
        gantt.taskbarSelect(1, "t21");

    }


    public static void main(String[] args) {
        String[] args2 = { DvtSchedulingGanttTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<DvtSchedulingGanttDemoPage> getPageClass() {
        return DvtSchedulingGanttDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "schedulingGantt.jspx";
    }
}
