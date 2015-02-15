package com.redheap.selenium;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByXPath;


public abstract class AdfFinder {

    private static final Logger logger = Logger.getLogger(AdfFinder.class.getName());

    private AdfFinder() {
    }

    public static By treeNodeByLabel(String label) {
        return new TreeNodeByLabel(label);
    }

    public static class TreeNodeByLabel extends By {

        private final String label;

        public TreeNodeByLabel(String label) {
            this.label = label;
        }

        @Override
        public List<WebElement> findElements(SearchContext searchContext) {
            logger.finer("finding label " + label + " in " + searchContext);
            String safeLabel;
            if (label.contains("'")) {
                // replace each ' with ', "'", ' so we can use it in a concat expression
                // makes concat('O', "'", 'Neil') from O'Neil
                safeLabel = "concat('" + label.replace("'", "', \"'\", '") + "')";
            } else {
                safeLabel = "'" + label + "'";
            }
            // start from . for instances where we are searching within a scoped element
            // tr elements having a _afrrk (rowkey) attribute are considered tree nodes
            String xpath = ".//tr[@_afrrk and .//span[text()=" + safeLabel + "]]";
            logger.finer("using xpath " + xpath);
            return ((FindsByXPath) searchContext).findElementsByXPath(xpath);
        }

        @Override
        public String toString() {
            return TreeNodeByLabel.class.getSimpleName() + ": " + label;
        }
    }

}
