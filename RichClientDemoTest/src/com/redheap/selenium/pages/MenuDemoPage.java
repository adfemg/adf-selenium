package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCommandMenuItem;
import com.redheap.selenium.component.AdfMenu;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class MenuDemoPage extends Page {
    
    private final String fileMenu = "dmoTpl:menu1";
    private final String newSubmenu = "dmoTpl:menu2";
    private final String disabledMenu = "dmoTpl:menu8";
    private final String closeMenuItem = "dmoTpl:cmi9";
    
    public MenuDemoPage(WebDriver webDriver) {
        super(webDriver);
    }
    
    @Override
    protected String getExpectedTitle() {
        return "menu Demo";
    }
    
    public AdfMenu findFileMenu() {
        return findAdfComponent(fileMenu);
    } 
   
    public AdfCommandMenuItem findCloseItem(){
        return findAdfComponent(closeMenuItem);
    }
   
    public AdfMenu findNewSubMenu() {
        return findAdfComponent(newSubmenu);
    } 
  
    public AdfMenu findDisabledMenu() {
        return findAdfComponent(disabledMenu);
    }  
}
