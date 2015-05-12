package com.redheap.selenium.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
    private static final String JS_GET_ACTIVE_DAY_DOM = JS_FIND_PEER + "return peer._getActiveDayFromDom(comp);";

    private final DateFormat dateformat = new SimpleDateFormat("MM/d/yyyy");

    public static enum View {
        MONTH,
        DAY,
        WEEK,
        LIST
    };

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

    public String getActiveDay() {
        return (String) executeScript(JS_GET_ACTIVE_DAY, getClientId());
    }

    public Date getActiveDayFromDom() {
        String day = (String) executeScript(JS_GET_ACTIVE_DAY_DOM, getClientId());
        try {
            Date date = dateformat.parse(day);
            return date;
        } catch (ParseException e) {
            throw new WebDriverException("invalid active day from dom:" + day, e);
        }
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

    public View getView() {
        String view = (String) executeScript(JS_GET_VIEW, getClientId());
        return View.valueOf(view.toUpperCase());
    }

    public AdfButton findDayViewButton() {
        return findSubIdComponent(SUBID_day_view_button);
    }

    public AdfButton findWeekViewButton() {
        return findSubIdComponent(SUBID_week_view_button);
    }

    public AdfButton findMonthViewButton() {
        return findSubIdComponent(SUBID_month_view_button);
    }

    public AdfButton findListViewButton() {
        return findSubIdComponent(SUBID_list_view_button);
    }

    public AdfButton findTodayButton() {
        return findSubIdComponent(SUBID_today_button);
    }

    public AdfButton findPreviousButton() {
        return findSubIdComponent(SUBID_previous_button);
    }

    public AdfButton findNextButton() {
        return findSubIdComponent(SUBID_next_button);
    }

    public int getActivitiesInViewCount() {
        List<WebElement> activitiesInView = findAllActivitiesInView();
        return activitiesInView == null ? 0 : activitiesInView.size();
    }

    public void clickActivityInView(int index) {
        WebElement element = findAllActivitiesInView().get(index);
        element.click();
        waitForPpr();
    }

    public void hoverActivityInView(int index) {
        WebElement element = findAllActivitiesInView().get(index);
        // we use pause to give javascript timer to detect hover and show popup
        new Actions(getDriver()).moveToElement(element).pause(1000).perform();
        waitForPpr();
    }

    public String getActivityInViewLabel(int index) {
        WebElement div = findAllActivitiesInView().get(index);
        //WebElement span = div.findElement(By.tagName("span"));
        return div.getText();
    }

    public int getDateLinkCount() {
        List<WebElement> links = findAllDateLinks();
        return links == null ? 0 : links.size();
    }

    public void clickDateLink(int index) {
        WebElement element = findAllDateLinks().get(index);
        element.click();
        waitForPpr();
    }

    public int getMoreLinksInViewCount() {
        List<WebElement> moreLinks = findAllMoreLinksInView();
        return moreLinks == null ? 0 : moreLinks.size();
    }

    public void clickMoreLinkInView(int index) {
        WebElement element = findAllMoreLinksInView().get(index);
        element.click();
        waitForPpr();
    }

    public void createDayActivity(int dayOfMonth) {
        View view = getView();
        WebElement target;
        switch (view) {
        case MONTH:
            final Calendar targetDay = Calendar.getInstance();
            targetDay.setTime(getActiveDayFromDom());
            targetDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            final String xpath = "//div[@_adfdycl='" + dateformat.format(targetDay.getTime()) + "']";
            target =
                getElement().findElement(By.xpath(xpath)).findElement(By.cssSelector(".af_calendar_month-grid-cell-header"));
            break;
        default:
            throw new IllegalStateException("unsupported view for creating day activity: " + view);
        }
        target.click();
        waitForPpr();
    }


    /**
     * Set the ADFCalendar to a specific Date
     *
     * @param date
     */
    public void goToDate(Date date) {

        View view = this.getView();

        Date preppedTarget = preppedTarget = prepDateForComparison(date, view);

        Date preppedCalendar = prepDateForComparison(this.getActiveDayFromDom(), view);
        int direction = preppedTarget.compareTo(preppedCalendar);
        // if not the correct day/week/month, navigate
        while (direction != 0) {

            // direction > 0 = target date is still in the future.
            if (direction > 0) {
                this.findNextButton().click();
            }
            // direction < 0 = target date is still in the past.
            else {
                this.findPreviousButton().click();
            }

            preppedCalendar = prepDateForComparison(this.getActiveDayFromDom(), view);
            direction = preppedTarget.compareTo(preppedCalendar);
        }
    }

    /**
     * Preparing the two dates for comparison.
     * Time is set to '00:00:00'. If current calendar view is a month or week,
     * dates are adjusted to one specific day of the month or week.
     *
     * @param date
     * @param view
     * @return date
     */
    private Date prepDateForComparison(Date date, View view) {

        Date preppedDate = date;

        if (view.equals(AdfCalendar.View.DAY) || view.equals(AdfCalendar.View.WEEK) ||
            view.equals(AdfCalendar.View.MONTH)) {

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            if (view.equals(AdfCalendar.View.WEEK)) {
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            } else if (view.equals(AdfCalendar.View.MONTH)) {
                c.set(Calendar.DAY_OF_MONTH, 1);
            }
            preppedDate = c.getTime();
        }

        return preppedDate;
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
