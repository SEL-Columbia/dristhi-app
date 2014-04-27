package org.ei.drishti.view.activity;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.ei.drishti.R;
import org.ei.drishti.view.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(DrishtiTestRunner.class)
@Config(shadows = {ShadowContext.class})
public class NativeECSmartRegisterActivityTest {

    private Activity ecActivity;

    @Before
    public void setup() {
        ecActivity = Robolectric.buildActivity(NativeECSmartRegisterActivity.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    @Test
    public void activityShouldNotBeNull() {
        assertNotNull(ecActivity);
    }

    @Test
    public void shouldContainListView() {
        ListView list = (ListView) ecActivity.findViewById(R.id.list);

        assertNotNull(list);
    }

    @Test
    public void clientsHeaderShouldContain7Columns() {
        ViewGroup header = (ViewGroup) ecActivity.findViewById(R.id.clients_header_layout);
        assertEquals(7, header.getChildCount());
    }

    @Test
    public void clientsHeaderTextsShouldMatch() {
        ViewGroup header = (ViewGroup) ecActivity.findViewById(R.id.clients_header_layout);
        String[] txtHeaders = {"NAME", "EC NO.", "GPLSA", "FP METHOD", "CHILDREN", "STATUS", ""};

        for (int i = 0; i < 7; i++) {
            assertEquals(((TextView) header.getChildAt(i)).getText(), txtHeaders[i]);
        }
    }

    @Test
    public void listViewShouldContainNoItemsIfNoClientPresent() {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getCount();
        assertEquals(0, clientsCount);
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor1Clients.class})
    public void listViewShouldAlwaysContainNavigationViewAtTheBottom() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getCount();
        assertEquals(2, clientsCount);

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(1, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.INVISIBLE || nextButton.getVisibility() == View.GONE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 1", info.getText()); // #TODO: Isn't this wrong :)
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor1Clients.class})
    public void listViewHeaderAndListViewItemWeightsShouldMatch() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);

        LinearLayout listItem = (LinearLayout) list.getAdapter().getView(0, null, null);
        LinearLayout header = (LinearLayout) ecActivity.findViewById(R.id.clients_header_layout);

        assertEquals(2, list.getAdapter().getCount());
        int separatorCount = 6;
        assertEquals(listItem.getChildCount() - separatorCount, header.getChildCount());
        assertEquals((int) listItem.getWeightSum(), (int) header.getWeightSum());

        // TODO: As of now, Children weight varies by little margin.
//        for (int i = 0; i < listItem.getChildCount(); i++) {
//            int listColumnWeight = (int) ((LinearLayout.LayoutParams)listItem.getChildAt(i).getLayoutParams()).weight;
//            int headerColumnWeight = (int) ((LinearLayout.LayoutParams)header.getChildAt(i).getLayoutParams()).weight;
//
//            assertEquals(listColumnWeight, headerColumnWeight);
//        }
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterController.class})
    public void listViewShouldNotHavePagingFor10Items() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getCount();
        assertEquals(11, clientsCount);

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(10, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.INVISIBLE || nextButton.getVisibility() == View.GONE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 1", info.getText());
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor20Clients.class})
    public void listViewShouldNotHavePagingFor20Items() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getCount();

        assertEquals(21, clientsCount);

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(20, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.INVISIBLE || nextButton.getVisibility() == View.GONE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 1", info.getText());
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor21Clients.class})
    public void listViewShouldHavePagingFor21Items() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getAdapter().getCount();

        assertEquals(21, clientsCount);

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(20, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.VISIBLE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 2", info.getText());
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor21Clients.class})
    public void listViewNavigationShouldWork() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getAdapter().getCount();
        assertEquals(21, clientsCount);

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(20, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.VISIBLE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 2", info.getText());

        nextButton.performClick();

        clientsCount = list.getAdapter().getCount();
        assertEquals(2, clientsCount);

        footer = (ViewGroup) list.getAdapter().getView(1, null, null);
        nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.INVISIBLE || nextButton.getVisibility() == View.GONE);
        assertTrue(previousButton.getVisibility() == View.VISIBLE);
        assertEquals("Page 2 of 2", info.getText());

        previousButton.performClick();

        clientsCount = list.getAdapter().getCount();
        assertEquals(21, clientsCount);

        footer = (ViewGroup) list.getAdapter().getView(20, null, null);
        nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.VISIBLE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 2", info.getText());
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor500Clients.class})
    public void listViewShouldShow25PagesFor500Clients() throws InterruptedException {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getAdapter().getCount();
        assertEquals(21, clientsCount);

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(20, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.VISIBLE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 25", info.getText());
    }

    // #TODO: How to test DialogFragments?
    @Test
    public void pressingSortOptionButtonShouldOpenDialogWithSortOptions() {
        ImageButton sortButton = (ImageButton) ecActivity.findViewById(R.id.sort_selection);
        sortButton.performClick();

    }

    @Test
    public void activityShouldBeClosedOnPressingNavBarBackButton() {
        ecActivity.findViewById(R.id.btn_back_to_home).performClick();

        ShadowActivity sa = Robolectric.shadowOf(ecActivity);
        assertTrue(sa.isFinishing());
    }

    @Test
    public void activityShouldBeClosedOnPressingNavBarTitleButton() {
        ecActivity.findViewById(R.id.title_layout).performClick();

        ShadowActivity sa = Robolectric.shadowOf(ecActivity);
        assertTrue(sa.isFinishing());
    }


    //#TODO: how to test Search functionality
    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor5ProperClients.class})
    public void shouldShowOnlySearchFilterSatisfiedResult() {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        int clientsCount = list.getAdapter().getCount();
        assertEquals(6, clientsCount);

        EditText searchText = (EditText) ecActivity.findViewById(R.id.edt_search);
        searchText.setText("a");
        clientsCount = list.getAdapter().getCount();
        assertEquals(4, clientsCount);

        searchText.setText("b");
        clientsCount = list.getAdapter().getCount();
        assertEquals(2, clientsCount);
        ViewGroup listItem  = (ViewGroup) list.getAdapter().getView(0, null, null);
        assertEquals(((TextView)listItem.findViewById(R.id.txt_wife_name)).getText(), "Bhagya");
        assertEquals(((TextView)listItem.findViewById(R.id.txt_husband_name)).getText(), "Ramesh");
        assertEquals(((TextView)listItem.findViewById(R.id.txt_village_name)).getText(), "Hosa agrahara");

        searchText.setText("c");
        clientsCount = list.getAdapter().getCount();
        assertEquals(2, clientsCount);
        listItem = (ViewGroup) list.getAdapter().getView(0, null, null);
        assertEquals(((TextView)listItem.findViewById(R.id.txt_wife_name)).getText(), "Chaitra");
        assertEquals(((TextView)listItem.findViewById(R.id.txt_husband_name)).getText(), "Rams");
        assertEquals(((TextView)listItem.findViewById(R.id.txt_village_name)).getText(), "Somanahalli colony");

        searchText.setText("xyz");
        clientsCount = list.getAdapter().getCount();
        assertEquals(1, clientsCount);

        searchText.setText("adh");
        clientsCount = list.getAdapter().getCount();
        assertEquals(2, clientsCount);
        listItem = (ViewGroup) list.getAdapter().getView(0, null, null);
        assertEquals(((TextView)listItem.findViewById(R.id.txt_wife_name)).getText(), "Adhiti");
        assertEquals(((TextView)listItem.findViewById(R.id.txt_husband_name)).getText(), "Rama");
        assertEquals(((TextView)listItem.findViewById(R.id.txt_village_name)).getText(), "Battiganahalli");
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor5ProperClients.class})
    public void pressingSearchCancelButtonShouldClearSearchTextAndLoadAllClients() {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        assertEquals(6, list.getAdapter().getCount());

        EditText searchText = (EditText) ecActivity.findViewById(R.id.edt_search);
        searchText.setText("adh");

        assertTrue("Adh".equalsIgnoreCase(searchText.getText().toString()));
        assertEquals(2, list.getAdapter().getCount());

        ecActivity.findViewById(R.id.btn_search_cancel).performClick();
        assertEquals("", searchText.getText().toString());
        assertEquals(6, list.getAdapter().getCount());
    }

    @Test
    @Config(shadows = {ShadowECSmartRegisterControllerFor25ProperUnSortedClients.class})
    public void paginationShouldBeCascadeWhenSearchIsInProgress() {
        final ListView list = (ListView) ecActivity.findViewById(R.id.list);
        assertEquals(21, list.getAdapter().getCount());

        ViewGroup footer = (ViewGroup) list.getAdapter().getView(20, null, null);
        Button nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        Button previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        TextView info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.VISIBLE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 2", info.getText());

        EditText searchText = (EditText) ecActivity.findViewById(R.id.edt_search);
        searchText.setText("a");

        assertEquals(4, list.getAdapter().getCount());
        footer = (ViewGroup) list.getAdapter().getView(3, null, null);
        nextButton = (Button) footer.findViewById(R.id.btn_next_page);
        previousButton = (Button) footer.findViewById(R.id.btn_previous_page);
        info = (TextView) footer.findViewById(R.id.txt_page_info);

        assertEquals(3, footer.getChildCount());
        assertTrue(nextButton.getVisibility() == View.INVISIBLE || nextButton.getVisibility() == View.GONE);
        assertTrue(previousButton.getVisibility() == View.INVISIBLE || previousButton.getVisibility() == View.GONE);
        assertEquals("Page 1 of 1", info.getText());
    }
}
