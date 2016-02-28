package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AdfSelectOrderShuttle extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <div> element
    private static final String SUBID_trailUl = "trailUl"; // <ul> element Selected items
    private static final String SUBID_item = "item"; // <input type='listbox'> element
    private static final String SUBID_label = "label"; // <label> element
    private static final String SUBID_move = "move"; // <a> element
    private static final String SUBID_moveAll = "moveall"; // <a> element
    private static final String SUBID_moveBottom = "movebottom"; // <a> element
    private static final String SUBID_moveDown = "movedown"; // <a> element
    private static final String SUBID_moveTop = "movetop"; // <a> element
    private static final String SUBID_moveUp = "moveup"; // <a> element
    private static final String SUBID_remove = "remove"; // <a> element
    private static final String SUBID_removeAll = "removeall"; // <a> element


    private static final String JS_INDEX_BY_LABEL =
        JS_FIND_COMPONENT +
        "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getLabel()===arguments[1]) return i}; return null;";
    private static final String JS_INDEX_BY_VALUE =
        JS_FIND_COMPONENT +
        "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getValue()===arguments[1]) return i}; return null;";
    private static final String JS_LABEL_BY_INDEX =
        JS_FIND_COMPONENT +
        "return comp.getSelectItems()[arguments[1]] && comp.getSelectItems()[arguments[1]].getLabel();";
    private static final String JS_VALUE_BY_INDEX =
        JS_FIND_COMPONENT +
        "return comp.getSelectItems()[arguments[1]] && comp.getSelectItems()[arguments[1]].getValue();";


    public AdfSelectOrderShuttle(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    /**
     * Click items in the list on the indices. This deselects an item if it was selected or selects it if it was not.
     * @param indices
     * @see #clickItemsByLabels
     */
    public void clickItemsByIndices(long... indices) {
        //Using Actions to CTRL click all the items.
        Actions a = new Actions(getDriver());
        for (int i = 0; i < indices.length; i++) {
            WebElement elem = findItem(indices[i]); //returns the hidden checkbox webelement
            //Ignore non-existent indices
            if (elem != null) {
                //The parent of the checkbox is visible, and thus clickable
                elem = (WebElement) executeScript("return arguments[0].parentElement", elem);
                executeScript("arguments[0].scrollIntoView()", elem); // needed in 12.2
                a.click(elem);
                //press CTRL after first click
                if(i==0) {
                    a.keyDown(Keys.CONTROL);
                }
            }
        }
        a.keyUp(Keys.CONTROL).perform();
        waitForPpr();
    }

    /**
     * Click items in the selected list on the indices. This deselects an item if it was selected or selects it if it was not.
     * @param indices
     * @see #clickItemsByLabels
     */
    public void clickSelectedItemsByIndices(long... indices) {
        //Using Actions to CTRL click all the items.
        Actions a = new Actions(getDriver());
        for (int i = 0; i < indices.length; i++) {
            WebElement elem = findSubIdElement(SUBID_trailUl);
            elem = (WebElement) executeScript("return arguments[0].childNodes[arguments[1]]", elem, indices[i]);
            //Ignore non-existent indices
            if (elem != null) {
                executeScript("arguments[0].scrollIntoView()", elem); // needed in 12.2
                a.click(elem);
                //press CTRL after first click
                if(i==0) {
                    a.keyDown(Keys.CONTROL);
                }
            }
        }
        a.keyUp(Keys.CONTROL).perform();
        waitForPpr();
    }

    /**
     * Click all items in the list which equal the labels. This deselects an item if it was selected or selects it if
     * it was not
     * @param labels
     * @see #clickItemsByIndices
     */
    public void clickItemsByLabels(String... labels) {
        long[] indices = new long[labels.length];
        for (int i = 0, n = labels.length; i < n; i++) {
            indices[i] = getItemIndexByLabel(labels[i]);
        }
        clickItemsByIndices(indices);
    }

    /**
     * Returns the value of the selected items in this selectManyShuttle. Typically these are just string values
     * with the indices unless valuePassThrough is enabled which causes the server-side value to be used at the client
     * side.
     * @return values of items which could be empty but never {@code null}
     * @see #getValueLabels
     */
    @Override
    public List<Object> getValue() {
        @SuppressWarnings("unchecked")
        List<Object> val = (List<Object>) super.getValue();
        return val == null ? Collections.emptyList() : new ArrayList<Object>(val);
    }

    /**
     * Returns the labels of the selected items in this selectManyShuttle.
     * @return collection of item labels
     * @see #getValue
     */
    public List<String> getValueLabels() {
        List<Object> vals = getValue();
        List<String> retval = new ArrayList<String>(vals.size());
        for (Object val : vals) {
            retval.add(getItemLabel(getItemIndexByValue(val)));
        }
        return retval;
    }

    public long[] getValueIndices() {
        List<Object> vals = getValue();
        long[] retval = new long[vals.size()];
        for (int i = 0, n = vals.size(); i < n; i++) {
            retval[i] = getItemIndexByValue(vals.get(i));
        }
        return retval;
    }

    /**
     * Get the value of an item using the label.
     * @param label
     * @return int value
     */
    public long getItemIndexByLabel(String label) {
        Long index = (Long) executeScript(JS_INDEX_BY_LABEL, getClientId(), label);
        return index == null ? -1 : Long.valueOf(index);
    }

    public long getItemIndexByValue(Object value) {
        Long index = (Long) executeScript(JS_INDEX_BY_VALUE, getClientId(), value);
        return index == null ? -1 : Long.valueOf(index);
    }

    public String getItemLabel(long index) {
        String label = (String) executeScript(JS_LABEL_BY_INDEX, getClientId(), index);
        return label;
    }

    public Object getItemValue(long index) {
        Object value = executeScript(JS_VALUE_BY_INDEX, getClientId(), index);
        return value;
    }

    /**
     * Method to get the content.
     * @return the content WebElement
     */
    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    /**
     * Get an item in the list by index.
     * @param index
     * @return The &lt;input type=checkbox&gt; item WebElement
     */
    protected WebElement findItem(long index) {
        Object value = getItemValue(index);
        return findSubIdElement(SUBID_item + "[" + value + "]");
    }

    /**
     * Get the label.
     * @return the label WebElement
     */
    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }

    /**
     * Method to click on the &lt;a&gt; element to move an available item to the selected list.
     */
    public void clickMove() {
        findSubIdElement(SUBID_move).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move all available items to the selected list.
     */
    public void clickMoveAll() {
        findSubIdElement(SUBID_moveAll).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move a selected item to the bottom of the selected list.
     */
    public void clickMoveBottom() {
        findSubIdElement(SUBID_moveBottom).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move a selected item down in the selected list.
     */
    public void clickMoveDown() {
        findSubIdElement(SUBID_moveDown).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move a selected item to the top of the selected list.
     */
    public void clickMoveTop() {
        findSubIdElement(SUBID_moveTop).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move a selected item up in the selected list.
     */
    public void clickMoveUp() {
        findSubIdElement(SUBID_moveUp).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move a selected item back to the available list.
     */
    public void clickRemove() {
        findSubIdElement(SUBID_remove).click();
    }

    /**
     * Method to click on the &lt;a&gt; element to move all selected items back to the available list.
     */
    public void clickRemoveAll() {
        findSubIdElement(SUBID_removeAll).click();
    }
}
