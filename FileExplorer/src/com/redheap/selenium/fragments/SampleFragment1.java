package com.redheap.selenium.fragments;

import com.redheap.selenium.component.AdfButton;
import com.redheap.selenium.component.AdfRegion;


public class SampleFragment1 extends PageFragment {

    private final String switchRegion2ButtonId = "cb1";

    public SampleFragment1(AdfRegion region) {
        super(region);
    }

    public SampleFragment2 clickRegion2Button() {
        System.out.println("Clicking 'switch to region 2' button");
        AdfButton button = findAdfComponent(switchRegion2ButtonId, AdfButton.class);
        button.click();
        waitForPpr();
        return navigatedTo(SampleFragment2.class);
    }

}
