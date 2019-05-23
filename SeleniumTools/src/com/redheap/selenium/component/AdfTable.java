package com.redheap.selenium.component;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.internal.JsonToWebElementConverter;

public class AdfTable extends AdfComponent {

    private static final String SUBID_disclosureID = "disclosureID"; // [99]disclosureId returns <a> element

    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY =
        JS_FIND_COMPONENT +
        "var child=comp.findComponent(arguments[1],arguments[2]); return child?child.getClientId():null";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX =
        JS_FIND_PEER +
        "var idx=peer.getRowKey(arguments[2]); if (!idx){return null}var child=comp.findComponent(arguments[1],idx); return child?child.getClientId():null";
    private static final String JS_IS_ROW_DISCLOSED =
        JS_FIND_COMPONENT + "return comp.isDisclosed(arguments[1])===true";
    private static final String JS_SCROLLTO_ROWINDEX = JS_FIND_COMPONENT + "comp.scrollToRowIndex(arguments[1])";
    private static final String JS_GET_FOCUSED_ROWKEY = JS_FIND_PEER + "return peer.GetFocusedRowKey()";
    private static final String JS_GET_ROWKEY = JS_FIND_PEER + "return peer.getRowKey(arguments[1])";
    private static final String JS_GET_ROWINDEX = JS_FIND_PEER + "return peer.getRowIndex(arguments[1])";
    private static final String JS_SELECTED_ROWS =
        JS_FIND_PEER +
        "var keys=Object.keys(comp.getSelectedRowKeys()); var retval=[]; keys.forEach(function(key){retval.push(peer.getRowIndex(key))}); return retval;";
    private static final String JS_DISCLOSED_ROWS =
        JS_FIND_PEER +
        "var keys=Object.keys(comp.getDisclosedRowKeys()); var retval=[]; keys.forEach(function(key){retval.push(peer.getRowIndex(key))}); return retval;";
    private static final String JS_GET_ROW_BY_INDEX =
        JS_FIND_PEER +
        "var rowinfo=peer.FindRowByKey(peer.getRowKey(arguments[1])); if (rowinfo){return rowinfo.tr}else{return null}"; //Fixed defect: was peer.getRowIndex instead of peer.getRowKey
    private static final String JS_GET_COLUMN_INDEX_BY_ID =
            JS_FIND_PEER + 
            "getColumnIndexById = function(cId) {\n" + 
            "    var retval;\n" + 
            "    if (!peer._headerless) {\n" + 
            "        var d = peer.getDomElement();\n" + 
            "        d && (d = AdfAgent.AGENT.getAttribute(d, \"_leafColClientIds\"),\n" + 
            "        d = eval(d))\n" + 
            "        if (d) {\n" + 
            "            for (var h = 0; h < d.length && !retval; h++) {\n" + 
            "                if (d[h].indexOf(cId) !== -1 ) {\n" + 
            "                    retval = h;\n" + 
            "                    break\n" + 
            "                }\n" + 
            "            }\n" + 
            "        }\n" + 
            "    }\n" + 
            "    return retval\n" + 
            "};" +
            "return getColumnIndexById(arguments[1]);";
        
    
    private static final String JS_GET_ROW_BY_COLUMN_VALUE =
        JS_FIND_PEER +
        "FindRowByColumnValue = function(cIdx, cVal) {\n" + 
        "    for (var retval = null, d = peer.GetDataBody().childNodes, e = 0; e < d.length && !retval; e++) {\n" + 
        "        var f = d[e]\n" + 
        "          , g = f.rows;\n" + 
        "        if (g)\n" + 
        "            for (var h = 0; h < g.length && !retval; h++) {\n" + 
        "                var k = g[h];\n" + 
        "                if (cIdx && k.childNodes[cIdx].textContent.indexOf(cVal) !== -1) {\n" + 
        "                    retval = {\n" + 
        "                        tr: k,\n" + 
        "                        index: f.startRow + h,\n" + 
        "                        block: f\n" + 
        "                    };\n" + 
        "                    break\n" + 
        "                }\n" + 
        "            }\n" + 
        "    }\n" + 
        "    return retval\n" + 
        "};" +
        "return FindRowByColumnValue(arguments[1],arguments[2]);";
    
    private static final String JS_GET_ROW_COUNT = JS_FIND_PEER + "return peer.GetRowCount();";
    private static final String JS_IS_EMPTY = JS_FIND_COMPONENT + "return comp.isEmpty();";

    public AdfTable(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, String rowKey) {
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY, getClientId(), relativeClientId, rowKey);
        if (clientid == null) {
            return null;
        }
        return AdfComponent.forClientId(getDriver(), clientid);
    }

    /**
     * @param <T>
     * @param relativeClientId
     * @param rowIndex
     * @return
     * see https://docs.oracle.com/middleware/1221/adf/api-reference-javascript-faces/org/apache/myjs/trinidad/component/AdfUITable.html
     * AdfUITable provides this overload of AdfUIComponent.findComponent() which takes an (optional) row key. 
     * When the row key is specified, AdfUITable.findComponent() returns the stamped component instance for the specified row. 
     * Note that the actual row key value may be transformed by the rich client framework such that the client-side value of
     * the row key may not have any resemblance to the server-side row key. 
     * As such, only row keys served up by the client-side framework (eg. row keys obtained from selection events)
     * should be passed to AdfUITable.findComponent().
     */
    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, int rowIndex) {
        scrollToRowIndex(rowIndex); // scroll to row (and possibly fetch additional data)
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX, getClientId(), relativeClientId,
                                   rowIndex);
        if (clientid == null) {
            return null;
        }
        T retval = AdfComponent.forClientId(getDriver(), clientid);
        // TODO; would be niced to use TablePeer.scrollColumnIntoView but we haven't figured out a way to
        // determine column index for a component
        retval.scrollIntoView();
        return retval;
    }
    
    public boolean isEmpty () {
        return (Boolean) executeScript(JS_IS_EMPTY, getClientId());
    }
    
    public List<Integer> getSelectedRows() {
        return rowIndices((List<Long>) executeScript(JS_SELECTED_ROWS, getClientId()));
    }

    public List<Integer> getDisclosedRows() {
        return rowIndices((List<Long>) executeScript(JS_DISCLOSED_ROWS, getClientId()));
    }

    private List<Integer> rowIndices(List<Long> longs) {
        List<Integer> retval = new ArrayList<Integer>(longs.size());
        for (Long l : longs) {
            retval.add(l.intValue());
        }
        return retval;
    }

    public boolean isRowDisclosed(int rowIndex) {
        scrollToRowIndex(rowIndex);
        String rowkey = getRowKey(rowIndex);
        return (Boolean) executeScript(JS_IS_ROW_DISCLOSED, getClientId(), rowkey);
    }

    public void discloseRowDetail(int rowIndex) {
        scrollToRowIndex(rowIndex); // scroll the table rows
        WebElement link = findDisclosureLink(rowIndex);
        if (link != null) {
            scrollIntoView(link); // scroll the browser document
            link.click();
            waitForPpr();
        }
    }

    public void scrollToRowIndex(int rowIndex) {
        // could also accept a callback function
        executeScript(JS_SCROLLTO_ROWINDEX, getClientId(), rowIndex);
        waitForPpr();
    }

    public String getFocusedRowKey() {
        return (String) executeScript(JS_GET_FOCUSED_ROWKEY, getClientId());
    }

    /**
     * Returns the rowKey to identify a row based on its index. There is no guarantee that a row identified by a
     * rowKey can be found on the client since all the rows are not rendered on the client. Also keep in mind that
     * the rowKey on the client is not the same as the one on the server, since the server provides a mechanism for
     * converting client row keys into server row keys.
     * @param index the rowIndex of the row
     * @return the rowKey to identify a row if found otherwise null
     */
    public String getRowKey(int index) {
        return (String) executeScript(JS_GET_ROWKEY, getClientId(), index);
    }

    /**
     * Returns the index of a row identified by rowKey. There is no guarantee that a row identified by a
     * rowKey can be found on the client since all the rows are not rendered on the client. Also keep in mind that
     * the rowKey on the client is not the same as the one on the server, since the server provides a mechanism for
     * converting client row keys into server row keys.
     * @param rowKey the client side rowKey that identifies the row
     * @return the index of a row for a row key if it is found otherwise -1
     */
    public int getRowIndex(String rowKey) {
        return ((Number) executeScript(JS_GET_ROWINDEX, getClientId(), rowKey)).intValue();
    }

    public long getRowCount() {
        return ((Number) executeScript(JS_GET_ROW_COUNT, getClientId())).longValue();
    }

    public void selectRow(int index) {
        scrollToRowIndex(index);
        WebElement row = findRow(index);
        row.click();
        waitForPpr();
    }
    
    public void selectRow(String columnComponentId, String cellValue) {
        Map<?, ?> rowinfo = (Map<?, ?>) findRowWithInfo(columnComponentId, cellValue);
        assertNotNull("задача отсутсвует в task list", rowinfo);
        Long index = (Long)rowinfo.get("index");
        scrollToRowIndex(index.intValue());
        WebElement row = (WebElement)rowinfo.get("tr");
        row.click();
        waitForPpr();
    }
    
    public boolean checkFindRow(String columnComponentId, String cellValue) {
        Map<?, ?> rowinfo = (Map<?, ?>) findRowWithInfo(columnComponentId, cellValue);
        if(rowinfo == null) 
            return false;
        else
            return true;
    }

    public void selectToRow(int index) {
        scrollToRowIndex(index);
        WebElement row = findRow(index);
        new Actions(getDriver()).keyDown(Keys.SHIFT).click(row).keyUp(Keys.SHIFT).perform();
        waitForPpr();
    }

    public void selectAdditionalRow(int index) {
        scrollToRowIndex(index);
        WebElement row = findRow(index);
        final Keys key = isPlatform(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        new Actions(getDriver()).keyDown(key).click(row).keyUp(key).perform();
        waitForPpr();
    }

    protected WebElement findDisclosureLink(int index) {
        // warning: when a row is disclosed all rows lower in the tabel cannot be found due to a bug in
        // AdfDhtmlTableBasePeer.convertLocatorToClientId which doesn't account for the extra <tr> row
        // injected by the disclosed detailStamp
        return findSubIdElement("[" + index + "]" + SUBID_disclosureID);
    }

    public WebElement findRow(int index) {
        return (WebElement) executeScript(JS_GET_ROW_BY_INDEX, getClientId(), String.valueOf(index));
    }
    
    public WebElement findRow(String columnComponentId, String cellValue){
        Map<?, ?> rowinfo = (Map<?, ?>) findRowWithInfo(columnComponentId, cellValue);
        assertNotNull("задача отсутсвует в task list", rowinfo);
        return (WebElement) rowinfo.get("tr");
    }
    
    /**
     * @param columnComponentId
     * @param cellValue - row will be selected by matching this value and value in the cell in the column {@code columnComponentId}
     * @return
     */
    protected Object findRowWithInfo(String columnComponentId, String cellValue) {
        int cIdx = getColumnIndex(columnComponentId);
        return executeScript(JS_GET_ROW_BY_COLUMN_VALUE, getClientId(), cIdx, cellValue);
    }
    
    /**
     * @param columnComponentId
     * @return index of table colunm based on {@param columnComponentId}
     */
    protected int getColumnIndex(String columnComponentId) {
        return ((Number) executeScript(JS_GET_COLUMN_INDEX_BY_ID, getClientId(), columnComponentId)).intValue();
    }


    public void getRowCount(Integer contractNameRow) {
    }
}
