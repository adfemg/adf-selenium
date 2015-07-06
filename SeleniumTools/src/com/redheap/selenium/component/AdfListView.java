package com.redheap.selenium.component;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class AdfListView extends AdfComponent {

    private static final String JS_SELECTED_ROWS =
        JS_FIND_PEER +
        "var keys=Object.keys(comp.getSelectedRowKeys()); var retval=[]; keys.forEach(function(key){retval.push(peer.getRowIndex(key))}); return retval;";

    public AdfListView(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    public List<Integer> getSelectedRows() {
        return rowIndices((List<Long>) executeScript(JS_SELECTED_ROWS, getClientId()));
    }

    private List<Integer> rowIndices(List<Long> longs) {
        List<Integer> retval = new ArrayList<Integer>(longs.size());
        for (Long l : longs) {
            retval.add(l.intValue());
        }
        return retval;
    }
}
