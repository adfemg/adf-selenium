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

    public Calendar getActiveDayFromDomCalendar() {
        Calendar retval = Calendar.getInstance();
        retval.setTime(getActiveDayFromDom());
        return retval;
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
     * Browse the calendar with previous/next buttons until the day, week or month is shown that contains the given
     * date.
     * @param date date which should be included in the view after this method completes
     * @throws IllegalStateException when calendar is currently in list view
     */
    public void goToDate(final Date date) {
        if (getView() == View.LIST) {
            throw new IllegalStateException("goToDate cannot be used with list view");
        }
        final Date targetViewStart = startOfViewPeriod(date);
        int direction;
        do {
            direction = targetViewStart.compareTo(startOfViewPeriod(this.getActiveDayFromDom()));
            if (direction < 0) {
                // direction < 0 = target date is still in the past.
                this.findPreviousButton().click();
            } else if (direction > 0) {
                // direction > 0 = target date is still in the future.
                this.findNextButton().click();
            }
        } while (direction != 0);
    }

    /**
     * Convert a given date to a date that would be at the beginning of the period showing in the browser with the
     * current view type.
     * For example, for day view this would only strip the time component from the given date, for week view this
     * would return midnight at Monday at the beginning of the week that contains the given date, for month view this
     * would return midnight of the first day of the month containing the given date.
     * @param date
     * @return date
     * @throws IllegalStateException when calendar is currently in list view
     */
    private Date startOfViewPeriod(final Date date) {
        final Calendar retval = Calendar.getInstance();
        retval.setTime(date);
        // strip time components
        retval.set(Calendar.HOUR, 0);
        retval.set(Calendar.MINUTE, 0);
        retval.set(Calendar.SECOND, 0);
        retval.set(Calendar.MILLISECOND, 0);
        switch (getView()) {
        case DAY: // nothing to do, other than time stripping
            break;
        case WEEK:
            retval.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            break;
        case MONTH:
            retval.set(Calendar.DAY_OF_MONTH, 1);
            break;
        case LIST:
            throw new IllegalStateException("startOfViewPeriod cannot be used with list view");
        }
        return retval.getTime();
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
