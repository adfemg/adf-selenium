package com.redheap.selenium;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;


public abstract class AdfConditions {

    public static final long DFLT_WAIT_TIMEOUT_SECS = 30;
    public static final long DFLT_WAIT_INTERVAL_MSECS = 500;

    private static final Logger logger = Logger.getLogger(AdfConditions.class.getName());

    private AdfConditions() {
    }

    @Deprecated
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

    public static ExpectedCondition<Boolean> javascriptExpressionTrue(final String jsExpression, final Object... args) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                logger.log(Level.FINER, "executing condition javascript: {0}", jsExpression);
                Object result = jsDriver.executeScript(jsExpression, args);
                logger.log(Level.FINER, "js result: {0}", result);
                return Boolean.TRUE.equals(result);
            }
        };
    }

}
