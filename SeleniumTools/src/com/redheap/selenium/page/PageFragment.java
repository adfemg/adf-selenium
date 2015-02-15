package com.redheap.selenium.page;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfRegion;

import org.openqa.selenium.WebDriverException;

public class PageFragment /*extends BaseObject*/ {

    private final AdfRegion region;

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

    protected AdfRegion findRegion() {
        return region;
    }

    protected <T extends AdfComponent> T findAdfComponent(String relativeClientId, Class<? extends T> cls) {
        return region.findAdfComponent(relativeClientId, cls);
    }

}

