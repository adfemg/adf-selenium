package com.redheap.selenium.component;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfTree extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_disclosureID = "disclosureID";

    public AdfTree(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichTree";
    }

    public int getExpandedNodeCount() {
        String js = String.format("Object.keys(%s.getDisclosedRowKeys()).length", scriptFindComponent());
        return ((Number) executeScript(js)).intValue();
    }

    public void clickNode(By locator) {
        findElement(locator).findElement(By.tagName("a")).click();
        waitForPpr();
    }

    public String getFocusedRowKey() {
        String js = String.format("%s.GetFocusedRowKey()", scriptBoundPeer());
        return (String) executeScript(js);
    }

    public List<String> getSelectedRowKeys() {
        String js = String.format("Object.keys(%s.getSelectedRowKeys())", scriptFindComponent());
        return (List<String>) executeScript(js);
    }

    public List<String> getDisclosedRowKeys() {
        String js = String.format("Object.keys(%s.getDisclosedRowKeys())", scriptFindComponent());
        return (List<String>) executeScript(js);
    }

    public int getDepth(String rowKey) {
        String js = String.format("%s.getDepth('%s')", scriptBoundPeer(), rowKey);
        return ((Number) executeScript(js)).intValue();
    }

    public boolean isLeaf(String rowKey) {
        String js = String.format("%s.isLeaf('%s')", scriptBoundPeer(), rowKey);
        return (Boolean) executeScript(js);
    }

    public RowInfo getRowInfo(String rowKey) {
        /*
         * tp.FindRowByKey("14")
         * rowInfo = {tr:tr, index:block.startRow + r, block:block};
         */
        String rowinfojs = String.format("%s.FindRowByKey('%s')", scriptBoundPeer(), rowKey);
        String js = scriptObjectToArray(rowinfojs, "tr", "index", "block");
        List<?> list = (List<?>) executeScript(js);
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
        String js = String.format("%s._findRowKeyByIndexPath('%s')", scriptBoundPeer(), path.toString());
        return (String) executeScript(js);
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

}
