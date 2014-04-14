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
import org.ei.drishti.view.BackgroundAction;
import org.ei.drishti.view.LockingBackgroundTask;
import org.ei.drishti.view.ProgressIndicator;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.customControls.CustomFontTextView;
import org.ei.drishti.view.customControls.FontVariant;
import org.ei.drishti.view.dialog.*;
import org.joda.time.LocalDate;

import java.text.MessageFormat;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public abstract class SecuredNativeSmartRegisterActivity extends SecuredActivity
        implements View.OnClickListener {

    private static final String DIALOG_TAG = "dialog";
    private static final int DIALOG_SORT = 1;
    private static final int DIALOG_FILTER = 2;
    private static final int DIALOG_SERVICE_MODE = 3;
    private static final int DIALOG_EDIT = 4;

    public static final List<? extends DialogOption> DEFAULT_FILTER_OPTIONS =
            asList(new AllClientsFilter(), new OutOfAreaFilter());

    private ListView clientsView;
    private ProgressBar clientsProgressView;
    private SmartRegisterPaginatedAdapter clientsAdapter;

    private TextView serviceModeView;
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
    private EditText searchView;
    private View searchCancelView;

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

        TextView reportMonthStartView = (TextView) findViewById(R.id.btn_report_month);
        setReportDates(reportMonthStartView);

        clientsHeaderLayout = (LinearLayout) findViewById(R.id.clients_header_layout);
        populateClientListHeaderView();

        clientsProgressView = (ProgressBar) findViewById(R.id.client_list_progress);
        clientsView = (ListView) findViewById(R.id.list);

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
        searchCancelView.setOnClickListener(this);

        View villageFilterView = findViewById(R.id.filter_selection);
        villageFilterView.setOnClickListener(this);
        View sortView = findViewById(R.id.sort_selection);
        sortView.setOnClickListener(this);
        serviceModeView = (TextView) findViewById(R.id.service_mode_selection);
        serviceModeView.setOnClickListener(this);
        serviceModeView.setText(getDefaultServiceModeName());

        appliedSortView = (TextView) findViewById(R.id.sorted_by);
        appliedVillageFilterView = (TextView) findViewById(R.id.village);

        findViewById(R.id.register_client).setOnClickListener(this);

        setupFooterView();
        updateStatusBar();
        updateDefaultOptions();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        new LockingBackgroundTask(new ProgressIndicator() {
            @Override
            public void setVisible() {
                clientsProgressView.setVisibility(View.VISIBLE);
                clientsView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void setInvisible() {
                clientsProgressView.setVisibility(View.GONE);
                clientsView.setVisibility(View.VISIBLE);
            }
        }).doActionInBackground(new BackgroundAction<Object>() {
            @Override
            public Object actionToDoInBackgroundThread() {
                setupAdapter();
                return null;
            }

            @Override
            public void postExecuteInUIThread(Object result) {
                clientsView.setAdapter(clientsAdapter);
                clientsView.setVisibility(View.VISIBLE);
                updateFooter();
            }
        });
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

        footerView.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.pagination_bar_height)));
        clientsView.addFooterView(footerView);
    }

    public ViewGroup getFooterView() {
        return (ViewGroup) getLayoutInflater().inflate(R.layout.smart_register_pagination, null);
    }

    public void updateFooter() {
        pageInfoView.setText(MessageFormat.
                format(getResources().getString(R.string.str_page_info),
                        (clientsAdapter.currentPage() + 1),
                        (clientsAdapter.pageCount())));
        nextPageView.setVisibility(clientsAdapter.hasNextPage() ? View.VISIBLE : View.INVISIBLE);
        previousPageView.setVisibility(clientsAdapter.hasPreviousPage() ? View.VISIBLE : View.INVISIBLE);
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

    protected abstract SmartRegisterClientsProvider clientProvider();

    //#TODO: Try inline attaching of events
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
                showFragmentDialog(DIALOG_FILTER, getDialogDataSet(DIALOG_FILTER));
                break;
            case R.id.sort_selection:
                showFragmentDialog(DIALOG_SORT, getDialogDataSet(DIALOG_SORT));
                break;
            case R.id.service_mode_selection:
                showFragmentDialog(DIALOG_SERVICE_MODE, getDialogDataSet(DIALOG_SERVICE_MODE));
                break;
            case R.id.btn_edit:
                showFragmentDialog(DIALOG_EDIT, getDialogDataSet(DIALOG_EDIT), view.getTag());
                break;
            case R.id.btn_next_page:
                goToNextPage();
                break;
            case R.id.btn_previous_page:
                goToPreviousPage();
                break;
            case R.id.btn_search_cancel:
                clearSearch();
                break;

        }
    }


    private void clearSearch() {
        searchView.setText("");
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

    protected void onEditSelection(EditOption editOption, SmartRegisterClient client) {
        editOption.doEdit(client);
    }

    private void goBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void showFragmentDialog(int dialogId, DialogOption[] options) {
        showFragmentDialog(dialogId, options, null);
    }

    void showFragmentDialog(int dialogId, DialogOption[] options, Object tag) {
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
                .newInstance(this, dialogId, options, tag)
                .show(ft, DIALOG_TAG);
    }

    public void onDialogOptionSelected(int dialogId, DialogOption option, Object tag) {
        switch (dialogId) {
            case DIALOG_SORT:
                onSortSelection((SortOption) option);
                break;

            case DIALOG_FILTER:
                onFilterSelection((FilterOption) option);
                break;

            case DIALOG_SERVICE_MODE:
                onServiceModeSelection((ServiceModeOption) option);
                break;

            case DIALOG_EDIT:
                onEditSelection((EditOption) option, (SmartRegisterClient) tag);
                return;
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

            case DIALOG_SERVICE_MODE:
                return getServiceModeOptions();

            default:
            case DIALOG_EDIT:
                return getEditOptions();
        }
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
}
