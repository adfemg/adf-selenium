package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;
import com.redheap.selenium.errors.SubIdNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfTextEditor extends UixInput {

    // subid's at http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String JS_FIND_CONTENT_NODE =
        JS_FIND_ELEMENT +
        "return (comp.getEditMode()==\"wysiwyg\"?AdfSubIdUtils.getSubIdElementById(comp, \"cont\"):AdfSubIdUtils.getSubIdElementById(comp, \"src\"));";
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
    private static final String SUBID_mode_source = "_afrModeCode"; // Editor mode source button
    private static final String SUBID_mode_wysiwyg = "_afrModeRichText"; // Editor mode wysiwyg button


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
     * Gets the editor mode source button
     * @return editor mode source button
     */
    public AdfCommandToolbarButton findEditorModeSourceButton() {
        return findSubIdComponent(SUBID_mode_source);
    }

    /**
     * Gets the editor mode wysiwyg button
     * @return editor mode wysiwyg button
     */
    public AdfCommandToolbarButton findEditorModeWysiwygButton() {
        return findSubIdComponent(SUBID_mode_wysiwyg);
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
     * Returns the submittedValue instead of the value.
     * getValue() javascript is not implemented and always returns null for RTE.
     * @return submittedValue
     */
    @Override
    public Object getValue() {
        return getSubmittedValue();
    }

    /**
     * Returns the content node depending on the Editor Mode (source or wysiwyg).
     * @return text editor content node.
     */
    @Override
    protected WebElement findContentNode() {
        final Object result = executeScript(JS_FIND_CONTENT_NODE, getClientId());
        if (result instanceof WebElement) {
            return (WebElement) result;
        } else {
            throw new SubIdNotFoundException("could not find content node for " + getElement());
        }
    }

    /**
     * Method to get the Content Label WebElement.
     * @return the Content Label WebElement
     */
    protected WebElement findlabel() {
        return findSubIdElement(SUBID_label);
    }

}
