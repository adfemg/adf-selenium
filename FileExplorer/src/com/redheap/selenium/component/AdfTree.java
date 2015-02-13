package com.redheap.selenium.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdfTree extends AdfComponent {

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
        return this;
    }

}
