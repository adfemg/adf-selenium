package com.redheap.selenium.junit;

import java.util.Locale;
import java.util.logging.Logger;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxDriverResource extends WebDriverResource {

    private static final Logger logger = Logger.getLogger(FirefoxDriverResource.class.getName());

    /**
     * From Selenium 3 on, Firefox is launched using Geckodriver (see: <a href="https://github.com/mozilla/geckodriver/releases">
     * https://github.com/mozilla/geckodriver/releases</a>) unless it is specified by means of system property
     * {@code webdriver.firefox.marionette} that the new geckodriver (marionette) is not to be used, but the legacy firefox driver
     * instead.
     * <p>
     * If the new driver is to be used, we need a path to it's executable which should be set in system property
     * {@code webdriver.gecko.driver} before creation of the driver.
     *
     * @param width
     * @param height
     * @param locale
     * @param forceLegacyDriver Indicates if the legacy firefox driver should be used instead of the new geckodriver
     */
    public FirefoxDriverResource(final int width, final int height, final Locale locale, final boolean forceLegacyDriver) {
        super(width, height, locale);
        if (forceLegacyDriver) {
            logger.fine("Force use of legacy firefox driver: webdriver.firefox.marionette=false");
            System.setProperty("webdriver.firefox.marionette", Boolean.FALSE.toString());
        }
    }

    /**
     * See {@link #FirefoxDriverResource(int, int, Locale, boolean)}, only without forcing the use of the legacy driver.
     *
     * @param width
     * @param height
     * @param locale
     */
    public FirefoxDriverResource(final int width, final int height, final Locale locale) {
        this(width, height, locale, false);
    }

    /**
     * Get default instance, but give a path to Geckodriver executable.
     */
    public FirefoxDriverResource() {
        super();
    }

    /**
     * {@inheritDoc}.
     * @param language
     * @return
     */
    @Override
    protected RemoteWebDriver createDriver(final String language) {
        final RemoteWebDriver driver = new FirefoxDriver(createProfile(language));
        return driver;
    }

    /**
     * Create a generic profile ({@link FirefoxProfile}) for use with a {@link FirefoxDriver} instance.
     * @param language
     * @return
     */
    protected FirefoxProfile createProfile(final String language) {
        final FirefoxProfile profile = new FirefoxProfile();
        // native events cause "Component returned failure code: 0x80004005 (NS_ERROR_FAILURE) [nsINativeMouse.click]"
        // on Windows with multiple calls to AdfSelectOneChoice.clickItemByIndex (and others)
        profile.setEnableNativeEvents(false);
        profile.setPreference("app.update.enabled", false); // don't bother updating Firefox (takes too much time)
        profile.setPreference("browser.usedOnWindows10", true); // don't show first-time windows 10 welcome page
        profile.setPreference("intl.accept_languages", language);
        return profile;
    }
}
