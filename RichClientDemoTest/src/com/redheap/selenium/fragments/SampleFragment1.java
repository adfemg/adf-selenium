package com.redheap.selenium.fragments;

import com.redheap.selenium.component.AdfCommandButton;
import com.redheap.selenium.component.AdfRegion;
import com.redheap.selenium.page.PageFragment;

import java.util.logging.Logger;


public class SampleFragment1 extends PageFragment {

    private final String switchRegion2ButtonId = "cb1";

    private static final Logger logger = Logger.getLogger(SampleFragment1.class.getName());

    public SampleFragment1(AdfRegion region) {
        super(region);
    }

    public SampleFragment2 clickRegion2Button() {
        logger.fine("Clicking 'switch to region 2' button");
        AdfCommandButton button = findAdfComponent(switchRegion2ButtonId, AdfCommandButton.class);
        button.click();
        return navigatedTo(SampleFragment2.class);
    }

}
