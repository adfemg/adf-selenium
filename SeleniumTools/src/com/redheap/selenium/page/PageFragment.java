package com.redheap.selenium.page;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfRegion;
import com.redheap.selenium.AdfConditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageFragment /*extends BaseObject*/ {

    private AdfRegion region;

    public PageFragment(AdfRegion region) {
        this.region = region;
        // TODO: verify correct fragment is running
    }

    //private AdfRegion getRegion() {
    //    return region;
    //}

    //private WebDriver getDriver() {
    //    return getRegion().getElement().getWrappedDriver();
    //}

    protected <P extends PageFragment> P navigatedTo(Class<? extends P> cls) {
        try {
            return cls.getConstructor(AdfRegion.class).newInstance(region);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected void waitForPpr() {
        waitForPpr(PageObject.DFLT_WAIT_TIMEOUT_SECS, PageObject.DFLT_WAIT_INTERVAL_MSECS);
    }

    protected void waitForPpr(long timeOutInSeconds) {
        waitForPpr(timeOutInSeconds, PageObject.DFLT_WAIT_INTERVAL_MSECS);
    }

    protected void waitForPpr(long timeOutInSeconds, long sleepInMillis) {
        new WebDriverWait(getDriver(), timeOutInSeconds, sleepInMillis).until(AdfConditions.clientSynchedWithServer());
    }

    protected AdfRegion findRegion() {
        return region;
    }

    private WebDriver getDriver() {
        return region.getDriver();
    }

    protected <T extends AdfComponent> T findAdfComponent(String relativeClientId, Class<? extends T> cls) {
        return region.findAdfComponent(relativeClientId, cls);
    }

}

