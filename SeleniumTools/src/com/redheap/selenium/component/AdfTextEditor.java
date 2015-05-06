package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfTextEditor extends UixInput {

    // subid's at http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_cont = "cont"; // Gets the IFrame
    private static final String SUBID_content =
        "content"; // Gets the Head Div with all other content like toolbar,IFrame etc.
    private static final String SUBID_label = "label"; // Gets the content_Label
    private static final String SUBID_bold_button = "_afrBold"; // Bold button
    private static final String SUBID_italic_button = "_afrItalic"; // Italic butotn
    private static final String SUBID_underline_button = "_afrUnderline"; // Underline button
    private static final String SUBID_undo = "_afrUndo"; // Undo button
    private static final String SUBID_redo = "_afrUnderline"; // Underline button
    private static final String SUBID_outdent = "_afrOutdent"; // Outdent button
    private static final String SUBID_indent = "_afrIndent"; // Indent button
    private static final String SUBID_ordered_list = "_afrOrdList"; // Orderedlist button
    private static final String SUBID_unordered_list = "_afrUnordList"; // Unorderedlist button

    public AdfTextEditor(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    /**
     * Gets the bold button
     * @return bold button
     */
    public AdfCommandToolbarButton findBoldButton() {
        return findSubIdComponent(SUBID_bold_button);
    }

    /**
     * Gets the italic button
     * @return italic button
     */
    public AdfCommandToolbarButton findItalicButton() {
        return findSubIdComponent(SUBID_italic_button);
    }

    /**
     * Gets the underline button
     * @return underline button
     */
    public AdfCommandToolbarButton findUnderlineButton() {
        return findSubIdComponent(SUBID_underline_button);
    }

    /**
     * Gets the undo button
     * @return undo button
     */
    public AdfCommandToolbarButton findUndoButton() {
        return findSubIdComponent(SUBID_undo);
    }

    /**
     * Gets the redo button
     * @return redo button
     */
    public AdfCommandToolbarButton findRedoButton() {
        return findSubIdComponent(SUBID_redo);
    }

    /**
     * Gets the outdent button
     * @return outdent button
     */
    public AdfCommandToolbarButton findOutdentButton() {
        return findSubIdComponent(SUBID_outdent);
    }

    /**
     * Gets the indent button
     * @return indent button
     */
    public AdfCommandToolbarButton findIndentButton() {
        return findSubIdComponent(SUBID_indent);
    }

    /**
     * Gets the ordered list button
     * @return orderedlist button
     */
    public AdfCommandToolbarButton findOrderedListButton() {
        return findSubIdComponent(SUBID_ordered_list);
    }

    /**
     * Gets the unordered list button
     * @return unorderedlist button
     */
    public AdfCommandToolbarButton findUnorderedListButton() {
        return findSubIdComponent(SUBID_unordered_list);
    }

    /**
     * Gets the component label.
     * @return the component label
     */
    public String getLabel() {
        WebElement element = findlabel();
        return element.getText();
    }

    /**
     * Method to get the IFrame WebElement.
     * @return the IFrame WebElement
     */
    protected WebElement findCont() {
        return findSubIdElement(SUBID_cont);
    }

    @Override
    public Object getValue() {
        // getValue() javascript is not implemented and always returns null for RTE. So just use the submittedValue
        return getSubmittedValue();
    }

    /**
     * Method to get the Content WebElement.
     * <p>
     * This element has both the Toolbar as well as the IFrame with the content.
     * @return the Content WebElement
     */
    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    /**
     * Method to get the Content Label WebElement.
     * @return the Content Label WebElement
     */
    protected WebElement findlabel() {
        return findSubIdElement(SUBID_label);
    }

}