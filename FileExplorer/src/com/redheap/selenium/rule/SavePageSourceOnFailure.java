package com.redheap.selenium.rule;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class SavePageSourceOnFailure extends TestWatcher {

    private final File basedir;
    private final WebDriver driver;

    public SavePageSourceOnFailure(WebDriver driver) {
        this(driver, new File("."));
    }

    public SavePageSourceOnFailure(WebDriver driver, File basedir) {
        this.driver = driver;
        this.basedir = basedir;
    }

    @Override
    protected void failed(Throwable t, Description description) {
        try {
            File file = new File(basedir, description.getClassName() + "-" + description.getMethodName() + ".txt");
            file.getCanonicalFile().getParentFile().mkdirs();
            System.out.println("dumping page source " + file.getCanonicalPath());
            FileUtils.write(file, driver.getPageSource());
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebDriverException(e);
        }
    }

}
