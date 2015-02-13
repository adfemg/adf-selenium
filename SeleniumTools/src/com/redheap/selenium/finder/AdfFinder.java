package com.redheap.selenium.finder;

import org.openqa.selenium.By;


public abstract class AdfFinder {

    private AdfFinder() {
    }

    public static By treeNodeByLabel(String label) {
        return new TreeNodeByLabel(label);
    }

}
