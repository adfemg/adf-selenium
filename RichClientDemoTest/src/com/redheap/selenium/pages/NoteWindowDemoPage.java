package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.component.AdfNoteWindow;
import com.redheap.selenium.component.AdfPopup;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class NoteWindowDemoPage extends Page {

    private final String scavengingLink = "dmoTpl:scavenger";
    private final String scavengingPopup = "dmoTpl:popupScavenger";
    private final String scavengingNoteWindow = "dmoTpl:nw1";

    public NoteWindowDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "noteWindow Demo";
    }

    public AdfCommandLink findScavengingLink() {
        return findAdfComponent(scavengingLink);
    }

    public AdfPopup findScavengingPopup() {
        return findAdfComponent(scavengingPopup);
    }

    public AdfNoteWindow findScavengingNoteWindow() {
        return findAdfComponent(scavengingNoteWindow);
    }

}
