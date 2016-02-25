package com.redheap.selenium.junit;

import java.util.Locale;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PhantomJSDriverResource extends WebDriverResource {

    public PhantomJSDriverResource(int width, int height, Locale locale) {
        super(width, height, locale);
    }

    public PhantomJSDriverResource() {
        super();
    }

    @Override
    protected RemoteWebDriver createDriver(String language) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Accept-Language", language);
        PhantomJSDriver retval = new PhantomJSDriver(caps);
        return retval;
    }
}
