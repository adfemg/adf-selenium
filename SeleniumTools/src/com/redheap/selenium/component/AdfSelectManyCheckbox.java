package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * ADF Selenium representation of a {@code selectManyCheckbox} component.
 * @author Vincent Huiberts
 */
public class AdfSelectManyCheckbox extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_ITEM = "item"; // item[99] <input type='checkbox'> element
    private static final String SUBID_LABEL = "label"; // <label> element

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

    /**
     * Constructor, passthrough voor {@link AdfComponent} constructor.
     * @param driver
     * @param clientId
     */
    public AdfSelectManyCheckbox(final WebDriver driver, final String clientId) {
        super(driver, clientId);
    }

    /**
     * Click items in the list on the indices. This deselects an item if it was selected or selects it if it was not.
     * @param indices
     * @see #clickItemsByLabels
     */
    public void clickItemsByIndices(final long... indices) {
        for (int i = 0; i < indices.length; i++) {
            findItem(indices[i]).click();
            waitForPpr();
        }
    }

    /**
     * Click all items in the list which equal the labels. This deselects an item if it was selected or selects it if
     * it was not
     * @param labels
     * @see #clickItemsByIndices
     */
    public void clickItemsByLabels(final String... labels) {
        final long[] indices = new long[labels.length];
        for (int i = 0, n = labels.length; i < n; i++) {
            indices[i] = getItemIndexByLabel(labels[i]);
        }
        clickItemsByIndices(indices);
    }

    /**
     * Returns the value of the selected items in this selectManyCheckbox. Typically these are just string values
     * with the indices unless valuePassThrou is enabled which causes the server-side value to be used at the client
     * side.
     * @return values of items which could be empty but never {@code null}
     * @see #getValueLabels
     */
    @Override
    public List<Object> getValue() {
        // We weten hier dat we als SelectManyCheckbox een List<Object> terug krijgen uit een getValue().
        @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
        final List<Object> val = (List<Object>) super.getValue();
        return val == null ? Collections.emptyList() : new ArrayList<Object>(val);
    }

    /**
     * Returns the labels of the selected items in this selectManyCheckbox.
     * @return collection of item labels
     * @see #getValue
     */
    public List<String> getValueLabels() {
        final List<Object> vals = getValue();
        final List<String> retval = new ArrayList<>(vals.size());
        for (Object val : vals) {
            retval.add(getItemLabel(getItemIndexByValue(val)));
        }
        return retval;
    }

    /**
     * Returns the indices of the selected items in this selectManyCheckbox.
     * @return array of item indices
     */
    public long[] getValueIndices() {
        final List<Object> vals = getValue();
        final long[] retval = new long[vals.size()];
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
    public long getItemIndexByLabel(final String label) {
        final Long index = (Long) executeScript(JS_INDEX_BY_LABEL, getClientId(), label);
        return index == null ? -1 : Long.valueOf(index);
    }

    /**
     * Get the value of an item using the index.
     * @param value
     * @return int value
     */
    public long getItemIndexByValue(final Object value) {
        final Long index = (Long) executeScript(JS_INDEX_BY_VALUE, getClientId(), value);
        return index == null ? -1 : Long.valueOf(index);
    }

    /**
     * Get the label of an item using the index.
     * @param index
     * @return label of the item as String
     */
    public String getItemLabel(final long index) {
        final String label = (String) executeScript(JS_LABEL_BY_INDEX, getClientId(), index);
        return label;
    }

    /**
     * Get the value of an item using the index.
     * @param index
     * @return value of the item
     */
    public Object getItemValue(final long index) {
        final Object value = executeScript(JS_VALUE_BY_INDEX, getClientId(), index);
        return value;
    }

    /**
     * Get an item in the list by index.
     * @param index
     * @return The &lt;input type=checkbox&gt; item WebElement
     */
    protected WebElement findItem(final long index) {
        final Object value = getItemValue(index);
        return findSubIdElement(SUBID_ITEM + "[" + value + "]");
    }

    /**
     * Get the label.
     * @return the label WebElement
     */
    protected WebElement findLabel() {
        return findSubIdElement(SUBID_LABEL);
    }
}
