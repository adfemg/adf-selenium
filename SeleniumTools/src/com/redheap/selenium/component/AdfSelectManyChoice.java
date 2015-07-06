package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfSelectManyChoice extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <input type='text'> element
    private static final String SUBID_drop = "drop"; // <a> element to expand
    private static final String SUBID_item = "item"; // item[99] <input type='checkbox'> element
    private static final String SUBID_label = "label"; // <label> element

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

    public AdfSelectManyChoice(WebDriver driver, String clientId) {
        super(driver, clientId);
    }

    /**
     * Click items in the list on the indices. This deselects an item if it was selected or selects it if it was not.
     * @param indices
     * @see #clickItemsByLabels
     */
    public void clickItemsByIndices(long... indices) {
        findDrop().click(); // expand list
        // click items within list
        for (int i = 0; i < indices.length; i++) {
            findItem(indices[i]).click();
        }
        findDrop().click(); // collapse list
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
     * Returns the value of the selected items in this selectManyChoice. Typically these are just string values
     * with the indices unless valuePassThrou is enabled which causes the server-side value to be used at the client
     * side.
     * @return values of items which could be empty but never {@code null}
     * @see #getValueLabels
     */
    @Override
    public List<Object> getValue() {
        List<Object> val = (List<Object>) super.getValue();
        return val == null ? Collections.emptyList() : new ArrayList<Object>(val);
    }

    /**
     * Returns the labels of the selected items in this selectManyChoice.
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
     * Get the value of an item using the label
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
     * Method to get the content
     * @return the content WebElement
     */
    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    /**
     * Method to get the &lt;a&gt; element to expand or collapse the dropdown list
     * @return the dropdown WebElement
     */
    protected WebElement findDrop() {
        return findSubIdElement(SUBID_drop);
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
     * Get the label
     * @return the label WebElement
     */
    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }

}
