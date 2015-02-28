package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCarousel;
import com.redheap.selenium.component.AdfImage;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class CarouselDemoPage extends Page {

    private final String carousel = "pt:carousel";
    private final String carouselImage = "img";

    public CarouselDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "carousel Demo";
    }

    public AdfCarousel findCarousel() {
        return findAdfComponent(carousel);
    }

    public AdfImage findCarouselImage() {
        return findCarousel().findAdfComponent(carouselImage);
    }

}
