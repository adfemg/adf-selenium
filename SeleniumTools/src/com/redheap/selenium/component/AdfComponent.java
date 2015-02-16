package com.redheap.selenium.component;

import com.redheap.selenium.AdfConditions;
import com.redheap.selenium.errors.AutomationDisabledException;
import com.redheap.selenium.errors.SubIdNotFoundException;

import java.util.ArrayList;
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

    protected void requireAutomation() {
        if (!AdfDocument.forDriver(driver).isAutomationEnabled()) {
            throw new AutomationDisabledException();
        }
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

    public List<ComponentReference> getDescendantComponents() {
        String js =
            String.format("(function(){var desc=%s.getDescendantComponents(); var retval=[]; desc.forEach(function(comp){retval.push([comp.getClientId(),comp.getComponentType()]);}); return retval;})()",
                          scriptFindComponent());
        return buildReferences((List<List<String>>) executeScript(js));
    }

    public List<ComponentReference> getChildComponents() {
        String js =
            String.format("(function(){var retval=[];%s.visitChildren(function(comp){retval.push([comp.getClientId(),comp.getComponentType()]);return 1}); return retval;})()",
                          scriptFindComponent());
        return buildReferences((List<List<String>>) executeScript(js));
    }

    public List<String> getPropertyKeys() {
        String js = String.format("Object.getOwnPropertyNames(%s.getPropertyKeys())", scriptFindComponent());
        return (List<String>) executeScript(js);
    }

    public Object getProperty(String propName) {
        String js = String.format("%s.getProperty('%s')", scriptFindComponent(), propName);
        return executeScript(js);
    }

    public void scrollIntoView(boolean focus, String subTargetId) {
        executeScript(String.format("%s.scrollIntoView(%b,'%s')", scriptFindComponent(), focus, subTargetId));
    }

    public void scrollIntoView(boolean focus) {
        executeScript(String.format("%s.scrollIntoView(%b)", scriptFindComponent(), focus));
    }

    public void scrollIntoView(String subTargetId) {
        executeScript(String.format("%s.scrollIntoView(%b,'%s')", scriptFindComponent(), false, subTargetId));
    }

    public void scrollIntoView() {
        executeScript(String.format("%s.scrollIntoView()", scriptFindComponent()));
    }

    protected List<ComponentReference> buildReferences(List<List<String>> jsResult) {
        List<ComponentReference> retval = new ArrayList<ComponentReference>(jsResult.size());
        for (List<String> comp : jsResult) {
            retval.add(new ComponentReference(comp.get(0), comp.get(1)));
        }
        return retval;
    }

    protected Object executeScript(String javascript) {
        JavascriptExecutor jsrunner = (JavascriptExecutor) getDriver();
        logger.finer("Executing script " + javascript);
        // selenium handles that this is wrapped in anonymous closure to prevent variable leakage
        Object result = jsrunner.executeScript("return " + javascript);
        logger.finer("Executed script returned: " + result +
                     (result == null ? "" : String.format(" (%s)", result.getClass())));
        return result;
    }

    protected WebElement findSubIdElement(String subid) {
        // component.getPeer().getSubIdDomElement(component, subid)
        requireAutomation();
        final Object result =
            executeScript(String.format("%s.getSubIdDomElement(%s,'%s')", scriptUnboundPeer(), scriptFindComponent(),
                                        subid));
        if (result instanceof WebElement) {
            return (WebElement) result;
        } else {
            throw new SubIdNotFoundException("could not find subid " + subid + " for " + getElement());
        }
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
