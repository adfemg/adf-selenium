package com.redheap.selenium.junit;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PhantomJSDriverResource extends WebDriverResource {

    public PhantomJSDriverResource() {
        super();
    }

    @Override
    protected RemoteWebDriver createDriver() {
        PhantomJSDriver retval = new PhantomJSDriver();
        return retval;
    }
}
