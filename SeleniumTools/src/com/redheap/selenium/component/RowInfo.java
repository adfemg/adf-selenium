package com.redheap.selenium.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.openqa.selenium.WebElement;

// rowInfo = {tr:tr, index:block.startRow + r, block:block};
public class RowInfo {

    private final WebElement row;
    private final int index;
    private final WebElement block;

    public RowInfo(WebElement row, int index, WebElement block) {
        this.row = row;
        this.index = index;
        this.block = block;
    }

    public WebElement getRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }

    public WebElement getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("row", row).append("index", index).append("block", block).build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(row).append(index).append(block).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RowInfo other = (RowInfo) obj;
        return new EqualsBuilder().append(this.row, other.row).append(this.index, other.index).append(this.block,
                                                                                                      other.block).build();
    }

}
