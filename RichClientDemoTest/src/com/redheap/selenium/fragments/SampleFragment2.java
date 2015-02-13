package com.redheap.selenium.fragments;

import com.redheap.selenium.component.AdfRegion;

import org.openqa.selenium.By;

public class SampleFragment2 extends PageFragment {

    private By switchRegion1Button = By.id("cb1");

    public SampleFragment2(AdfRegion region) {
        super(region);
    }

}
