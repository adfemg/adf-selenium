package com.redheap.selenium.component;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfCalendar extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_all_activities_in_view = "all_activities_in_view"; // <div> collection
    private static final String SUBID_all_date_links = "all_date_links"; // <a> collection
    private static final String SUBID_all_more_links_in_view = "all_more_links_in_view"; // <a> links for too many items
    private static final String SUBID_day_view_button = "day_view_button"; // af:button
    private static final String SUBID_list_view_button = "list_view_button"; // af:button
    private static final String SUBID_month_view_button = "month_view_button"; // af:button
    private static final String SUBID_next_button = "next_button"; // af:button
    private static final String SUBID_previous_button = "previous_button"; // af:button
    private static final String SUBID_today_button = "today_button"; // af:button
    private static final String SUBID_week_view_button = "week_view_button"; // af:button

    private static final String JS_GET_ACTIVE_DAY = JS_FIND_COMPONENT + "return comp.getActiveDay();";
    private static final String JS_GET_HOUR_ZOOM = JS_FIND_COMPONENT + "return comp.getHourZoom();";
    private static final String JS_GET_LIST_COUNT = JS_FIND_COMPONENT + "return comp.getListCount();";
    private static final String JS_GET_SHOW_ACTIVITY_TYPES = JS_FIND_COMPONENT + "return comp.getShowActivityTypes();";
    private static final String JS_GET_START_DAY_OF_WEEK = JS_FIND_COMPONENT + "return comp.getStartDayOfWeek();";
    private static final String JS_GET_START_HOUR = JS_FIND_COMPONENT + "return comp.getStartHour();";
    private static final String JS_GET_TIME_SLOTS_PER_HOUR = JS_FIND_COMPONENT + "return comp.getTimeSlotsPerHour();";
    private static final String JS_GET_VIEW = JS_FIND_COMPONENT + "return comp.getView();";

    /* also interesting:
     * peer._getActivityInfo(activity, gridInfo)  (activity is dom elem)
     *   gridinfo can come from _calculateMonthGridInfo(monthgrid,monthheader) both are subids
     * peer._getActivityLinkInList(listRow)
     * peer._getClientActivityId(providerId, activityId, suffix)
     *
     *
     */
    public AdfCalendar(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichCalendar";
    }

    public String getActiveDay() {
        return (String) executeScript(JS_GET_ACTIVE_DAY, getClientId());
    }

    public String getHourZoom() {
        return (String) executeScript(JS_GET_HOUR_ZOOM, getClientId());
    }

    public int getListCount() {
        return ((Number) executeScript(JS_GET_LIST_COUNT, getClientId())).intValue();
    }

    public List<String> getShowActivityTypes() {
        return (List<String>) executeScript(JS_GET_SHOW_ACTIVITY_TYPES, getClientId());
    }

    public String getStartDayOfWeek() {
        return (String) executeScript(JS_GET_START_DAY_OF_WEEK, getClientId());
    }

    public int getStartHour() {
        return ((Number) executeScript(JS_GET_START_HOUR, getClientId())).intValue();
    }

    public int getTimeSlotsPerHour() {
        return ((Number) executeScript(JS_GET_TIME_SLOTS_PER_HOUR, getClientId())).intValue();
    }

    public String getView() {
        return (String) executeScript(JS_GET_VIEW, getClientId());
    }

    public AdfButton findDayViewButton() {
        return findSubIdComponent(SUBID_day_view_button, AdfButton.class);
    }

    public AdfButton findWeekViewButton() {
        return findSubIdComponent(SUBID_week_view_button, AdfButton.class);
    }

    public AdfButton findMonthViewButton() {
        return findSubIdComponent(SUBID_month_view_button, AdfButton.class);
    }

    public AdfButton findListViewButton() {
        return findSubIdComponent(SUBID_list_view_button, AdfButton.class);
    }

    public AdfButton findTodayButton() {
        return findSubIdComponent(SUBID_today_button, AdfButton.class);
    }

    public AdfButton findPreviousButton() {
        return findSubIdComponent(SUBID_previous_button, AdfButton.class);
    }

    public AdfButton findNextButton() {
        return findSubIdComponent(SUBID_next_button, AdfButton.class);
    }

    // raw elements should not be exposed public due to ppr logic that should be part of component
    protected List<WebElement> findAllActivitiesInView() {
        return findSubIdElements(SUBID_all_activities_in_view);
    }

    // raw elements should not be exposed public due to ppr logic that should be part of component
    protected List<WebElement> findAllDateLinks() {
        return findSubIdElements(SUBID_all_date_links);
    }

    // raw elements should not be exposed public due to ppr logic that should be part of component
    protected List<WebElement> findAllMoreLinksInView() {
        return findSubIdElements(SUBID_all_more_links_in_view);
    }

}
