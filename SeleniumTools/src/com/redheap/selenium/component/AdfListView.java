package com.redheap.selenium.component;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * AdfListView component class.
 * TODO Deze class mag weg zodra deze is opgenomen in adf-selenium
 */
public class AdfListView extends AdfComponent {

    private static final String JS_SELECTED_ROWS =
        JS_FIND_PEER +
        "var keys=Object.keys(comp.getSelectedRowKeys()); var retval=[]; keys.forEach(function(key){retval.push(peer.getRowIndex(key))}); return retval;";

    //TODO: geen geweldige manier maar comp.getRows() werkt niet.
    //private static final String JS_GET_ROW_COUNT = JS_FIND_COMPONENT + "return comp.getRows();";
    private static final String JS_GET_ROW_COUNT =
        JS_FIND_COMPONENT + "var list=comp.getDescendantComponents(); var li=0; for(var i=0;i<list.length;i++ ){ " +
        "if( list[i]._componentType == 'oracle.adf.RichListItem' ) li++; } return li;";

    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY =
        JS_FIND_COMPONENT +
        "var child=comp.findComponent(arguments[1],arguments[2]); return child?child.getClientId():null";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX =
        JS_FIND_PEER + "var idx=peer.getRowKey(arguments[2]); if (!idx){return null}" +
        "var child=comp.findComponent(arguments[1],idx); return child?child.getClientId():null";

    /**
     * Constructor.
     * @param webDriver
     * @param clientId
     */
    public AdfListView(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    /**
     * Return the indices of selected row.
     * @return
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Get the rowcount of the AdfListView.
     * @return
     */
    public long getRowCount() {
        return ((Number) executeScript(JS_GET_ROW_COUNT, getClientId())).longValue();
    }

    /**
     * Find a given AdfComponent.
     * @param <T>
     * @param relativeClientId
     * @param rowKey
     * @return
     */
    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, String rowKey) {
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWKEY, getClientId(), relativeClientId, rowKey);
        if (clientid == null) {
            return null;
        }
        return AdfComponent.forClientId(getDriver(), clientid);
    }

    // TODO werkt niet altijd. Bij ZittingDetails in Zitting tab werkt het wel,
    // in ZaakOpZittingDetails bij activiteit PlaatsZaakOpZitting ( praktisch een kopie van ZittingDetails )
    // werkt het niet.

    /**
     * Find the given AdfComponent.
     * @param <T>
     * @param relativeClientId
     * @param rowIndex
     * @return
     */
    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, int rowIndex) {
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ROWINDEX, getClientId(), relativeClientId,
                                   rowIndex);
        if (clientid == null) {
            return null;
        }
        T retval = AdfComponent.forClientId(getDriver(), clientid);
        retval.scrollIntoView();
        return retval;
    }
}
