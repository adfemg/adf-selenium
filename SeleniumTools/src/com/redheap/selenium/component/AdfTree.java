package com.redheap.selenium.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    public AdfTree clickNode(By locator) {
        findElement(locator).findElement(By.tagName("a")).click();
        waitForPpr();
        return this;
    }

    public void discloseNode(int... keypath) {
        // build full subid, for example [0][1]disclosureId for second node within first top-level node
        StringBuilder subid = new StringBuilder();
        for (int i : keypath) {
            subid.append("[").append(i).append("]");
        }
        subid.append(SUBID_disclosureID);
        findSubIdElement(subid.toString()).click();
    }

}
