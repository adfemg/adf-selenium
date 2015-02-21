package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfTable extends AdfComponent {

    public AdfTable(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichTable";
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, String rowKey, Class<? extends T> cls) {
        String js =
            String.format("%s.findComponent('%s', '%s').getClientId()", scriptFindComponent(), relativeClientId,
                          rowKey);
        String clientid = (String) executeScript(js);
        return AdfComponent.forClientId(getDriver(), clientid, cls);
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, int rowIndex, Class<? extends T> cls) {
        scrollToRowIndex(rowIndex); // scroll to row (and possibly fetch additional data)
        String js =
            String.format("%s.findComponent('%s', %s.getRowKey(%d)).getClientId()", scriptFindComponent(),
                          relativeClientId, scriptBoundPeer(), rowIndex);
        String clientid = (String) executeScript(js);
        T retval = AdfComponent.forClientId(getDriver(), clientid, cls);
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
        String js = String.format("%s.isDisclosed('%s')", scriptFindComponent(), rowKey);
        return (Boolean) executeScript(js);
    }

    public void scrollToRowIndex(int rowIndex) {
        // could also accept a callback function
        String js = String.format("%s.scrollToRowIndex(%d)", scriptFindComponent(), rowIndex);
        executeScript(js);
        waitForPpr();
    }

    public String getFocusedRowKey() {
        String js = String.format("%s.GetFocusedRowKey()", scriptBoundPeer());
        return (String) executeScript(js);
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
        String js = String.format("%s.getRowKey(%d)", scriptBoundPeer(), index);
        return (String) executeScript(js);
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
        String js = String.format("%s.getRowIndex('%s')", scriptBoundPeer(), rowKey);
        return ((Number) executeScript(js)).intValue();
    }

}
