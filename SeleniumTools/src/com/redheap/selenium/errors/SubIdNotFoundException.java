package com.redheap.selenium.errors;

import org.openqa.selenium.WebDriverException;

public class SubIdNotFoundException extends WebDriverException {

    public SubIdNotFoundException() {
        super();
    }

    public SubIdNotFoundException(String msg) {
        super(msg);
    }

    public SubIdNotFoundException(Throwable cause) {
        super(cause);
    }

    public SubIdNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
