package com.redheap.selenium.page;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfRegion;
import com.redheap.selenium.domain.PageMessageWrapper;

import oracle.adf.view.rich.automation.selenium.DialogManager;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import org.openqa.selenium.WebDriverException;

public class PageFragment /*extends BaseObject*/ {

    private final AdfRegion region;

    public PageFragment(AdfRegion region) {
        this.region = region;
        // TODO: verify correct fragment is running
    }

    protected <P extends PageFragment> P navigatedTo(Class<? extends P> cls) {
        try {
            return ConstructorUtils.invokeConstructor(cls, region);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected <D extends WindowDialog> D openedDialog(Class<? extends D> cls) {
        try {
            return ConstructorUtils.invokeConstructor(cls, findRegion().getDriver(),
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

    /**
     * Convenience method to get all the messages on the page.
     * @return A {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper} object containing
     * all messages and some convenicence methods
     */
    protected PageMessageWrapper getMessages() {
        return findRegion().getAllMessages();
    }

}

