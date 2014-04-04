package org.ei.drishti.view.activity;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import org.ei.drishti.R;
import org.ei.drishti.adapter.WrappedSmartRegisterPaginatedAdapter;
import org.ei.drishti.provider.WrappedSmartRegisterClientsProvider;

public abstract class SecuredNativeSmartRegisterActivity extends Activity implements View.OnClickListener {

    public static final String SORT_BY_NAME = "Name";
    public static final String SORT_BY_AGE = "Age";
    public static final String SORT_BY_EC_NO = "EC No";
    public static final String FILTER_BY_ALL = "All";
    public static final String FILTER_BY_OA = "O/A";
    public static final String FILTER_BY_LP = "L/P";

    public static final String[] DEFAULT_SORT_OPTIONS = {SORT_BY_NAME, SORT_BY_AGE, SORT_BY_EC_NO};
    public static final String[] DEFAULT_FILTER_OPTIONS = {FILTER_BY_ALL, FILTER_BY_OA, FILTER_BY_LP};

    private ListView mPeopleView;
    private WrappedSmartRegisterPaginatedAdapter mPeopleAdapter;

    private EditText mEdtSearch;
    private View mFilterSelectionView;
    private View mSortSelectionView;
    private Button mTypeSelectionView;
    private LinearLayout mHeaderLayout;
    private int mCurrentTypeSelectionId = 0;

    private ListPopupWindow mFilterSelectionPopupWindow = null;
    private ListPopupWindow mSortSelectionPopupWindow = null;
    private ListPopupWindow mTypeSelectionPopupWindow = null;

    private TextView mTxtVillageView;
    private TextView mTxtSortedByView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.smart_register_activity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setupViews();
    }

    private void setupViews() {
        findViewById(R.id.btn_back_to_home).setOnClickListener(this);

        TextView title = (TextView) findViewById(R.id.btn_title);
        title.setOnClickListener(this);
        title.setText(getRegisterTitle());

        mHeaderLayout = (LinearLayout) findViewById(R.id.list_header_holder);
        layoutHeaderView();

        mPeopleView = (ListView) findViewById(R.id.list);

        setupAdapter();

        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                mPeopleAdapter.getFilter().filter(cs);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mPeopleAdapter.updateFooter();

        mFilterSelectionView = findViewById(R.id.filter_selection);
        mFilterSelectionView.setOnClickListener(this);
        mSortSelectionView = findViewById(R.id.sort_selection);
        mSortSelectionView.setOnClickListener(this);
        mTypeSelectionView = (Button) findViewById(R.id.section_type_selection);
        mTypeSelectionView.setOnClickListener(this);
        mTypeSelectionView.setText(getDefaultTypeName());

        mTxtSortedByView = (TextView) findViewById(R.id.sorted_by);
        mTxtVillageView = (TextView) findViewById(R.id.village);

        updateStatusBar();

    }

    private void updateStatusBar() {
        mTxtSortedByView.setText(getDefaultSortOption());
        mTxtVillageView.setText(getDefaultVillageFilterOption());
    }

    private void layoutHeaderView() {
        LinearLayout listHeader = mHeaderLayout;
        listHeader.removeAllViewsInLayout();

        listHeader.setWeightSum(getColumnWeightSum());
        int columnCount = getColumnCount();
        int[] weights = getColumnWeights();
        int[] headerTxtResIds = getColumnHeaderTextResourceIds();

        for (int i = 0; i < columnCount; i++) {
            listHeader.addView(getColumnHeaderView(i, weights, headerTxtResIds));
            listHeader.addView(getSeperatorView());
        }
    }

    private View getSeperatorView() {
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(getDividerLayoutParams());
        iv.setImageResource(R.color.list_divider_color);
        return iv;
    }

    private LinearLayout.LayoutParams getDividerLayoutParams() {
        return new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.list_item_divider_height),
                ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private View getColumnHeaderView(int i, int[] weights, int[] headerTxtResIds) {
        TextView tv = new TextView(this, null, R.style.TextAppearance_Header);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        weights[i]);

        tv.setLayoutParams(lp);
        tv.setText(headerTxtResIds[i]);
        tv.setPadding(10, 0, 0, 0);


        return tv;
    }

    private void setupAdapter() {
        mPeopleAdapter = adapter();
        mPeopleView.setAdapter(mPeopleAdapter);
        mPeopleAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                mPeopleAdapter.updateFooter();
            }
        });
    }

    protected WrappedSmartRegisterPaginatedAdapter adapter() {
        return new WrappedSmartRegisterPaginatedAdapter(this, listItemProvider());
    }

    protected abstract WrappedSmartRegisterClientsProvider listItemProvider();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title:
            case R.id.btn_back_to_home:
                goBack();
                break;
            case R.id.filter_selection:
                showFilterSelectionView(mFilterSelectionView);
                break;
            case R.id.sort_selection:
                showSortSelectionView(mSortSelectionView);
                break;
            case R.id.section_type_selection:
                showTypeSelectionView(mTypeSelectionView);
                break;
        }
    }

    private void showTypeSelectionView(View typeSelectionView) {
        typeSelectionPopupWindow(typeSelectionView).show();
    }

    protected void onTypeSelection(String type) {
        mPeopleAdapter.showSection(type);
        mPeopleAdapter.notifyDataSetChanged();
        mTypeSelectionView.setText(type);
    }

    private void showSortSelectionView(View sortSelectionView) {
        sortSelectionPopupWindow(sortSelectionView).show();
    }

    protected void onSortSelection(String sortBy) {
        mPeopleAdapter.sortBy(sortBy);
        mPeopleAdapter.notifyDataSetChanged();
        mTxtSortedByView.setText(sortBy);
    }

    private void showFilterSelectionView(View filterSelectionView) {
        filterSelectionPopupWindow(filterSelectionView).show();
    }

    protected void onFilterSelection(String filter) {
        mPeopleAdapter.getFilter().filter(filter);
        mPeopleAdapter.notifyDataSetChanged();
        mTxtVillageView.setText(filter);
    }

    private ListPopupWindow filterSelectionPopupWindow(View anchorView) {
        if (mFilterSelectionPopupWindow == null) {
            mFilterSelectionPopupWindow = createFilterSelectionPopupWindow(anchorView);
        }
        return mFilterSelectionPopupWindow;
    }

    private ListPopupWindow sortSelectionPopupWindow(View anchorView) {
        if (mSortSelectionPopupWindow == null) {
            mSortSelectionPopupWindow = createSortSelectionPopupWindow(anchorView);
        }
        return mSortSelectionPopupWindow;
    }

    private ListPopupWindow typeSelectionPopupWindow(View anchorView) {
        if (mTypeSelectionPopupWindow == null) {
            mTypeSelectionPopupWindow = createTypeSelectionPopupWindow(anchorView);
        }
        return mTypeSelectionPopupWindow;
    }

    private ListPopupWindow createFilterSelectionPopupWindow(View anchorView) {
        final ListPopupWindow pw = new ListPopupWindow(this);
        pw.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_checked, getFilterOptions()));
        pw.setAnchorView(anchorView);
        pw.setContentWidth(250);
        pw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pw.dismiss();
                onFilterSelection(((TextView) view).getText().toString());
            }
        });
        return pw;
    }

    private ListPopupWindow createSortSelectionPopupWindow(View anchorView) {
        final ListPopupWindow pw = new ListPopupWindow(this);
        pw.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_checked, getSortingOptions()));
        pw.setAnchorView(anchorView);
        pw.setContentWidth(150);
        pw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pw.dismiss();
                onSortSelection(((TextView) view).getText().toString());

            }
        });
        return pw;
    }

    private ListPopupWindow createTypeSelectionPopupWindow(View anchorView) {
        final ListPopupWindow pw = new ListPopupWindow(this);
        pw.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_checked, getTypeOptions()));
        pw.setAnchorView(mTypeSelectionView);
        pw.setContentWidth(150);

        pw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                pw.dismiss();
                onTypeSelection(((TextView) view).getText().toString());
                layoutHeaderView();
            }
        });

        return pw;
    }

    private void goBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isAnyPopupIsShowing()) {
            closeAllPopups();
        } else {
            super.onBackPressed();
        }
    }

    private void closeAllPopups() {
        closePopup(mFilterSelectionPopupWindow);
        closePopup(mSortSelectionPopupWindow);
        closePopup(mTypeSelectionPopupWindow);
    }

    private boolean isAnyPopupIsShowing() {
        return isPopupShowing(mFilterSelectionPopupWindow)
                || isPopupShowing(mSortSelectionPopupWindow)
                || isPopupShowing(mTypeSelectionPopupWindow);
    }

    private boolean isPopupShowing(ListPopupWindow popup) {
        return (popup != null && popup.isShowing());
    }

    private void closePopup(ListPopupWindow popup) {
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        }
    }

    protected abstract String getDefaultTypeName();

    protected abstract String getDefaultVillageFilterOption();

    protected abstract String getDefaultSortOption();

    public abstract int getColumnCount();

    public abstract int getColumnWeightSum();

    public abstract int[] getColumnWeights();

    protected abstract int[] getColumnHeaderTextResourceIds();

    protected abstract String getRegisterTitle();

    protected abstract String[] getFilterOptions();

    protected abstract String[] getTypeOptions();

    protected abstract String[] getSortingOptions();


}
