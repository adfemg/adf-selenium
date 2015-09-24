package com.redheap.selenium.junit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxDriverResource extends WebDriverResource {

    public FirefoxDriverResource() {
        super();
    }

    @Override
    protected RemoteWebDriver createDriver() {
        RemoteWebDriver driver = new FirefoxDriver(createProfile());
        if (!Long.valueOf(10).equals(driver.executeScript("return arguments[0]", 10))) {
            throw new WebDriverException("This browser version is not supported due to Selenium bug 8387. See https://code.google.com/p/selenium/issues/detail?id=8387");
        }
        return driver;
    }

    protected FirefoxProfile createProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        // native events cause "Component returned failure code: 0x80004005 (NS_ERROR_FAILURE) [nsINativeMouse.click]"
        // on Windows with multiple calls to AdfSelectOneChoice.clickItemByIndex (and others)
        profile.setEnableNativeEvents(false);
        profile.setPreference("app.update.enabled", false); // don't bother updating Firefox (takes too much time)
        return profile;
    }

}
