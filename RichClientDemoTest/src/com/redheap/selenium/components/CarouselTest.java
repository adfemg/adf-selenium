package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCarousel;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.CarouselDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class CarouselTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<CarouselDemoPage> pages =
        new PageProvider(CarouselDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/carousel.jspx";

    @Test
    public void testCarouselBrowse() {
        AdfCarousel carousel = pages.goHome().findCarousel();
        assertEquals("0", carousel.getCurrentItemKey());
        assertEquals("Alice in Wonderland", carousel.getItemText());
        carousel.clickNext();
        assertEquals("1", carousel.getCurrentItemKey());
        assertEquals("Basic Instinct", carousel.getItemText());
        carousel.clickPrevious();
        assertEquals("0", carousel.getCurrentItemKey());
        assertEquals("Alice in Wonderland", carousel.getItemText());
    }

    @Test
    public void testCarouselJump() {
        AdfCarousel carousel = pages.goHome().findCarousel();
        carousel.setCurrentItemKey("10");
        assertEquals("10", carousel.getCurrentItemKey());
        assertEquals("Final Destination", carousel.getItemText());
    }

    @Test
    public void testCarouselItemCount() {
        AdfCarousel carousel = pages.goHome().findCarousel();
        assertEquals(25, carousel.getItemDisplayCount());
        assertEquals(30, carousel.getRowCount());
    }

    @Test
    public void testCarouselFetchByBrowse() {
        AdfCarousel carousel = pages.goHome().findCarousel();
        carousel.setCurrentItemKey("23"); // jump to one before last as setting key never does a fetch
        assertEquals("23", carousel.getCurrentItemKey());
        assertEquals("Stargate", carousel.getItemText());
        carousel.clickNext(); // clicking navigation buttons does do a fetch when needed
        assertEquals("24", carousel.getCurrentItemKey());
        assertEquals("Terminator 2 Judgement Day", carousel.getItemText());
        carousel.clickNext(); // this really gets us to the second range page
        assertEquals("25", carousel.getCurrentItemKey());
        assertEquals("Terminator 2 T2 Extreme", carousel.getItemText());
        // still only 25 items on the client
        assertEquals(25, carousel.getItemDisplayCount());
    }

    public static void main(String[] args) {
        String[] args2 = { CarouselTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
