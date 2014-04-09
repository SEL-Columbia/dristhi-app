package org.ei.drishti.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.DataSetObserver;
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
import org.ei.drishti.view.dialog.*;
import org.joda.time.LocalDate;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class SecuredNativeSmartRegisterActivity extends SecuredActivity
        implements View.OnClickListener {

    private static final String DIALOG_TAG = "dialog";
    private static final int DIALOG_SORT = 1;
    private static final int DIALOG_FILTER = 2;
    private static final int DIALOG_SERVICE_MODE = 3;

    public static final List<? extends DialogOption> DEFAULT_FILTER_OPTIONS =
            asList(new AllClientsFilter(), new OutOfAreaFilter());

    private ListView clientsView;
    private SmartRegisterPaginatedAdapter clientsAdapter;

    private Button serviceModeView;
    private LinearLayout clientsHeaderLayout;
    private TextView appliedVillageFilterView;
    private TextView appliedSortView;

    private Button nextPageView;
    private Button previousPageView;
    private TextView pageInfoView;

    private FilterOption currentVillageFilter;
    private SortOption currentSortOption;
    private FilterOption currentSearchFilter;
    private ServiceModeOption currentServiceModeOption;

    @Override
    protected void onCreation() {
        setContentView(R.layout.smart_register_activity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        onInitialization();
        setupViews();
    }

    @Override
    protected void onResumption() {
    }

    private void setupViews() {
        findViewById(R.id.btn_back_to_home).setOnClickListener(this);

        ViewGroup titleLayout = (ViewGroup) findViewById(R.id.title_layout);
        titleLayout.setOnClickListener(this);

        TextView titleLabelView = (TextView) findViewById(R.id.txt_title_label);
        titleLabelView.setText(getRegisterTitleInShortForm());

        TextView reportMonthStartView = (TextView) findViewById(R.id.btn_report_month_start_date);
        setReportDates(reportMonthStartView);

        clientsHeaderLayout = (LinearLayout) findViewById(R.id.clients_header_layout);
        populateClientListHeaderView();

        clientsView = (ListView) findViewById(R.id.list);
        setupFooterView();
        setupAdapter();

        EditText searchCriteria = (EditText) findViewById(R.id.edt_search);
        searchCriteria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                currentSearchFilter = new ECSearchOption(cs.toString());
                clientsAdapter
                        .refreshList(currentVillageFilter, currentServiceModeOption,
                                currentSearchFilter, currentSortOption);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        View villageFilterView = findViewById(R.id.filter_selection);
        villageFilterView.setOnClickListener(this);
        View sortView = findViewById(R.id.sort_selection);
        sortView.setOnClickListener(this);
        serviceModeView = (Button) findViewById(R.id.section_type_selection);
        serviceModeView.setOnClickListener(this);
        serviceModeView.setText(getDefaultTypeName());

        appliedSortView = (TextView) findViewById(R.id.sorted_by);
        appliedVillageFilterView = (TextView) findViewById(R.id.village);

        updateFooter();
        updateStatusBar();
        updateDefaultOptions();
    }

    protected abstract String getRegisterTitleInShortForm();

    private void setReportDates(TextView titleView) {
        ReportMonth report = new ReportMonth();
        titleView.setText(report.startOfCurrentReportMonth(LocalDate.now()).toString("dd/MM")
                + " - "
                + report.endOfCurrentReportMonth(LocalDate.now()).toString("dd/MM"));
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

        nextPageView.setOnClickListener(this);
        previousPageView.setOnClickListener(this);

        clientsView.addFooterView(footerView);
    }

    public ViewGroup getFooterView() {
        return (ViewGroup) getLayoutInflater().inflate(R.layout.smart_register_pagination, null);
    }

    public void updateFooter() {
        pageInfoView.setText((clientsAdapter.currentPage() + 1) + " of " + (clientsAdapter.pageCount() + 1));
        nextPageView.setEnabled(clientsAdapter.hasNextPage());
        previousPageView.setEnabled(clientsAdapter.hasPreviousPage());
    }

    private void goToPreviousPage() {
        clientsAdapter.previousPage();
        clientsAdapter.notifyDataSetChanged();
    }

    private void goToNextPage() {
        clientsAdapter.nextPage();
        clientsAdapter.notifyDataSetChanged();
    }

    private void updateStatusBar() {
        appliedSortView.setText(getDefaultSortOption().name());
        appliedVillageFilterView.setText(getDefaultVillageFilterOption().name());
    }

    private void populateClientListHeaderView() {
        LinearLayout listHeader = clientsHeaderLayout;
        listHeader.removeAllViewsInLayout();
        listHeader.setWeightSum(getColumnWeightSum());
        int columnCount = getColumnCount();
        int[] weights = getColumnWeights();
        int[] headerTxtResIds = getColumnHeaderTextResourceIds();

        for (int i = 0; i < columnCount; i++) {
            listHeader.addView(getColumnHeaderView(i, weights, headerTxtResIds));
        }
    }

    private View getColumnHeaderView(int i, int[] weights, int[] headerTxtResIds) {
        TextView header = new TextView(this, null, R.style.SmartRegister_Header);
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
        clientsView.setAdapter(clientsAdapter);
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

    protected abstract SmartRegisterClientsProvider clientProvider();

    //#TODO: Try inline attaching of events
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_layout:
            case R.id.btn_back_to_home:
                goBack();
                break;
            case R.id.filter_selection:
                showFragmentDialog(DIALOG_FILTER, getDialogDataSet(DIALOG_FILTER));
                break;
            case R.id.sort_selection:
                showFragmentDialog(DIALOG_SORT, getDialogDataSet(DIALOG_SORT));
                break;
            case R.id.section_type_selection:
                showFragmentDialog(DIALOG_SERVICE_MODE, getDialogDataSet(DIALOG_SERVICE_MODE));
                break;
            case R.id.btn_next_page:
                goToNextPage();
                break;
            case R.id.btn_previous_page:
                goToPreviousPage();
                break;
        }
    }

    protected void onServiceModeSelection(ServiceModeOption serviceModeOption) {
        currentServiceModeOption = serviceModeOption;
        serviceModeView.setText(serviceModeOption.name());
    }

    protected void onSortSelection(SortOption sortBy) {
        currentSortOption = sortBy;
        appliedSortView.setText(sortBy.name());
    }

    protected void onFilterSelection(FilterOption filter) {
        currentVillageFilter = filter;
        appliedVillageFilterView.setText(filter.name());
    }

    private void goBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void showFragmentDialog(int dialogId, DialogOption[] options) {
        if (options.length <= 0) {
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SmartRegisterDialogFragment
                .newInstance(this, dialogId, options)
                .show(ft, DIALOG_TAG);
    }

    public void onDialogOptionSelected(int dialogId, DialogOption option) {
        switch (dialogId) {
            case DIALOG_SORT:
                onSortSelection((SortOption) option);
                break;
            case DIALOG_FILTER:
                onFilterSelection((FilterOption) option);
                break;
            default:
            case DIALOG_SERVICE_MODE:
                onServiceModeSelection((ServiceModeOption) option);
                break;
        }
        clientsAdapter
                .refreshList(currentVillageFilter, currentServiceModeOption,
                        currentSearchFilter, currentSortOption);
    }

    private DialogOption[] getDialogDataSet(int dialogId) {
        switch (dialogId) {
            case DIALOG_SORT:
                return getSortingOptions();

            case DIALOG_FILTER:
                return getFilterOptions();

            default:
            case DIALOG_SERVICE_MODE:
                return getServiceModeOptions();
        }
    }

    protected abstract String getDefaultTypeName();

    protected abstract FilterOption getDefaultVillageFilterOption();

    protected abstract SortOption getDefaultSortOption();

    protected abstract ServiceModeOption getDefaultServiceModeOption();

    protected abstract FilterOption getDefaultSearchOption();

    protected abstract int getColumnCount();

    protected abstract int getColumnWeightSum();

    protected abstract int[] getColumnWeights();

    protected abstract int[] getColumnHeaderTextResourceIds();

    protected abstract String getRegisterTitle();

    protected abstract DialogOption[] getFilterOptions();

    protected abstract DialogOption[] getServiceModeOptions();

    protected abstract DialogOption[] getSortingOptions();

    protected abstract void onInitialization();
}
