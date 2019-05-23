package com.redheap.selenium.junit;

import java.util.Locale;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxDriverResource extends WebDriverResource {


    public FirefoxDriverResource(int width, int height, Locale locale) {
        super(width, height, locale);
    }
    
    public FirefoxDriverResource() {
        super();
    }

    @Override
    protected RemoteWebDriver createDriver(String language) {
        FirefoxOptions options = new FirefoxOptions() //changed to recommended constructor for selenium 3.3.1
            .setProfile(createProfile(language))
            .setLegacy(true);
        
        RemoteWebDriver driver = new FirefoxDriver(options); //changed to recommended constructor for selenium 3.3.1 
        if (!Long.valueOf(10).equals(driver.executeScript("return arguments[0]", 10))) {
            throw new WebDriverException("This browser version is not supported due to Selenium bug 8387. See https://code.google.com/p/selenium/issues/detail?id=8387");
        }
        return driver;
    }

    protected FirefoxProfile createProfile(String language) {
        FirefoxProfile profile = new FirefoxProfile();
        // native events cause "Component returned failure code: 0x80004005 (NS_ERROR_FAILURE) [nsINativeMouse.click]"
        // on Windows with multiple calls to AdfSelectOneChoice.clickItemByIndex (and others)
        profile.setEnableNativeEvents(false);
        profile.setPreference("app.update.enabled", false); // don't bother updating Firefox (takes too much time)
        profile.setPreference("browser.usedOnWindows10", true); // don't show first-time windows 10 welcome page
        profile.setPreference("intl.accept_languages", language);
        profile.setPreference("browser.download.folderList",2);
        profile.setPreference("browser.download.manager.showWhenStarting",false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf");
        return profile;
    }

}
