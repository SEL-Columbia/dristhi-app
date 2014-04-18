package org.ei.drishti.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import org.ei.drishti.R;
import org.ei.drishti.adapter.SmartRegisterPaginatedAdapter;
import org.ei.drishti.domain.ReportMonth;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.customControls.CustomFontTextView;
import org.ei.drishti.view.customControls.FontVariant;
import org.ei.drishti.view.dialog.*;
import org.joda.time.LocalDate;

import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;
import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.ei.drishti.AllConstants.SHORT_DATE_FORMAT;

public abstract class SecuredNativeSmartRegisterActivity extends SecuredActivity {

    private static final String DIALOG_TAG = "dialog";

    public static final List<? extends DialogOption> DEFAULT_FILTER_OPTIONS =
            asList(new AllClientsFilter(), new OutOfAreaFilter());

    private ListView clientsView;
    private ProgressBar clientsProgressView;
    private SmartRegisterPaginatedAdapter clientsAdapter;

    private TextView serviceModeView;
    private TextView appliedVillageFilterView;
    private TextView appliedSortView;

    private Button nextPageView;
    private Button previousPageView;
    private TextView pageInfoView;

    private FilterOption currentVillageFilter;
    private SortOption currentSortOption;
    private FilterOption currentSearchFilter;
    private ServiceModeOption currentServiceModeOption;
    private EditText searchView;
    private View searchCancelView;

    private final PaginationHandler paginationHandler = new PaginationHandler();
    private final NavBarActionsHandler navBarActionsHandler = new NavBarActionsHandler();
    private final SearchCancelHandler searchCancelHandler = new SearchCancelHandler();

    @Override
    protected void onCreation() {
        setContentView(R.layout.smart_register_activity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        onInitialization();
        setupViews();
    }

    @Override
    protected void onResumption() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                publishProgress();
                setupAdapter();
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                clientsProgressView.setVisibility(View.VISIBLE);
                clientsView.setVisibility(View.INVISIBLE);
            }

            @Override
            protected void onPostExecute(Void result) {
                clientsView.setAdapter(clientsAdapter);
                updateFooter();
                clientsProgressView.setVisibility(View.GONE);
                clientsView.setVisibility(View.VISIBLE);

            }
        }.executeOnExecutor(THREAD_POOL_EXECUTOR);
    }

    private void setupViews() {
        setupNavBarViews();
        populateClientListHeaderView();

        clientsProgressView = (ProgressBar) findViewById(R.id.client_list_progress);
        clientsView = (ListView) findViewById(R.id.list);

        setupStatusBarViews();
        setupFooterView();
        updateStatusBar();
        updateDefaultOptions();
    }

    private void setupStatusBarViews() {
        appliedSortView = (TextView) findViewById(R.id.sorted_by);
        appliedVillageFilterView = (TextView) findViewById(R.id.village);
    }

    private void setupNavBarViews() {
        findViewById(R.id.btn_back_to_home).setOnClickListener(navBarActionsHandler);

        setupTitleView();

        View villageFilterView = findViewById(R.id.filter_selection);
        villageFilterView.setOnClickListener(navBarActionsHandler);

        View sortView = findViewById(R.id.sort_selection);
        sortView.setOnClickListener(navBarActionsHandler);

        serviceModeView = (TextView) findViewById(R.id.service_mode_selection);
        serviceModeView.setOnClickListener(navBarActionsHandler);
        serviceModeView.setText(getDefaultServiceModeName());

        findViewById(R.id.register_client).setOnClickListener(navBarActionsHandler);

        setupSearchView();
    }

    private void setupTitleView() {
        ViewGroup titleLayout = (ViewGroup) findViewById(R.id.title_layout);
        titleLayout.setOnClickListener(navBarActionsHandler);

        TextView titleLabelView = (TextView) findViewById(R.id.txt_title_label);
        titleLabelView.setText(getRegisterTitleInShortForm());

        TextView reportMonthStartView = (TextView) findViewById(R.id.btn_report_month);
        setReportDates(reportMonthStartView);
    }

    private void setupSearchView() {
        searchView = (EditText) findViewById(R.id.edt_search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                currentSearchFilter = new ECSearchOption(cs.toString());
                clientsAdapter
                        .refreshList(currentVillageFilter, currentServiceModeOption,
                                currentSearchFilter, currentSortOption);

                searchCancelView.setVisibility(isEmpty(cs) ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        searchCancelView = findViewById(R.id.btn_search_cancel);
        searchCancelView.setOnClickListener(searchCancelHandler);
    }

    private void setReportDates(TextView titleView) {
        ReportMonth report = new ReportMonth();
        titleView.setText(report.startOfCurrentReportMonth(LocalDate.now()).toString(SHORT_DATE_FORMAT)
                + " - "
                + report.endOfCurrentReportMonth(LocalDate.now()).toString(SHORT_DATE_FORMAT));
    }

    private void updateDefaultOptions() {
        currentSearchFilter = getDefaultSearchOption();
        currentVillageFilter = getDefaultVillageFilterOption();
        currentServiceModeOption = getDefaultServiceModeOption();
        currentSortOption = getDefaultSortOption();
    }

    private void setupFooterView() {
        ViewGroup footerView = getFooterView();
        nextPageView = (Button) footerView.findViewById(R.id.btn_next_page);
        previousPageView = (Button) footerView.findViewById(R.id.btn_previous_page);
        pageInfoView = (TextView) footerView.findViewById(R.id.txt_page_info);

        nextPageView.setOnClickListener(paginationHandler);
        previousPageView.setOnClickListener(paginationHandler);

        footerView.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.pagination_bar_height)));
        clientsView.addFooterView(footerView);
    }

    public ViewGroup getFooterView() {
        return (ViewGroup) getLayoutInflater().inflate(R.layout.smart_register_pagination, null);
    }

    public void updateFooter() {
        pageInfoView.setText(
                format(getResources().getString(R.string.str_page_info),
                        (clientsAdapter.currentPage() + 1),
                        (clientsAdapter.pageCount())));
        nextPageView.setVisibility(clientsAdapter.hasNextPage() ? View.VISIBLE : View.INVISIBLE);
        previousPageView.setVisibility(clientsAdapter.hasPreviousPage() ? View.VISIBLE : View.INVISIBLE);
    }

    private void updateStatusBar() {
        appliedSortView.setText(getDefaultSortOption().name());
        appliedVillageFilterView.setText(getDefaultVillageFilterOption().name());
    }


    private void populateClientListHeaderView() {
        LinearLayout clientsHeaderLayout = (LinearLayout) findViewById(R.id.clients_header_layout);
        clientsHeaderLayout.removeAllViewsInLayout();
        clientsHeaderLayout.setWeightSum(getColumnWeightSum());
        int columnCount = getColumnCount();
        int[] weights = getColumnWeights();
        int[] headerTxtResIds = getColumnHeaderTextResourceIds();

        for (int i = 0; i < columnCount; i++) {
            clientsHeaderLayout.addView(getColumnHeaderView(i, weights, headerTxtResIds));
        }
    }

    private View getColumnHeaderView(int i, int[] weights, int[] headerTxtResIds) {
        CustomFontTextView header = new CustomFontTextView(this, null, R.style.CustomFontTextViewStyle_Header_Black);
        header.setFontVariant(FontVariant.BLACK);
        header.setTextSize(16);
        header.setTextColor(getResources().getColor(R.color.client_list_header_text_color));
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        weights[i]);

        header.setLayoutParams(lp);
        header.setText(headerTxtResIds[i]);
        return header;
    }

    private void setupAdapter() {
        clientsAdapter = adapter();
        clientsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                updateFooter();
            }
        });
    }

    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(this, clientProvider());
    }

    protected void onServiceModeSelection(ServiceModeOption serviceModeOption) {
        currentServiceModeOption = serviceModeOption;
        serviceModeView.setText(serviceModeOption.name());
        clientsAdapter
                .refreshList(currentVillageFilter, currentServiceModeOption,
                        currentSearchFilter, currentSortOption);
    }

    protected void onSortSelection(SortOption sortBy) {
        currentSortOption = sortBy;
        appliedSortView.setText(sortBy.name());
        clientsAdapter
                .refreshList(currentVillageFilter, currentServiceModeOption,
                        currentSearchFilter, currentSortOption);
    }

    protected void onFilterSelection(FilterOption filter) {
        currentVillageFilter = filter;
        appliedVillageFilterView.setText(filter.name());
        clientsAdapter
                .refreshList(currentVillageFilter, currentServiceModeOption,
                        currentSearchFilter, currentSortOption);
    }

    protected void onEditSelection(EditOption editOption, SmartRegisterClient client) {
        editOption.doEdit(client);
    }

    private void goBack() {
        finish();
    }

    void showFragmentDialog(DialogOptionModel dialogOptionModel) {
        showFragmentDialog(dialogOptionModel, null);
    }

    void showFragmentDialog(DialogOptionModel dialogOptionModel, Object tag) {
        if (dialogOptionModel.getDialogOptions().length <= 0) {
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SmartRegisterDialogFragment
                .newInstance(this, dialogOptionModel, tag)
                .show(ft, DIALOG_TAG);
    }

    protected abstract String getDefaultServiceModeName();

    protected abstract FilterOption getDefaultVillageFilterOption();

    protected abstract SortOption getDefaultSortOption();

    protected abstract ServiceModeOption getDefaultServiceModeOption();

    protected abstract FilterOption getDefaultSearchOption();

    protected abstract int getColumnCount();

    protected abstract int getColumnWeightSum();

    protected abstract int[] getColumnWeights();

    protected abstract int[] getColumnHeaderTextResourceIds();

    protected abstract DialogOption[] getFilterOptions();

    protected abstract DialogOption[] getServiceModeOptions();

    protected abstract DialogOption[] getSortingOptions();

    protected abstract DialogOption[] getEditOptions();

    protected abstract void onInitialization();

    protected abstract void startRegistration();

    protected abstract SmartRegisterClientsProvider clientProvider();

    protected abstract String getRegisterTitleInShortForm();

    private class FilterDialogOptionModel implements DialogOptionModel {
        @Override
        public DialogOption[] getDialogOptions() {
            return getFilterOptions();
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            onFilterSelection((FilterOption) option);
        }
    }

    private class SortDialogOptionModel implements DialogOptionModel {
        @Override
        public DialogOption[] getDialogOptions() {
            return getSortingOptions();
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            onSortSelection((SortOption) option);
        }
    }

    private class ServiceModeDialogOptionModel implements DialogOptionModel {
        @Override
        public DialogOption[] getDialogOptions() {
            return getServiceModeOptions();
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            onServiceModeSelection((ServiceModeOption) option);
        }
    }

    private class PaginationHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_next_page:
                    goToNextPage();
                    break;
                case R.id.btn_previous_page:
                    goToPreviousPage();
                    break;
            }
        }

        private void goToPreviousPage() {
            clientsAdapter.previousPage();
            clientsAdapter.notifyDataSetChanged();
        }

        private void goToNextPage() {
            clientsAdapter.nextPage();
            clientsAdapter.notifyDataSetChanged();
        }
    }

    public class NavBarActionsHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.title_layout:
                case R.id.btn_back_to_home:
                    goBack();
                    break;
                case R.id.register_client:
                    startRegistration();
                    break;
                case R.id.filter_selection:
                    showFragmentDialog(new FilterDialogOptionModel());
                    break;
                case R.id.sort_selection:
                    showFragmentDialog(new SortDialogOptionModel());
                    break;
                case R.id.service_mode_selection:
                    showFragmentDialog(new ServiceModeDialogOptionModel());
                    break;
            }
        }
    }

    public class SearchCancelHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            clearSearchText();
        }

        private void clearSearchText() {
            searchView.setText("");
        }
    }
}
