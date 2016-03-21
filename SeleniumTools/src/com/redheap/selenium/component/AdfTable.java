package com.redheap.selenium.component;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
        "var rowinfo=peer.FindRowByKey(peer.getRowIndex(arguments[1])); if (rowinfo){return rowinfo.tr}else{return null}";
    private static final String JS_GET_ROW_COUNT = JS_FIND_PEER + "return peer.GetRowCount();";

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
        Number num = ((Number) executeScript(JS_GET_ROW_COUNT, getClientId()));
        if (num==null) {
            return 0;
        }
        return num.longValue();
    }

    public void selectRow(int index) {
        scrollToRowIndex(index);
        WebElement row = findRow(index);
        row.click();
        waitForPpr();
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

    protected WebElement findRow(int index) {
        return (WebElement) executeScript(JS_GET_ROW_BY_INDEX, getClientId(), String.valueOf(index));
    }

}
