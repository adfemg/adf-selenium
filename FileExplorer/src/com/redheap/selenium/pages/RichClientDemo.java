package com.redheap.selenium.pages;

import com.google.common.base.Function;

import com.redheap.selenium.conditions.AdfConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RichClientDemo extends PageObject {

    private By layoutTreeNode = By.id("tmplt:accMenu:tagGrouped:tree:4::di");
    private By fileExplorerLink = By.linkText("File Explorer");

    public RichClientDemo(WebDriver driver) {
        super(driver);
        String pageTitle = driver.getTitle();
        if (!"ADF Faces Rich Client Demos".equals(pageTitle)) {
            throw new IllegalStateException("Not on RichClientDemo page: " + pageTitle);
        }
    }

    public RichClientDemo clickLayoutTreeNode() {
        System.out.println("Clicking Layout node in the Tag Guide component tree");
        driver.findElement(layoutTreeNode).click();
        waitForPpr();
        System.out.println("done");
        return this;
    }

    public FileExplorer clickFileExplorerLink() {
        System.out.println("Clicking File Explorer link...");
        WebElement link = driver.findElement(fileExplorerLink);
        System.out.println(link);
        System.out.println(link.getClass());
        link.click();
        System.out.println("clicked link");
        return new FileExplorer(driver);
    }

    //      public LoginPagina typeGebruikersnaam(String gebruiker) {
    //        System.out.println("type gebruikersnaam " + gebruiker);
    //        driver.findElement(gebruikersnaamLocator).sendKeys(gebruiker);
    //        return this;
    //      }
    //
    //      public LoginPagina typeWachtwoord(String wachtwoord) {
    //        System.out.println("type wachtwoord " + wachtwoord);
    //        driver.findElement(wachtwoordLocator).sendKeys(wachtwoord);
    //        return this;
    //      }
    //
    //      public PersoonScherm login() {
    //        System.out.println("klik login knop");
    //        driver.findElement(inlogKnopLocator).click();
    //        new WebDriverWait(driver, 60).until(new Function<WebDriver, Boolean>(){
    //            public Boolean apply(WebDriver driver) {
    //              JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
    //              Object result = jsDriver.executeScript("return 1+3");
    //              System.out.println("result: " + result);
    //              return false;
    //            }
    //          });
    //        return new PersoonScherm(driver);
    //      }
    //
    //      public LoginPagina loginVerwachtFout() {
    //        driver.findElement(inlogKnopLocator).click();
    //        return new LoginPagina(driver);
    //      }
    //
    //      public PersoonScherm loginAlsGebruiker(String gebruiker, String wachtwoord) {
    //        typeGebruikersnaam(gebruiker);
    //        typeWachtwoord(wachtwoord);
    //        return login();
    //      }

    //  public LoginPagina loginMetFouteCredentials(String gebruiker, String wachtwoord) {
    //
    //  }
    //
    //  public String getFoutmelding() {
    //
    //  }

}
