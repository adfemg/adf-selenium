package com.redheap.selenium.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import org.openqa.selenium.JavascriptExecutor;


public class FacesMessageDomain {

    private String compId;
    private String title;
    private Long maxType;
    private Long severity;
    private String summary;
    private String detail;
    private List<FacesMessageDomain> facesMessages = new ArrayList<FacesMessageDomain>();

    public void setFacesMessages(List<FacesMessageDomain> facesMessages) {
        this.facesMessages = facesMessages;
    }

    public List<FacesMessageDomain> getFacesMessages() {
        return facesMessages;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompId() {
        return compId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FacesMessageDomain() {
        super();
    }

    public FacesMessageDomain(ArrayList facesMessages) {
        super();
        ArrayList properties = (ArrayList) getValueFromArrayByKey("properties", facesMessages);
        ArrayList<ArrayList> facesmessagesArray =
            (ArrayList<ArrayList>) getValueFromArrayByKey("facesmessages", facesMessages);
        if (facesmessagesArray != null) {
            for (ArrayList subArrayList : facesmessagesArray) {
                this.facesMessages.add(new FacesMessageDomain(subArrayList));
            }
        }
        // These 3 should only be filled for the wrapper message (except maybe title)
        this.compId = (String) getValueFromArrayByKey("compId", properties);
        this.title = (String) getValueFromArrayByKey("title", properties);
        this.maxType = (Long) getValueFromArrayByKey("maxType", properties);
        // These 3 should only be filled for the real message
        this.severity = (Long) (getValueFromArrayByKey("severity", facesMessages));
        this.summary = (String) getValueFromArrayByKey("summary", facesMessages);
        this.detail = (String) getValueFromArrayByKey("detail", facesMessages);
    }

    public String getTitle() {
        return title;
    }

    public void setSeverity(Long severity) {
        this.severity = severity;
    }

    public Long getSeverity() {
        return severity;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setMaxType(Long maxType) {
        this.maxType = maxType;
    }

    public Long getMaxType() {
        return maxType;
    }


    /**
     * Javascript method that gets all messages from the AdfPage and converts it to arraytype.
     * This is needed as Selenium can't handle complex types. These JS functions are tightly coupled
     * with the calling method in {@link com.redheap.selenium.component.AdfRegion#getAllMessages AdfRegion#getAllMessages}
     * <p>
     * This Javascript function is only placed her because we only want to initialize it once
     */
    private static final String JS_FUNCTION_CONVERTPAGEMESSAGESTOARRAY =
        "" + "function convertPageMessagesToArray(pageMessages)\n" + "{\n" + "       var retPageMessages=[];\n" +
        "       var isEmpty = true;\n" + "       \n" + "       for(var prop in pageMessages) {\n" +
        "        if(pageMessages.hasOwnProperty(prop))\n" + "            isEmpty = false;\n" + "    }\n" +
        "       if (!isEmpty) {\n" + "               var retPageProperties=[];\n" + "               \n" +
        "               for (var key in pageMessages){\n" +
        "                       var componentMessages = convertComponentMessagesToArray(pageMessages[key]);\n" +
        "                       retPageMessages.push([['componentId', key],['facesmessages',componentMessages[0]]]);\n" +
        "               }\n" + "       }\n" + "       \n" + "       return retPageMessages;\n" + "};";

    /**
     * Javascript method that gets all messages from the AdfPage and converts it to arraytype.
     * This is needed as Selenium can't handle complex types. These JS functions are tightly coupled
     * with the calling method in {@link com.redheap.selenium.component.uix.UixInput#getMessages UixInput#getMessages}
     * <p>
     * This Javascript function is only placed here because we only want to initialize it once
     */
    private static final String JS_FUNCTION_CONVERTCOMPONENTMESSAGESTOARRAY =
        "" + "function convertComponentMessagesToArray(compMessages)\n" + "{\n" + "       var retComponent=[];\n" +
        "       if (compMessages) {             \n" + "               var retFacesMessages=[];\n" +
        "               var retComponentProperties=[];\n" + "               \n" +
        "               compMessages.forEach(function(facesMessage) {\n" +
        "                       var retFacesMessage = [];\n" +
        "                       if (facesMessage._messageType) {\n" +
        "                               retFacesMessage.push(['messageType',facesMessage.getMessageType()]);\n" +
        "                       }\n" +
        "                       retFacesMessage.push(['severity',facesMessage.getSeverity()]);\n" +
        "                       retFacesMessage.push(['summary',facesMessage.getSummary()]);\n" +
        "                       retFacesMessage.push(['detail',facesMessage.getDetail()]);\n" +
        "                       retFacesMessages.push(retFacesMessage);\n" + "               });\n" +
        "               \n" + "               retComponentProperties.push(['compId',compMessages.compId]);\n" +
        "               retComponentProperties.push(['maxType',compMessages.maxType]);\n" +
        "               retComponentProperties.push(['title',compMessages.title]);\n" + "               \n" +
        "               retComponent.push([['componentId',compMessages.compId], ['facesmessages',retFacesMessages], ['properties',retComponentProperties]]);\n" +
        "       }\n" + "       \n" + "       return retComponent;\n" + "};";
    private static final String JS_GET_PAGE_MESSAGES =
        JS_FUNCTION_CONVERTPAGEMESSAGESTOARRAY + JS_FUNCTION_CONVERTCOMPONENTMESSAGESTOARRAY +
        "var messagesPage = AdfPage.PAGE.getAllMessages();return convertPageMessagesToArray(messagesPage);";
    private static final String JS_GET_COMP_MESSAGES =
        JS_FUNCTION_CONVERTCOMPONENTMESSAGESTOARRAY +
        "var messagesComp = AdfPage.PAGE.getMessages(arguments[0]);return convertComponentMessagesToArray(messagesComp);";

    public static Map<String, FacesMessageDomain> getAllMessages(JavascriptExecutor javascriptExecutor) {
        ArrayList<ArrayList> scriptResult =
            (ArrayList<ArrayList>) javascriptExecutor.executeScript(JS_GET_PAGE_MESSAGES, "");
        Map<String, FacesMessageDomain> facesMessageDomainList = new HashMap<String, FacesMessageDomain>();
        // aanroep naar private convert
        if (scriptResult != null) {
            //loop over all components
            for (ArrayList<ArrayList> compMessageList : scriptResult) {
                //parse each component

                //get the componentID [0]
                //               String compId = (String)compMessageList.get(0).get(1);
                //get the messages    [1]
                ArrayList facesMessages = (ArrayList) (compMessageList.get(1)).get(1);
                facesMessageDomainList.put(findComponentId(compMessageList), new FacesMessageDomain(facesMessages));
            }


        }
        return facesMessageDomainList;
    }

    public static Map getComponentMessages(JavascriptExecutor javascriptExecutor, String clientid) {
        ArrayList<ArrayList> scriptResult =
            (ArrayList<ArrayList>) javascriptExecutor.executeScript(JS_GET_COMP_MESSAGES, clientid);
        Map<String, FacesMessageDomain> facesMessageDomainList = new HashMap<String, FacesMessageDomain>();
        // aanroep naar private convert
        if (scriptResult != null && !scriptResult.isEmpty()) {
            String compId = (String) getValueFromArrayByKey("componentId", scriptResult.get(0));
            facesMessageDomainList.put(compId, new FacesMessageDomain(scriptResult.get(0)));
        }

        return facesMessageDomainList;
    }

    private static Object getValueFromArrayByKey(String key, ArrayList<ArrayList> arrayList) {
        if (arrayList != null) {
            for (ArrayList subArrayList : arrayList) {
                String currentKey = subArrayList.get(0).toString();
                if (currentKey.equals(key)) {
                    return subArrayList.get(1);
                }
            }
        }
        return null;
    }

    private Map convertArrayListToMap(ArrayList arrayList) {
        Map map = ArrayUtils.toMap(arrayList.toArray());
        for (Object object : map.entrySet()) {
            ;
        }
        return null;

    }

    private static String findComponentId(ArrayList<ArrayList> list) {
        if (list != null && list.size() >= 1) {
            return (String) list.get(0).get(1);
        }
        return null;
    }
}
