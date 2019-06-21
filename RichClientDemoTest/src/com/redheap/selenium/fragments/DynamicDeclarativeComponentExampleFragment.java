package com.redheap.selenium.fragments;

import com.redheap.selenium.component.AdfDynamicDeclarativeComponent;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.page.DeclarativeComponentFragment;

public class DynamicDeclarativeComponentExampleFragment extends DeclarativeComponentFragment {
    
    public DynamicDeclarativeComponentExampleFragment(AdfDynamicDeclarativeComponent dclr) {
        super(dclr);
    }
    
    /*
     * XSLT Generated 
     * This is Page Object Model for the tested ADF Page.
     * The model includes java code for those of Adf Faces Tag which are listed xsl:template match=. You can change this list according to your needs and re-generate code for this class.
     * Generated code includes: 
     *      - String constant with ADF component AbsoluteId value. This constant then is used to locate component by corresponding findAdfComponent() method of SeleniumTools library.
     *      - find-method which returm object instance representing ADF component.
     * См.
     * https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/oracle/adf/view/js/base/AdfPage.html#findComponentByAbsoluteId_String_
     * https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/oracle/adf/view/js/component/AdfUIComponent.html#findComponent_String__Boolean_
    */


    /*
     * Methods to locate and interract with inner components of DynamicDeclarativeComponentExample
    */  

    static final String _dcot1OutputText = "dcot1";
                    
    public AdfOutputText find_dcot1 () {
            return findAdfComponent(_dcot1OutputText);
    }
                    
    static final String _lwdcInputTextInputText = "lwdcInputText";
                    
    public AdfInputText find_lwdcInputText () {
            return findAdfComponent(_lwdcInputTextInputText);
    }
                    
    static final String _dcot2OutputText = "dcot2";
                    
    public AdfOutputText find_dcot2 () {
            return findAdfComponent(_dcot2OutputText);
    }
                    
    static final String _dci1Iterator = "dci1";
            
    public static final String get_dci1Iterator() {
        return _dci1Iterator;
    }
    
    /*
     * Methods to locate stamped-components of af:iterator.
     * For stamped-components of af:iterator is applied general template which generates AbsoluteId and general find-Methods
     * You need manually change them into componentId and find-by-index methods by concatinanting iterator absoluteId, index, componentId.
     * Also Mention situation when iterators are hierarhical
     * 
     * Below I implement changes after xslt generation
    */           
                    
    static final String _itemsTextInputText = "itemsText";
                    
    public AdfInputText find_itemsText (int index) {
            return findAdfComponent(get_dci1Iterator() +":" + index + ":" +_itemsTextInputText);
    }
                    
    static final String _dcot3OutputText = "dcot3";
                    
    public AdfOutputText find_dcot3 () {
            return findAdfComponent(_dcot3OutputText);
    }
                    
    static final String _dcot4OutputText = "dcot4";
                    
    public AdfOutputText find_dcot4 () {
            return findAdfComponent(_dcot4OutputText);
    }
                    
    static final String _dcot5OutputText = "dcot5";
                    
    public AdfOutputText find_dcot5 () {
            return findAdfComponent(_dcot5OutputText);
    }
                    
    static final String _dcot6OutputText = "dcot6";
                    
    public AdfOutputText find_dcot6 () {
            return findAdfComponent(_dcot6OutputText);
    }
}
