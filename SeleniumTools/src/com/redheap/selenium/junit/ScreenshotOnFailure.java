package com.redheap.selenium.junit;

import com.redheap.selenium.output.FileOutputType;

import java.io.File;
import java.io.IOException;

import java.util.Set;
import java.util.logging.Logger;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class ScreenshotOnFailure extends TestWatcher {

    private final File basedir;
    private final WebDriver driver;

    private static final Logger logger = Logger.getLogger(ScreenshotOnFailure.class.getName());

    public ScreenshotOnFailure(WebDriver driver) {
        this(driver, new File("."));
    }

    public ScreenshotOnFailure(WebDriver driver, File basedir) {
        this.driver = driver;
        this.basedir = basedir;
    }

    @Override
    protected void failed(Throwable t, Description description) {
        String oldWindow = driver.getWindowHandle();
        try {
            Set<String> windows = driver.getWindowHandles();
            int idx = 0;
            String baseFileName = description.getClassName() + "-" + description.getMethodName();
            for (String guid : windows) {
                StringBuilder fileName = new StringBuilder(baseFileName);
                if (windows.size() > 1) {
                    fileName.append("-").append((idx++));
                }
                fileName.append(".png");
                File file = new File(basedir, fileName.toString());
                file.getCanonicalFile().getParentFile().mkdirs();
                logger.info("*************** dumping error screenshot " + file.getCanonicalPath());
                try {
                    driver.switchTo().window(guid);
                    ((TakesScreenshot) driver).getScreenshotAs(new FileOutputType(file));
                } catch (RuntimeException e) {
                    // ignore and continue with next window
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebDriverException(e);
        } finally {
            driver.switchTo().window(oldWindow); // restore original active window
        }
    }

}
