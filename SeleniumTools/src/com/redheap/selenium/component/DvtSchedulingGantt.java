package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DvtSchedulingGantt extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    // some subids return only html tags, some return tags that are full adf components

    private static final String SUBID_zoom_in_button = "toolbar#zoomIn"; // TimeAxes Toolbar Button
    private static final String SUBID_zoom_out_button = "toolbar#zoomOut"; // TimeAxes Toolbar Button
    private static final String SUBID_zoom_to_fit_button = "toolbar#zoomToFit"; // TimeAxes Toolbar Button

    private static final String JS_GET_DATA_TREE_TABLE_ABSOLUTE_ID = 
        JS_FIND_PEER + 
        "return peer.getDataTreeTableId()";
    private static final String JS_GET_CHART_TREE_TABLE_ABSOLUTE_ID = 
        JS_FIND_PEER + 
        "return peer.getChartTreeTableId();";
    private static final String JS_GET_TASK_BAR_ELEMENT = 
        JS_FIND_PEER + 
        "var taskbarelem=peer.getTaskbarElement(arguments[1],arguments[2],\"bar\"); return taskbarelem?taskbarelem :null;";
    private static final String JS_SCROLL_INTO_VIEW = 
        JS_FIND_PEER + 
        "peer.scrollInToView(peer.findElement(arguments[1],'tid'));";
    
    private static final String JS_SELECT_TASK = 
        JS_FIND_PEER + 
        "var taskbarelem=peer.getTaskbarElement(arguments[1],arguments[2],\"bar\");" +
        "var task=peer.findElement(taskbarelem,'tid');" +
        "peer.scrollInToView(task);" +
        "peer.removeSelectedTasks();" +
        "peer.selectTask(taskbarelem);"; 
    /* SUBIDs
     * 
     * [row index][task index]taskbar	A task bar at a specific row with a specific task
     * dataTreeTable[(level 0 index)]...[(level N child index)]disclosureID	Disclosure indicator specified via an array of level based indexes
     * dataTreeTable[(row index)]:(stamp id)	Stamped component at a specific row
     * taskMenu#create	Create menu
     * toolbar#zoomIn	Zoom in button
     * toolbar#zoomOut	Zoom out button
     * toolbar#zoomToFit	Zoom to fit combo
     * splitter	Splitter
     * ___________
     * gantt=AdfPage.PAGE.findComponent("sg1");
     * peer=gantt.getPeer();
     * peer.bind(gantt);
     * peer.getChartTreeTableId();
            "sg1:ctt"
     * peer.getDataTreeTableId();
     *      "sg1:dtt"

     * dtt=AdfPage.PAGE.findComponent("sg1:dtt"); ---> JS_FIND_DATA_TREE_TABLE
     *      _componentType: "oracle.adf.RichTreeTable", _clientId: "sg1:dtt",
     * child=dtt.findComponent("ot4",4); ---> AdfTree.findAdfComponent(String relativeClientId, int rowIndex)
     *      _componentType: "oracle.adf.RichOutputText", _clientId: "sg1:dtt:4:ot4"
     * ___________     
     * subelem=peer.getSubIdDomElement(gantt,"dataTreeTable[1]:ot4"); ---> Оставить без реализации, так как дублируется тем что выше
           <span id=​"sg1:​dtt:​1:​ot4">​21827​</span>​
     * subcomp=AdfRichUIPeer.getFirstAncestorComponent(subelem);
     * ____________
     * peer.getSubIdDomElement(gantt,"resourceMenu"); ---> Оставить без реализации, т.к. у нас не используется
     *      <div id="sg1:_resMenu"
     * peer.getSubIdDomElement(gantt,"taskMenu"); ---> Оставить без реализации, т.к. у нас меню свое вместо стандартного     
     *     <div id="sg1:_taskMenu"  
     * peer.getSubIdDomElement(gantt,"toolbar#zoomIn");
     *     <div id="sg1:_bZi" title="Увеличить"
     *______________
     * 
     * <div id="sg1:ctt:0:tsb" - сторока с bar'ами задач планировщика
     * <div id="sg1:ctt:ROW_ID:tsb"
     * <div tid="1214715" st="1537131600000" et="1537217999000" class="x1l8 x1ps" style="left:319px;height:14px;width:21px;"> - это сам bar
     * tid="TASK_ID из property" 
     * st="время старта в миллисекундах"
     * et="время завершения в миллисекундах"  ---> см. https://currentmillis.com/
     * 
     * 
     * peer.getSubIdDomElement(gantt,"[ROW_ID][tid]taskbar"); - вариант через SubId
     * или
     * peer.getTaskbarElement(0,1214715,"bar"); - вариант через JS API ---> JS_GET_TASK_BAR_ELEMENT, JS_SELECT_TASK
     * ______________1303314
     * peer.getChartRowContextMenuId()
            "p7"
     * 
     * 
     * 
     * peer.getDataTreeTableDomElement();
     * peer.GetBestMatchSubId(peer.getDataTreeTableDomElement());
            "splitter"


     * peer.getSubIdDomElement(gantt,"[0][0]taskbar");
     */

    public DvtSchedulingGantt(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public <T extends AdfComponent> T findDataTreeTable() {
        String clientid =
            (String) executeScript(JS_GET_DATA_TREE_TABLE_ABSOLUTE_ID, getClientId());
        if (clientid == null) {
            return null;
        }
        return AdfComponent.forClientId(getDriver(), clientid);
    }

    public <T extends AdfComponent> T findChartTreeTable() {
        String clientid =
            (String) executeScript(JS_GET_CHART_TREE_TABLE_ABSOLUTE_ID, getClientId());
        if (clientid == null) {
            return null;
        }
        return AdfComponent.forClientId(getDriver(), clientid);
    }

    /*
     *This element represents Gantt Chart Row | <tr role="row" _afrrk="2" aria-level="1" _afrip="2" class="x14o">
     *  and rowKey here is _afrrk="2"
     *                                        |     <td style="padding:0px;" nowrap="" role="gridcell" id="sg1:ctt:2:tsbc" class="xia x1kz">
     *                                        |          <span class="af_column_data-container" style="white-space: nowrap">
     *This element represents Task            |              <div id="sg1:ctt:2:tsb" down="sd" onmousewheel="hmw(event, 'sg1')" resid="32109" style="position:relative;height:44px;width:1820px;">
     *   and rowIndex here is :2:           
     *This element represents TaskBar         |                  <div tid="1210051" st="1536872400000" et="1536958799000"....
     *   and TASK_ID here is tid="1210051"
     *       st="task start time in milliseconds"
     *       et="task end time in milliseconds"  ---> check convertion at https://currentmillis.com/
     *       
     *There are two ways to get these data:
     *  1) peer.getSubIdDomElement(gantt,"[ROW_ID][tid]taskbar"); ---> through SubId
     *  or
     *  2) peer.getTaskbarElement(0,1214715,"bar"); ---> through JS API --> this one is implemanted in this class
     */
    private WebElement findTaskbar (int rowIndex, String taskId) {
        WebElement taskBarElement = (WebElement) executeScript(JS_GET_TASK_BAR_ELEMENT, getClientId(), rowIndex, taskId);
        waitForPpr();
        return taskBarElement;
    }
    
    
    public void taskbarSelect (int rowIndex, String taskId) {
        executeScript(JS_SELECT_TASK, getClientId(), rowIndex, taskId);
        waitForPpr();
    }

    public void taskbarContextClick (int rowIndex, String taskId) {
        WebElement taskBarElement = findTaskbar (rowIndex, taskId);
        executeScript(JS_SCROLL_INTO_VIEW,getClientId(), taskBarElement);
        new Actions(this.getDriver()).contextClick(taskBarElement).perform();
        waitForPpr();
    }    
    
    
    
    public AdfButton findZoomInButton() {
        return findSubIdComponent(SUBID_zoom_in_button);
    }

    public AdfButton findZoomOutButton() {
        return findSubIdComponent(SUBID_zoom_out_button);
    }



    public void ClickZoomIn() {
        findZoomInButton().click();
    }

    public void ClickZoomOutButton() {
        findZoomOutButton().click();
    }

    public AdfSelectOneChoice findZoomToFitButon() {
        return findSubIdComponent(SUBID_zoom_to_fit_button);
    }

}
