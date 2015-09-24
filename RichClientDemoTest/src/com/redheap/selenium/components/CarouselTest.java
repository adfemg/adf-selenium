package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCarousel;
import com.redheap.selenium.pages.CarouselDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class CarouselTest extends PageTestBase<CarouselDemoPage> {

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

    @Override
    protected Class<CarouselDemoPage> getPageClass() {
        return CarouselDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "carousel.jspx";
    }
}
