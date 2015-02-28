package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ComponentFactory {

    /**
     * Create instance of AdfComponent for given componentType or return {@code null} if this componentType is not
     * supported by this factory.
     * @param <T>
     * @param driver
     * @param clientid
     * @param element
     * @return
     */
    public <T extends AdfComponent> T createComponent(WebDriver driver, String componentType, String clientid,
                                                      WebElement element);

}
