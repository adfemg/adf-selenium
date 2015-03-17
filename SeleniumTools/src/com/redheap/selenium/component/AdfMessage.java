package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfMessage extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_detail = "detail"; // detail[idx] 0 gives <td>, while 1+ gives <div>'s
    private static final String SUBID_intro = "intro"; // intro[idx] 0 gives <td> for Messages for this component are listed below
    private static final String SUBID_summary = "summary"; // summary[idx]

    private static final String JS_GET_MESSAGE = JS_FIND_COMPONENT + "return comp.getMessage();";
    private static final String JS_GET_MESSAGE_TYPE = JS_FIND_COMPONENT + "return comp.getMessageType();";

    public AdfMessage(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public String getMessage() {
        return (String) executeScript(JS_GET_MESSAGE, getClientId());
    }

    public String getMessageType() {
        return (String) executeScript(JS_GET_MESSAGE_TYPE, getClientId());
    }

}
