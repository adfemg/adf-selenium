package com.redheap.selenium.component;

import com.redheap.selenium.errors.SubIdNotFoundException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.adf.view.rich.automation.selenium.RichWebDrivers;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public class AdfComponent /*extends BaseObject*/ {
    private static final Logger logger = Logger.getLogger(AdfComponent.class.getName());

    private final WebDriver driver;
    private final String clientid;
    private final WebElement element;
    private long timeoutMillisecs = DFLT_WAIT_TIMEOUT_MSECS;

    private static boolean timeoutRetrieved = false;
    private static final long STD_WAIT_TIMEOUT_MSECS = 30000;
    public static final long DFLT_WAIT_TIMEOUT_MSECS = getDefaultTimeout();


    private static final ServiceLoader<ComponentFactory> factories = ServiceLoader.load(ComponentFactory.class);
    private static final Map<String, Class<? extends AdfComponent>> defaultComponents = new HashMap<>();

    protected static final String JS_FIND_COMPONENT = "var comp=AdfPage.PAGE.findComponentByAbsoluteId(arguments[0]);";
    protected static final String JS_FIND_PEER = JS_FIND_COMPONENT + "var peer=comp.getPeer(); peer.bind(comp);";
    protected static final String JS_FIND_ELEMENT = JS_FIND_PEER + "var elem=peer.getDomElement();";

    private static final String JS_GET_ABSOLUTE_LOCATOR = JS_FIND_COMPONENT + "return comp.getAbsoluteLocator()";
    private static final String JS_GET_COMPONENT_TYPE = JS_FIND_COMPONENT + "return comp.getComponentType()";
    private static final String JS_FIND_ANCESTOR_COMPONENT =
        "return AdfRichUIPeer.getFirstAncestorComponent(arguments[0]).getClientId();";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID =
        JS_FIND_COMPONENT + "child=comp.findComponent(arguments[1],true); return child?child.getClientId():null;";
    private static final String JS_FIND_COMPONENT_BY_LOCATOR =
        "var comp=AdfPage.PAGE.findComponentByAbsoluteLocator(arguments[0]); return [comp.getComponentType(),comp.getClientId()]";
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
        JS_FIND_ELEMENT + "return AdfDhtmlEditableValuePeer.getContentNode(comp, elem);";
    private static final String JS_FIND_SUBID_ELEMENT =
        JS_FIND_PEER + "return peer.getSubIdDomElement(comp,arguments[1]);";
    private static final String JS_FIND_SUBID_CLIENTID =
        JS_FIND_PEER +
        "var subelem=peer.getSubIdDomElement(comp,arguments[1]); if(!subelem){return null;}return AdfRichUIPeer.getFirstAncestorComponent(subelem).getClientId();";
    private static final String JS_GET_DISABLED = JS_FIND_COMPONENT + "return comp.getDisabled();";
    private static final String JS_IS_NAMINGCONTAINER =
        JS_FIND_COMPONENT + "return AdfUIComponent.__isNamingContainer(comp.constructor);";

    private static final Pattern RELATIVE_ID = Pattern.compile("(:*)(.+)");

    protected AdfComponent(WebDriver driver, String clientid) {
        this.driver = driver;
        this.clientid = clientid;
        final List<WebElement> elems = driver.findElements(By.id(clientid));
        // some components do not have element (like dynamic declarative component)
        // TODO: why not lazy resolving in getElement?
        this.element = (elems != null && elems.size() == 1) ? elems.get(0) : null;
    }

    /**
     * Gets the default timeout property value from the property default.wait.timeout
     *
     * @return timeout long
     * @throws IOException
     */
    public static long getDefaultTimeout() {
        if (timeoutRetrieved) {
            return DFLT_WAIT_TIMEOUT_MSECS;
        } else {
            long timeoutMillisecs = STD_WAIT_TIMEOUT_MSECS;

            try {
                timeoutMillisecs = Long.parseLong(System.getProperty("default.wait.timeout"));
            }catch (Exception ex) {
                logger.warning("Unable to read timeout, returning default");
                return timeoutMillisecs;
            } finally {
                timeoutRetrieved = true;
            }

            return timeoutMillisecs;
        }

    }

    public static <C extends AdfComponent> C forClientId(WebDriver driver, String clientid) {
        // find component type
        String type = (String) ((JavascriptExecutor) driver).executeScript(JS_GET_COMPONENT_TYPE, clientid);
        return forClientId(driver, type, clientid);
    }

    private static <C extends AdfComponent> C forClientId(WebDriver driver, String componentType, String clientid) {
        // instantiate default component or one from ServiceLoader
        Iterator<ComponentFactory> iter = factories.iterator();
        if (iter.hasNext()) {
            final List<WebElement> elems = driver.findElements(By.id(clientid));
            // some components do not have element (like dynamic declarative component)
            WebElement element = (elems != null && elems.size() == 1) ? elems.get(0) : null;
            while (iter.hasNext()) {
                ComponentFactory factory = iter.next();
                final C component = factory.createComponent(driver, componentType, clientid, element);
                if (component != null) {
                    return component;
                }
            }
        }
        // none found from serviceloaders so try to use our defaults
        if (!defaultComponents.containsKey(componentType)) {
            // no class loaded yet for this type
            // determing java class from javascript type:
            // oracle.adf.RichInputText to com.redheap.selenium.component.AdfInputText
            String simpleType = componentType.substring(componentType.lastIndexOf('.') + ".Rich".length()); // InputText
            String className = AdfComponent.class.getPackage().getName() + ".Adf" + simpleType;
            final Class<? extends C> c;
            try {
                c = (Class<? extends C>) ClassUtils.getClass(className);
                defaultComponents.put(componentType, c); // remember class to prevent class loading each time
            } catch (ClassNotFoundException e) {
                defaultComponents.put(componentType, null); // no class found and remember this
            }
        }
        Class<? extends C> cls = (Class<? extends C>) defaultComponents.get(componentType);
        if (cls == null) {
            throw new UnsupportedOperationException("unknown component type: " + componentType);
        }
        try {
            return ConstructorUtils.invokeConstructor(cls, driver, clientid);
        } catch (Exception e) {
            throw new WebDriverException("unable to instantiate adf component: " + clientid,
                                         e.getCause() != null ? e.getCause() : e);
        }
    }

    public static <T extends AdfComponent> T forElement(WebElement element) {
        RemoteWebElement rwe = (RemoteWebElement) element;
        RemoteWebDriver rwd = (RemoteWebDriver) rwe.getWrappedDriver();
        String clientid = (String) rwd.executeScript(JS_FIND_ANCESTOR_COMPONENT, element);
        return forClientId(rwd, clientid);
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

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId) {
        String clientid = (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID, getClientId(), relativeClientId);
        if (clientid == null) {
            return null;
        }
        return AdfComponent.forClientId(driver, clientid);
    }

    public <T extends AdfComponent> T findAdfComponentByLocator(String relativeLocator) {
        // use logic similar to relativeClientId:
        // - when starting with :, the locator is absolute and starts from root of page
        // - when starting with multiple :, first traverse up the naming containers
        // - when not starting with :, traverse up to nearest naming container (which chould be this component itself)
        // - can use indices with relative lookups:
        //   * lookup "[3]it2" from an af:table
        //   * lookup "::[3]it2" from tab1[1]:it2
        // TODO: refactor or duplicate this to com.redheap.selenium.AdfFinder to implement org.openqa.selenium.By
        Matcher matcher = RELATIVE_ID.matcher(relativeLocator);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("illegal locator: " + relativeLocator);
        }
        int qualifiers = matcher.group(1).length();
        String searchScopedId = matcher.group(2);
        boolean relative = (qualifiers == 0 || qualifiers > 1);
        String absoluteLocator;
        if (!relative) {
            absoluteLocator = searchScopedId;
        } else {
            String base = getAbsoluteLocator();
            // traverse up based on number of : prefixes in relative locator
            int upcount;
            if (qualifiers > 1) {
                upcount = qualifiers - 1;
            } else {
                // when base is not a NamingContainer we need to go up one first to get to the NamingContainer
                upcount = isNamingContainer() ? 0 : 1;
            }
            while (upcount > 0) {
                base = base.substring(0, base.lastIndexOf(":"));
                upcount--;
            }
            if (searchScopedId.startsWith("[")) {
                // indexed locator like [3]it4
                if (base.endsWith("]")) {
                    // strip index from base
                    base = base.substring(0, base.lastIndexOf("["));
                }
                absoluteLocator = base + searchScopedId; // no need for : separator
            } else {
                absoluteLocator = base + ":" + searchScopedId;
            }
        }
        logger.fine("locating component by absolute locator " + absoluteLocator);
        List<?> jsresult = (List<?>) executeScript(JS_FIND_COMPONENT_BY_LOCATOR, absoluteLocator);
        String componentType = (String) jsresult.get(0);
        String clientid = (String) jsresult.get(1);
        return AdfComponent.forClientId(driver, componentType, clientid);
    }

    public String getId() {
        // simple (local) id
        return getElement().getAttribute("id");
    }

    public String getClientId() {
        return clientid;
    }

    public String getAbsoluteLocator() {
        return (String) executeScript(JS_GET_ABSOLUTE_LOCATOR, getClientId());
    }

    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

    private boolean isNamingContainer() {
        return Boolean.TRUE.equals(executeScript(JS_IS_NAMINGCONTAINER, getClientId()));
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

    public boolean isDisabled() {
        return (Boolean) executeScript(JS_GET_DISABLED, getClientId());
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

    protected void scrollIntoView(WebElement element) {
        executeScript("arguments[0].scrollIntoView()", element);
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

    protected List<WebElement> findSubIdElements(String subid) {
        return (List<WebElement>) executeScript(JS_FIND_SUBID_ELEMENT, getClientId(), subid);
    }

    protected <T extends AdfComponent> T findSubIdComponent(String subid) {
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
        return AdfComponent.forClientId(driver, subClientId);
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

    public void hover() {
        WebElement element = getElement();
        // we use pause to give javascript timer to detect hover and show popup
        new Actions(getDriver()).moveToElement(element).pause(1000).perform();
        waitForPpr();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientid", clientid).build();
    }

}
