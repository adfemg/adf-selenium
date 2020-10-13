package com.redheap.selenium.page;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfDynamicDeclarativeComponent;
import com.redheap.selenium.component.AdfRegion;
import com.redheap.selenium.component.uix.UixInclude;
import com.redheap.selenium.domain.PageMessageWrapper;

import oracle.adf.view.rich.automation.selenium.DialogManager;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import org.openqa.selenium.WebDriverException;

public class DeclarativeComponentFragment /*extends BaseObject*/ {

    private final UixInclude dclr;

    public DeclarativeComponentFragment(UixInclude dclr) {
        this.dclr = dclr;
    }

    protected <P extends DeclarativeComponentFragment> P getDclrContent(Class<? extends P> cls) {
        try {
            return ConstructorUtils.invokeConstructor(cls, dclr);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }


    protected UixInclude findDeclarativeComponent() {
        return dclr;
    }

    protected <T extends AdfComponent> T findAdfComponent(String relativeClientId) {
        return dclr.findAdfComponent(relativeClientId);
    }
    
    protected <T extends AdfComponent> T findAdfComponentByLocator(String relativeLocator) {
        return dclr.findAdfComponentByLocator(relativeLocator);
    }

}

