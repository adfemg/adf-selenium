package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPanelHeader extends AdfComponent {
    
    public AdfPanelHeader(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }
    
    /**
     *
     * @return The text value of the PanelHeader (the Title)
     */
    public String getText(){
        return (String)getProperty("text");
    }
    
    /**
     *
     * @return The header level (H1 through H6 HTML header level)
     */
    public long getHeaderLevel(){
        return (Long)getProperty("headerLevel");
    }
    
}
