package com.redheap.selenium.component;

import com.redheap.selenium.AdfConditions;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AdfComponent /*extends BaseObject*/ {

    private final WebDriver driver;
    private final String clientid;
    private final WebElement element;
    private WebDriverWait waiter;

    private static final Logger logger = Logger.getLogger(AdfComponent.class.getName());

    protected AdfComponent(WebDriver driver, String clientid) {
        this.driver = driver;
        this.clientid = clientid;
        this.element = driver.findElement(By.id(clientid));
        this.waiter = AdfConditions.defaultWaiter(driver);
        Assert.assertEquals(getExpectedComponentType(),
                            executeScript(String.format("AdfPage.PAGE.findComponentByAbsoluteId('%s').getComponentType()",
                                                        clientid)));
    }

    protected abstract String getExpectedComponentType();

    //    public static <T extends AdfComponent> T forElement(WebElement element, Class<? extends T> cls) {
    //        // instantiate and return
    //        try {
    //            return cls.getConstructor(WebElement.class).newInstance(element);
    //        } catch (Exception e) {
    //            throw new WebDriverException("unable to instantiate adf component: " + element,
    //                                         e.getCause() != null ? e.getCause() : e);
    //        }
    //    }

    public static <T extends AdfComponent> T forClientId(WebDriver driver, String clientid, Class<? extends T> cls) {
        // instantiate and return
        try {
            return cls.getConstructor(WebDriver.class, String.class).newInstance(driver, clientid);
        } catch (Exception e) {
            throw new WebDriverException("unable to instantiate adf component: " + clientid,
                                         e.getCause() != null ? e.getCause() : e);
        }
    }

    public static <T extends AdfComponent> T forElement(WebElement element, Class<? extends T> cls) {
        RemoteWebElement rwe = (RemoteWebElement) element;
        String js =
            String.format("return AdfRichUIPeer.getFirstAncestorComponent(AdfAgent.AGENT.getElementById('%s')).getClientId()");
        RemoteWebDriver rwd = (RemoteWebDriver) rwe.getWrappedDriver();
        String clientid = (String) rwd.executeScript(js);
        return forClientId(rwd, clientid, cls);
    }

    public void setWaiter(WebDriverWait waiter) {
        this.waiter = waiter;
    }

    protected String scriptFindComponent() {
        return String.format("AdfPage.PAGE.findComponentByAbsoluteId('%s')", clientid);
    }

    protected String scriptUnboundPeer() {
        return String.format("AdfPage.PAGE.findComponentByAbsoluteId('%s').getPeer()", clientid);
    }

    protected String scriptBoundPeer() {
        return String.format("(function(){var c=AdfPage.PAGE.findComponentByAbsoluteId('%s');var p=c.getPeer();p.bind(c);return p;})()",
                             clientid);
    }

    protected Object invokePeerMethod(String methodName) {
        String js = String.format("%s.%s()", scriptBoundPeer(), methodName);
        return executeScript(js);
    }

    /**
     * Makes wrapped RemoteWebElement availabl for subclasses.
     * @return RemoteWebElement supplied to the constructor.
     */
    public WebElement getElement() {
        return element;
    }

    public WebDriver getDriver() {
        return driver;
    }

    //@Override
    protected List<WebElement> findElements(By by) {
        return getElement().findElements(by);
    }

    //@Override
    protected WebElement findElement(By by) {
        return getElement().findElement(by);
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, Class<? extends T> cls) {
        String js = String.format("%s.findComponent('%s').getClientId()", scriptFindComponent(), relativeClientId);
        String clientid = (String) executeScript(js);
        return AdfComponent.forClientId(driver, clientid, cls);
    }

    public String getId() {
        // simple (local) id
        return getElement().getAttribute("id");
    }

    public void click() {
        getElement().click();
        waitForPpr();
    }

    protected Object executeScript(String javascript) {
        logger.finer("Executing script " + javascript);
        Object result = ((JavascriptExecutor) getDriver()).executeScript("return " + javascript);
        logger.finer("Executed script returned: " + result +
                     (result == null ? "" : String.format(" (%s)", result.getClass())));
        return result;
    }

    protected WebElement findSubIdElement(String subid) {
        // component.getPeer().getSubIdDomElement(component, subid)
        return (WebElement) executeScript(String.format("%s.getSubIdDomElement(%s,'%s')", scriptUnboundPeer(),
                                                        scriptFindComponent(), subid));
    }

    protected void waitForPpr() {
        waiter.until(AdfConditions.clientSynchedWithServer());
    }

    //    protected Collection<String> getDescendantComponents() {
    //        String js =
    //            "{ids=[]; component.getDescendantComponents().forEach(function(r){ids.push(r.getClientId())}); return ids}";
    //        Collection<String> ids = (Collection<String>) executeScript(js);
    //        Collection<AdfComponent> retval = new ArrayList<AdfComponent>(ids.size());
    //        for (String id : ids) {
    //            retval.add(findAdfComponent(By.id(id), ?.class));
    //        }
    //        return retval;
    //    }

}
