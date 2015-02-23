package com.redheap.selenium.component;

import com.redheap.selenium.AdfConditions;
import com.redheap.selenium.errors.AutomationDisabledException;
import com.redheap.selenium.errors.SubIdNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import oracle.adf.view.rich.automation.selenium.RichWebDrivers;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
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
        RemoteWebDriver rwd = (RemoteWebDriver) rwe.getWrappedDriver();
        String clientid =
            (String) rwd.executeScript("return AdfRichUIPeer.getFirstAncestorComponent(arguments[0]).getClientId()",
                                       element);
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

    protected String scriptDomElement() {
        return String.format("(function(){return %s.getDomElement();})()", scriptBoundPeer());
    }

    protected String scriptObjectToArray(String objectScript, String... attrs) {
        return String.format("(function(){var obj=%s; var retval=[]; %s.forEach(function(key){retval.push(obj[key])}); return retval;})()",
                             objectScript, jsStringArray(attrs));
    }

    protected Object invokePeerMethod(String methodName) {
        String js = String.format("%s.%s()", scriptBoundPeer(), methodName);
        return executeScript(js);
    }

    private String jsStringArray(String... strings) {
        StringBuilder retval = new StringBuilder(5 * strings.length);
        for (String s : strings) {
            if (retval.length() > 0) {
                retval.append(",");
            }
            retval.append(jsStringLiteral(s));
        }
        retval.insert(0, "[").append("]");
        return retval.toString();
    }

    private String jsStringLiteral(String s) {
        if (!s.contains("'")) {
            return "'" + s + "'";
        }
        throw new UnsupportedOperationException("string literals with ' not yet supported");
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

    public String getClientId() {
        return clientid;
    }

    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

    public void click() {
        WebElement element = getElement();
        logger.fine("clicking " + element);
        element.click();
        waitForPpr();
    }

    public void clickWithDialogDetect() {
        WebElement element = getElement();
        logger.fine("clicking " + element);
        element.click();
        waitForPprWithDialogDetect();
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

    protected WebElement findContentNode() {
        final Object result =
            executeScript(String.format("AdfDhtmlEditableValuePeer.GetContentNode(%s, %s)", scriptFindComponent(),
                                        scriptDomElement()));
        if (result instanceof WebElement) {
            return (WebElement) result;
        } else {
            throw new SubIdNotFoundException("could not find content node for " + getElement());
        }
    }

    protected WebElement findSubIdElement(String subid) {
        // component.getPeer().getSubIdDomElement(component, subid)
        requireAutomation();
        final Object result =
            executeScript(String.format("%s.getSubIdDomElement(%s,'%s')", scriptBoundPeer(), scriptFindComponent(),
                                        subid));
        return (WebElement) result;
    }

    protected <T extends AdfComponent> T findSubIdComponent(String subid, Class<? extends T> cls) {
        // component.getPeer().getSubIdDomElement(component, subid)
        requireAutomation();
        String js =
            String.format("(function(){var subelem=%s.getSubIdDomElement(%s,'%s'); if(!subelem){return null;}return AdfRichUIPeer.getFirstAncestorComponent(subelem).getClientId();})()",
                          scriptBoundPeer(), scriptFindComponent(), subid);
        final String subClientId = (String) executeScript(js);
        if (subClientId == null) {
            return null;
        }
        if (getClientId().equals(subClientId)) {
            throw new SubIdNotFoundException("subid " + subid + " for " + getElement() +
                                             " does not point to a component");
        }
        return AdfComponent.forClientId(driver, subClientId, cls);
    }

    protected void waitForPpr() {
        waiter.until(AdfConditions.clientSynchedWithServer());
    }

    protected void waitForPprWithDialogDetect() {
        // TODO timout shouldn't be hardcoded
        RichWebDrivers.waitForServer(getDriver(), AdfConditions.DFLT_WAIT_TIMEOUT_SECS * 10000, true);
    }

    protected boolean isPlatform(Platform p) {
        return ((RemoteWebDriver) driver).getCapabilities().getPlatform().is(p);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientid", clientid).build();
    }

}
