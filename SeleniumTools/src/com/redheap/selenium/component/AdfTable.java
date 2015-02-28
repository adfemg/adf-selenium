package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfTable extends AdfComponent {

    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY =
        JS_FIND_COMPONENT + "return comp.findComponent(arguments[1],arguments[2]).getClientId()";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX =
        JS_FIND_PEER + "return comp.findComponent(arguments[1],peer.getRowKey(arguments[2])).getClientId()";
    private static final String JS_IS_ROW_DISCLOSED = JS_FIND_COMPONENT + "comp.isDisclosed(arguments[1])";
    private static final String JS_SCROLLTO_ROWINDEX = JS_FIND_COMPONENT + "comp.scrollToRowIndex(arguments[1])";
    private static final String JS_GET_FOCUSED_ROWKEY = JS_FIND_PEER + "return peer.GetFocusedRowKey()";
    private static final String JS_GET_ROWKEY = JS_FIND_PEER + "return peer.getRowKey(arguments[1])";
    private static final String JS_GET_ROWINDEX = JS_FIND_PEER + "return peer.getRowIndex(arguments[1])";

    public AdfTable(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, String rowKey) {
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY, getClientId(), relativeClientId, rowKey);
        return AdfComponent.forClientId(getDriver(), clientid);
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, int rowIndex) {
        scrollToRowIndex(rowIndex); // scroll to row (and possibly fetch additional data)
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX, getClientId(), relativeClientId,
                                   rowIndex);
        T retval = AdfComponent.forClientId(getDriver(), clientid);
        // TODO; would be niced to use TablePeer.scrollColumnIntoView but we haven't figured out a way to
        // determine column index for a component
        retval.scrollIntoView();
        return retval;
    }

    //    public ?? getSelectedRowKeys() {
    //        // TODO: returns something like {0:true} when single row is selected
    //        String js = String.format("%s.getSelectedRowKeys()", scriptFindComponent());
    //        return (String) executeScript(js);
    //    }

    //    public ?? getDisclosedRowKeys() {
    //        // TODO: returns something like {0:true} when single row is selected
    //        String js = String.format("%s.getDisclosedRowKeys()", scriptFindComponent());
    //        return (String) executeScript(js);
    //    }

    public boolean isRowDisclosed(String rowKey) {
        return (Boolean) executeScript(JS_IS_ROW_DISCLOSED, getClientId(), rowKey);
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

}
