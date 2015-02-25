package com.redheap.selenium.component;

import com.redheap.selenium.errors.SubIdNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import oracle.adf.view.rich.automation.selenium.RichWebDrivers;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public abstract class AdfComponent /*extends BaseObject*/ {

    private final WebDriver driver;
    private final String clientid;
    private final WebElement element;
    private long timeoutMillisecs = DFLT_WAIT_TIMEOUT_MSECS;

    public static final long DFLT_WAIT_TIMEOUT_MSECS = 30000;

    private static final Logger logger = Logger.getLogger(AdfComponent.class.getName());

    protected static final String JS_FIND_COMPONENT = "var comp=AdfPage.PAGE.findComponentByAbsoluteId(arguments[0]);";
    protected static final String JS_FIND_PEER = JS_FIND_COMPONENT + "var peer=comp.getPeer(); peer.bind(comp);";
    protected static final String JS_FIND_ELEMENT = JS_FIND_PEER + "var elem=peer.getDomElement();";

    private static final String JS_GET_COMPONENT_TYPE = JS_FIND_COMPONENT + "return comp.getComponentType()";
    private static final String JS_FIND_ANCESTOR_COMPONENT =
        "return AdfRichUIPeer.getFirstAncestorComponent(arguments[0]).getClientId();";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID =
        JS_FIND_COMPONENT + "return comp.findComponent(arguments[1]).getClientId()";
    private static final String JS_GET_DESCENDANT_COMPONENTS =
        JS_FIND_COMPONENT + "var desc=comp.getDescendantComponents();" + "var retval=[];" +
        "desc.forEach(function(comp){retval.push([comp.getClientId(),comp.getComponentType()]);});" + "return retval;";
    private static final String JS_FIND_CHILD_COMPONENTS =
        JS_FIND_COMPONENT + "var retval=[];" +
        "comp.visitChildren(function(comp){retval.push([comp.getClientId(),comp.getComponentType()]);return 1});" +
        "return retval;";
    private static final String JS_GET_PROPERTY_KEYS =
        "return Object.getOwnPropertyNames(" + JS_FIND_COMPONENT + "return comp.getPropertyKeys());";
    private static final String JS_GET_PROPERTY = JS_FIND_COMPONENT + "return comp.getProperty(arguments[1])";
    private static final String JS_SCROLLINTOVIEW_FOCUS_SUBTARGET =
        JS_FIND_COMPONENT + "comp.scrollIntoView(arguments[1], arguments[2])";
    private static final String JS_SCROLLINTOVIEW_FOCUS = JS_FIND_COMPONENT + "comp.scrollIntoView(arguments[1]);";
    private static final String JS_SCROLLINTOVIEW_SUBTARGET = JS_FIND_COMPONENT + "comp.scrollIntoView(arguments[1]);";
    private static final String JS_SCROLLINTOVIEW = JS_FIND_COMPONENT + "comp.scrollIntoView();";
    private static final String JS_FIND_CONTENT_NODE =
        JS_FIND_ELEMENT + "return AdfDhtmlEditableValuePeer.GetContentNode(comp, elem);";
    private static final String JS_FIND_SUBID_ELEMENT =
        JS_FIND_PEER + "return peer.getSubIdDomElement(comp,arguments[1]);";
    private static final String JS_FIND_SUBID_CLIENTID =
        JS_FIND_PEER +
        "var subelem=peer.getSubIdDomElement(comp,arguments[1]); if(!subelem){return null;}return AdfRichUIPeer.getFirstAncestorComponent(subelem).getClientId();";

    protected AdfComponent(WebDriver driver, String clientid) {
        this.driver = driver;
        this.clientid = clientid;
        this.element = driver.findElement(By.id(clientid));
        assertEquals(getExpectedComponentType(), executeScript(JS_GET_COMPONENT_TYPE, clientid));
    }

    protected abstract String getExpectedComponentType();

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
        String clientid = (String) rwd.executeScript(JS_FIND_ANCESTOR_COMPONENT, element);
        return forClientId(rwd, clientid, cls);
    }

    public void setTimout(long milliseconds) {
        this.timeoutMillisecs = milliseconds;
    }

    protected Object executeScript(CharSequence script, Object... args) {
        JavascriptExecutor jsrunner = (JavascriptExecutor) driver;
        logger.finer("executing script '" + script + "' with params " + Arrays.asList(args));
        Object result = jsrunner.executeScript(script.toString(), args);
        logger.finer("executed script returned: " + result +
                     (result == null ? "" : String.format(" (%s)", result.getClass())));
        return result;
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

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, Class<? extends T> cls) {
        String clientid = (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID, getClientId(), relativeClientId);
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

    public void contextClick() {
        new Actions(driver).contextClick(getElement()).perform();
        waitForPpr();
    }

    public void doubleClick() {
        new Actions(driver).doubleClick(getElement()).perform();
        waitForPpr();
    }

    public void moveMouseTo() {
        new Actions(driver).moveToElement(getElement()).perform();
        waitForPpr();
    }

    public void dragAndDropTo(AdfComponent target) {
        dragAndDropTo(target.getElement());
        waitForPpr();
    }

    public void dragAndDropTo(WebElement target) {
        new Actions(driver).clickAndHold(this.getElement()).moveToElement(target).release(target).perform();
        waitForPpr();
    }

    public List<ComponentReference> getDescendantComponents() {
        return buildReferences((List<List<String>>) executeScript(JS_GET_DESCENDANT_COMPONENTS, getClientId()));
    }

    public List<ComponentReference> getChildComponents() {
        return buildReferences((List<List<String>>) executeScript(JS_FIND_CHILD_COMPONENTS, getClientId()));
    }

    public List<String> getPropertyKeys() {
        return (List<String>) executeScript(JS_GET_PROPERTY_KEYS, getClientId());
    }

    public Object getProperty(String propName) {
        return executeScript(JS_GET_PROPERTY, getClientId(), propName);
    }

    public void scrollIntoView(boolean focus, String subTargetId) {
        executeScript(JS_SCROLLINTOVIEW_FOCUS_SUBTARGET, getClientId(), focus, subTargetId);
    }

    public void scrollIntoView(boolean focus) {
        executeScript(JS_SCROLLINTOVIEW_FOCUS, getClientId(), focus);
    }

    public void scrollIntoView(String subTargetId) {
        executeScript(JS_SCROLLINTOVIEW_SUBTARGET, getClientId(), subTargetId);
    }

    public void scrollIntoView() {
        executeScript(JS_SCROLLINTOVIEW, getClientId());
    }

    protected List<ComponentReference> buildReferences(List<List<String>> jsResult) {
        List<ComponentReference> retval = new ArrayList<ComponentReference>(jsResult.size());
        for (List<String> comp : jsResult) {
            retval.add(new ComponentReference(comp.get(0), comp.get(1)));
        }
        return retval;
    }

    protected WebElement findContentNode() {
        final Object result = executeScript(JS_FIND_CONTENT_NODE, getClientId());
        if (result instanceof WebElement) {
            return (WebElement) result;
        } else {
            throw new SubIdNotFoundException("could not find content node for " + getElement());
        }
    }

    protected WebElement findSubIdElement(String subid) {
        return (WebElement) executeScript(JS_FIND_SUBID_ELEMENT, getClientId(), subid);
    }

    protected <T extends AdfComponent> T findSubIdComponent(String subid, Class<? extends T> cls) {
        final String subClientId = (String) executeScript(JS_FIND_SUBID_CLIENTID, getClientId(), subid);
        if (subClientId == null) {
            return null;
        }
        if (getClientId().equals(subClientId)) {
            WebElement elem = findSubIdElement(subid);
            throw new SubIdNotFoundException("subid " + subid + " for " + getElement() +
                                             " does not point to a component but to sub-element " +
                                             (elem == null ? "unknown" : elem.getTagName()));
        }
        return AdfComponent.forClientId(driver, subClientId, cls);
    }

    protected void waitForPpr() {
        RichWebDrivers.waitForServer(driver, timeoutMillisecs);
    }

    protected void waitForPprWithDialogDetect() {
        RichWebDrivers.waitForServer(driver, timeoutMillisecs, true);
    }

    protected boolean isPlatform(Platform p) {
        return ((RemoteWebDriver) driver).getCapabilities().getPlatform().is(p);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientid", clientid).build();
    }

}
