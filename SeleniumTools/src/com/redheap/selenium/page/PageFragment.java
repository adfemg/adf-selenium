package com.redheap.selenium.page;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfRegion;

import oracle.adf.view.rich.automation.selenium.Dialog;
import oracle.adf.view.rich.automation.selenium.DialogManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class PageFragment /*extends BaseObject*/ {

    private final AdfRegion region;

    public PageFragment(AdfRegion region) {
        this.region = region;
        // TODO: verify correct fragment is running
    }

    protected <P extends PageFragment> P navigatedTo(Class<? extends P> cls) {
        try {
            return cls.getConstructor(AdfRegion.class).newInstance(region);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected <D extends WindowDialog> D openedDialog(Class<? extends D> cls) {
        try {
            return cls.getConstructor(WebDriver.class, Dialog.class).newInstance(findRegion().getDriver(),
                                                                                 DialogManager.getInstance().getCurrentDialog());
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected AdfRegion findRegion() {
        return region;
    }

    protected <T extends AdfComponent> T findAdfComponent(String relativeClientId) {
        return region.findAdfComponent(relativeClientId);
    }

}

