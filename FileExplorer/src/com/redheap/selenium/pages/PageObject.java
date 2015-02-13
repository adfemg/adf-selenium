package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfDocument;
import com.redheap.selenium.conditions.AdfConditions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Full page running in a unbounded taskflow.
 * Gets the WebDriver (full browser) as container it is running it, but should not expose this to users of this
 * class. PageObjects should only expose "business" functions to their users like <br>
 * <code>public login(String username, String password)</code>
 * <p>
 * The implementation of these business functions can interact with AdfComponents on the page and should not
 * interact directly with the WebDriver or WebElements.
 */
public abstract class PageObject /*implements TakesScreenshot*/ {

    public static final long DFLT_WAIT_TIMEOUT_SECS = 30;
    public static final long DFLT_WAIT_INTERVAL_MSECS = 100;

    private RemoteWebDriver driver;

    private static final String JS_GET_REGIONS =
        "{ids=[]; AdfPage.PAGE.getComponentsByType('oracle.adf.RichRegion').forEach(function(r){ids.push(r.getClientId())}); return ids}";

    public PageObject(WebDriver driver) {
        super();
        this.driver = (RemoteWebDriver) driver; // keep handle which we don't expose but only use for navigatedTo
        // this is specific for unbounded taskflow page. Bounded page fragments should be available as the caller
        // already has to wait for its action to complete
        waitForPpr();
        assertEquals(getExpectedTitle(), driver.getTitle());
        // with animation enabled we sometimes try to click on elements still being rendered (like slowly expanding
        // af:tree node)
        executeScript("AdfPage.PAGE.setAnimationEnabled(false)");
    }

    protected AdfDocument findDocument() {
        JavascriptExecutor jsdriver = (JavascriptExecutor) driver;
        String docid = (String) jsdriver.executeScript("return AdfPage.PAGE.getDocumentClientId()");
        return AdfComponent.forElement(driver, docid, AdfDocument.class);
    }

    protected abstract String getExpectedTitle();

    protected <P extends PageObject> P navigatedTo(Class<? extends P> cls) {
        try {
            return cls.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected void waitForPpr() {
        waitForPpr(DFLT_WAIT_TIMEOUT_SECS, DFLT_WAIT_INTERVAL_MSECS);
    }

    protected void waitForPpr(long timeOutInSeconds) {
        waitForPpr(timeOutInSeconds, DFLT_WAIT_INTERVAL_MSECS);
    }

    protected void waitForPpr(long timeOutInSeconds, long sleepInMillis) {
        new WebDriverWait(driver, timeOutInSeconds, sleepInMillis).until(AdfConditions.clientSynchedWithServer());
    }

    protected <T extends AdfComponent> T findAdfComponent(String relativeClientId, Class<? extends T> cls) {
        return findDocument().findAdfComponent(relativeClientId, cls);
    }

    //@Override
    public <X extends Object> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return driver.getScreenshotAs(target);
    }

    protected Object executeScript(String javascript) {
        System.out.println("Executing script " + javascript);
        Object result = driver.executeScript("return " + javascript);
        System.out.println("Executed script returned: " + result +
                           (result == null ? "" : String.format(" (%s)", result.getClass())));
        return result;
    }


//    protected Collection<AdfRegion> getRegions() {
//        Collection<String> ids = (Collection<String>) executeScript(JS_GET_REGIONS);
//        Collection<AdfRegion> retval = new ArrayList<AdfRegion>(ids.size());
//        for (String id : ids) {
//            retval.add(findAdfComponent(By.id(id), AdfRegion.class));
//        }
//        return retval;
//    }

}
