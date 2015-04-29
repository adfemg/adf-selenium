package com.redheap.selenium.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.JavascriptExecutor;


/**
 * This class acts as a wrapper containing all FacesMessages on a page as well as some convenience methods.
 */
public class PageMessageWrapper {
    private Map<String, List<FacesMessageDomain>> facesMessages = new HashMap<String, List<FacesMessageDomain>>();

    /**
     * This method gets all facesmessages on the page.
     * @param A {@link org.openqa.selenium.JavascriptExecutor JavascriptExecutor} as we need to execute some JavaScript
     * @return A {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper} with all facesmessages on
     * the page as wel as some convenience methods.
     */
    public static PageMessageWrapper getAllMessages(JavascriptExecutor javascriptExecutor) {
        ArrayList<ArrayList> scriptResult =
            (ArrayList<ArrayList>) javascriptExecutor.executeScript(FacesMessageDomain.JS_GET_PAGE_MESSAGES, "");
        PageMessageWrapper pageMessageWrapper = new PageMessageWrapper();

        if (scriptResult != null) {
            //loop over all components
            for (ArrayList compMessageList : scriptResult) {
                String componentId = (String) compMessageList.get(0);
                List<ArrayList> componentFacesMessages = (ArrayList<ArrayList>) compMessageList.get(1);
                List<FacesMessageDomain> facesMessageDomainList = new ArrayList<FacesMessageDomain>();
                for (ArrayList compMessage : componentFacesMessages) {
                    facesMessageDomainList.add(new FacesMessageDomain(componentId, compMessage));
                }
                pageMessageWrapper.addAllComponentMessages(componentId, facesMessageDomainList);
            }


        }
        return pageMessageWrapper;
    }

    /**
     * Private method that will add an entry to the internal map with all messages for a specific component.
     * @param componentId A String with the componentId of the facesmessages.
     * @param facesMessageDomainList A list of {@link com.redheap.selenium.domain.FacesMessageDomain FacesMessageDomain}
     * representing the facesmessages.
     */
    private void addAllComponentMessages(String componentId, List<FacesMessageDomain> facesMessageDomainList) {
        getFacesMessages().put(componentId, facesMessageDomainList);
    }

    /**
     * Convenience method that will return {@code true} if the page has any message regardless of severety.
     * @return a boolean value with the result of the question if the current page has any messages.
     */
    public boolean hasMessages() {
        return !getFacesMessages().isEmpty();
    }

    /**
     * Method that will give you a map with all messages per component.
     * <p>
     * This function is provided if you need to do something with the messages that we have no convenience method for.
     * @return A {@code Map<String, List<FacesMessageDomain>>} containing the componentId as key and a List with
     * facesmessages. Global page messages that are not for a specific component will have the documentId as key.
     */
    public Map<String, List<FacesMessageDomain>> getFacesMessages() {
        return facesMessages;
    }

    /**
     * A convenience method that will check if a specific component has a specific message.
     * @param componentId A String with the relative clientId of the component you want to check.
     * @param messageText The messagetext you want to check for.
     * @return  a boolean value with the result of the question if the supplied component has the supplied message.
     */
    public boolean hasMessage(String componentId, String messageText) {
        List<FacesMessageDomain> compMessages = getFacesMessages().get(componentId);
        if (compMessages != null && !StringUtils.isBlank(messageText)) {
            for (FacesMessageDomain compMessage :
                 compMessages) {
                //return true if and only if the detail OR summary text matches the search String
                return messageText.equalsIgnoreCase(compMessage.getDetail()) ||
                       messageText.equalsIgnoreCase(compMessage.getSummary());
            }
        }
        return false;
    }

}
