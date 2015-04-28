package com.redheap.selenium.component;

import com.redheap.selenium.domain.FacesMessageDomain;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class AdfRegion extends AdfComponent {

    public AdfRegion(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected Object executeScript(CharSequence script, Object[] args) {
        return super.executeScript(script, args);
    }

    public Map getAllMessages() {
       return FacesMessageDomain.getAllMessages((JavascriptExecutor)getDriver());
    }

}
