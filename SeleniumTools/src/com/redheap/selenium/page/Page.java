package com.redheap.selenium.page;

import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfDocument;
import com.redheap.selenium.domain.PageMessageWrapper;

import java.util.logging.Logger;

import oracle.adf.view.rich.automation.selenium.DialogManager;
import oracle.adf.view.rich.automation.selenium.RichWebDrivers;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Full page running in a unbounded taskflow.
 * Gets the WebDriver (full browser) as container it is running it, but should not expose this to users of this
 * class. PageObjects should only expose "business" functions to their users like <br>
 * <code>public login(String username, String password)</code>
 * <p>
 * The implementation of these business functions can interact with AdfComponents on the page and should not
 * interact directly with the WebDriver or WebElements.
 */
public abstract class Page /*implements TakesScreenshot*/ {

    private final RemoteWebDriver driver;

    private static final String JS_GET_REGIONS =
        "{ids=[]; AdfPage.PAGE.getComponentsByType('oracle.adf.RichRegion').forEach(function(r){ids.push(r.getClientId())}); return ids}";
    private static final String JS_GET_DOCID = "return AdfPage.PAGE.getDocumentClientId()"; // Here is used Internal function to retrieve the document component's client ID
                                                                                            // ��. oracle.adfinternal.view.js.laf.dhtml.rich.AdfDhtmlPage.getDocumentClientId()
                                                                                            // https://docs.oracle.com/middleware/1221/adf/api-reference-javascript-faces/oracle/adfinternal/view/js/laf/dhtml/rich/AdfDhtmlPage.html
                                                                                            //
    
    private static final Logger logger = Logger.getLogger(Page.class.getName());

    public Page(WebDriver driver) {
        this(driver, true);
    }

    Page(WebDriver driver, boolean verifyTitle) {
        super();
        this.driver = (RemoteWebDriver) driver; // keep handle which we don't expose but only use for navigatedTo
        // this is specific for unbounded taskflow page. Bounded page fragments should be available as the caller
        // already has to wait for its action to complete
        RichWebDrivers.waitForRichPageToLoad(driver, AdfComponent.DFLT_WAIT_TIMEOUT_MSECS);
        if (verifyTitle) {
            assertEquals(getExpectedTitle(), getTitle());
        }
        // with animation enabled we sometimes try to click on elements still being rendered (like slowly expanding
        // af:tree node)
        findDocument().setAnimationEnabled(false);
        if (!findDocument().isAutomationEnabled()) {
            logger.warning("AUTOMATION IS DISABLED! set oracle.adf.view.rich.automation.ENABLED to true for all functions to work!");
        }
    }

    protected AdfDocument findDocument() {
        JavascriptExecutor jsdriver = (JavascriptExecutor) driver;
        String docid = (String) jsdriver.executeScript(JS_GET_DOCID);
        return AdfComponent.forClientId(driver, docid);
    }

    protected abstract String getExpectedTitle();

    public String getTitle() {
        return driver.getTitle();
    }

    protected <P extends Page> P navigatedTo(Class<? extends P> cls) {
        try {
            return ConstructorUtils.invokeConstructor(cls, driver);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected <D extends WindowDialog> D openedDialog(Class<? extends D> cls) {
        try {
            return ConstructorUtils.invokeConstructor(cls, driver, DialogManager.getInstance().getCurrentDialog());
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

    protected <T extends AdfComponent> T findAdfComponent(String relativeClientId) {
        return findDocument().findAdfComponent(relativeClientId);
    }

    protected <T extends AdfComponent> T findAdfComponentByLocator(String relativeClientId) {
        return findDocument().findAdfComponentByLocator(relativeClientId);
    }

    public <X extends Object> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return driver.getScreenshotAs(target);
    }

    /**
     * Convenience method to get all the messages on the page.
     * @return A {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper} object containing
     * all messages and some convenicence methods
     */
    public PageMessageWrapper getAllMessages() {
        return PageMessageWrapper.getAllMessages(driver);
    }

}
