package com.redheap.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;


public abstract class AdfConditions {

    private AdfConditions() {
    }

    public static ExpectedCondition<Boolean> clientSynchedWithServer() {
        // return false if AdfPage object and functions do not exist
        // if they do exist return true if page is fully loaded and ready or reason why this is not completed yet
        return javascriptExpressionTrue("typeof AdfPage !== 'undefined' && " +
                                        "typeof AdfPage.PAGE !== 'undefined' && " +
                                        "typeof AdfPage.PAGE.isSynchronizedWithServer === 'function' && " +
                                        "(AdfPage.PAGE.isSynchronizedWithServer() || " +
                                        "(typeof AdfPage.PAGE.whyIsNotSynchronizedWithServer === 'function' && " +
                                        "AdfPage.PAGE.whyIsNotSynchronizedWithServer()))");
    }

    public static ExpectedCondition<Boolean> javascriptExpressionTrue(final String jsExpression) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                Object result = jsDriver.executeScript("return " + jsExpression);
                System.out.println("js result: " + result + " [" + jsExpression + "]");
                return Boolean.TRUE.equals(result);
            }
        };
    }

}
