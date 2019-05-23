package com.redheap.selenium.component;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfTree extends AdfComponent {

    
    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_disclosureID = "disclosureID";

    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY =
        JS_FIND_COMPONENT +
        "var child=comp.findComponent(arguments[1],arguments[2]); return child?child.getClientId():null";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX =
        JS_FIND_PEER +
        "var idx=peer.getRowKey(arguments[2]); if (!idx){return null}var child=comp.findComponent(arguments[1],idx); return child?child.getClientId():null";

    private static final String JS_EXPANDED_NODE_COUNT =
        JS_FIND_COMPONENT + "return Object.keys(comp.getDisclosedRowKeys()).length";
    private static final String JS_GET_FOCUSED_ROWKEY = JS_FIND_PEER + "return peer.GetFocusedRowKey()";
    private static final String JS_GET_SELECTED_ROWKEYS =
        JS_FIND_COMPONENT + "return Object.keys(comp.getSelectedRowKeys())";
    private static final String JS_GET_DISCLOSED_ROWKEYS =
        JS_FIND_COMPONENT + "return Object.keys(comp.getDisclosedRowKeys())";
    private static final String JS_GET_DEPTH_ROWKEY = JS_FIND_PEER + "return peer.getDepth(arguments[1])";
    private static final String JS_ISLEAF_ROWKEY = JS_FIND_PEER + "return peer.isLeaf(arguments[1])";
    private static final String JS_FIND_ROWKEY_BY_INDEXPATH =
        JS_FIND_PEER + "peer._findRowKeyByIndexPath(arguments[1])";
    private static final String JS_GET_ROWINFO =
        JS_FIND_PEER + "var rowinfo=peer.FindRowByKey(arguments[1]);" +
        "return [rowinfo.tr,rowinfo.index,rowinfo.block];";
    private static final String JS_GET_DISCLOSURE_ICON =
        JS_FIND_PEER + "return peer._getDisclosureIconInCell(arguments[1]);";
    private static final String JS_GET_ROW_KEY =
        JS_FIND_PEER + "var rowkeyandrow=peer.GetRowKeyAndRow(arguments[1]); return rowkeyandrow&&rowkeyandrow[0];";
    private static final String JS_SCROLLTO_ROWINDEX = JS_FIND_COMPONENT + "comp.scrollToRowIndex(arguments[1])";
    

    public AdfTree(WebDriver driver, String clientid) {
        super(driver, clientid);
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
    
    public void scrollToRowIndex(int rowIndex) {
        // could also accept a callback function
        executeScript(JS_SCROLLTO_ROWINDEX, getClientId(), rowIndex);
        waitForPpr();
    }

    public int getExpandedNodeCount() {
        return ((Number) executeScript(JS_EXPANDED_NODE_COUNT, getClientId())).intValue();
    }

    public void clickNode(By locator) {
        getElement().findElement(locator).findElement(By.tagName("a")).click();
        waitForPpr();
    }

    public String getFocusedRowKey() {
        return (String) executeScript(JS_GET_FOCUSED_ROWKEY, getClientId());
    }

    public List<String> getSelectedRowKeys() {
        return (List<String>) executeScript(JS_GET_SELECTED_ROWKEYS, getClientId());
    }

    public List<String> getDisclosedRowKeys() {
        return (List<String>) executeScript(JS_GET_DISCLOSED_ROWKEYS, getClientId());
    }

    public int getDepth(String rowKey) {
        return ((Number) executeScript(JS_GET_DEPTH_ROWKEY, getClientId(), rowKey)).intValue();
    }

    public boolean isLeaf(String rowKey) {
        return (Boolean) executeScript(JS_ISLEAF_ROWKEY, getClientId(), rowKey);
    }

    public RowInfo getRowInfo(String rowKey) {
        /*
         * tp.FindRowByKey("14")
         * rowInfo = {tr:tr, index:block.startRow + r, block:block};
         */
        List<?> list = (List<?>) executeScript(JS_GET_ROWINFO, getClientId(), rowKey);
        return new RowInfo((WebElement) list.get(0), ((Number) list.get(1)).intValue(), (WebElement) list.get(2));
    }

    //    public String getRowKeyContainingElement(WebElement element) {
    //        String js = String.format("%s.GetRowKeyAndRow('%s')", scriptBoundPeer(), ??);
    //    }

    public String getRowKeyByIndexPath(int... indexpath) {
        StringBuilder path = new StringBuilder(indexpath.length * 2);
        for (int i : indexpath) {
            if (path.length() > 0) {
                path.append("_");
            }
            path.append(i);
        }
        return (String) executeScript(JS_FIND_ROWKEY_BY_INDEXPATH, getClientId(), path.toString());
    }

    public void discloseNode(int... indexpath) {
        // build full subid, for example [0][1]disclosureId for second node within first top-level node
        StringBuilder subid = new StringBuilder();
        for (int i : indexpath) {
            subid.append("[").append(i).append("]");
        }
        subid.append(SUBID_disclosureID);
        // AdfDhtmlTreePeer internally uses this._findRowKeyByIndexPath(indexPath)
        findSubIdElement(subid.toString()).click();
        waitForPpr();
    }

    /**
     * Click the disclose icon to expand (or collapse) a tree node.
     * @param locator Locator to find the {@code <tr>} element of a tree node.
     * @see com.redheap.selenium.AdfFinder#treeNodeByLabel(String)
     */
    public void discloseNode(final By locator) {
        WebElement treerow = getElement().findElement(locator);
        WebElement disclosureIcon = (WebElement) executeScript(JS_GET_DISCLOSURE_ICON, getClientId(), treerow);
        disclosureIcon.click();
        waitForPpr();
    }

    /**
     * Get the rowkey for a tree node based on its label or locator.
     * @param locator Locator to find the {@code <tr>}element of a tree node.
     * @return rowkey
     * @see com.redheap.selenium.AdfFinder#treeNodeByLabel(String)
     */
    public String getRowKey(final By locator) {
        WebElement treerow = getElement().findElement(locator);
        return (String) executeScript(JS_GET_ROW_KEY, getClientId(), treerow);
    }

    /**
     * Determines if a tree node is disclosed (expanded) or collapsed based on its label or locator.
     * @param locator Locator to find the {@code <tr>}element of a tree node.
     * @return {@code true} if the rowkey of the row with this locator is in the set of disclosed row keys
     * @see com.redheap.selenium.AdfFinder#treeNodeByLabel(String)
     */
    public boolean isDisclosed(final By locator) {
        List<String> disclosed = getDisclosedRowKeys();
        String rowkey = getRowKey(locator);
        return disclosed.contains(rowkey);
    }

}
