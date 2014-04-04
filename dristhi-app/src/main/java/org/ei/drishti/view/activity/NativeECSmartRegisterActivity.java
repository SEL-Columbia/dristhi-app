package org.ei.drishti.view.activity;

import org.ei.drishti.R;
import org.ei.drishti.adapter.WrappedSmartRegisterPaginatedAdapter;
import org.ei.drishti.provider.SmartECRegisterClientsProvider;
import org.ei.drishti.provider.WrappedSmartRegisterClientsProvider;
import org.ei.drishti.view.contract.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NativeECSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    public static final int COLUMN_COUNT = 7;
    public static final int COLUMN_WEIGHT_SUM = 140;
    public static final int[] COLUMN_WEIGHTS = {30, 10, 20, 20, 20, 30, 10};
    public static final int[] COLUMN_HEADER_RES_IDS = {
            R.string.header_name, R.string.header_ec_no, R.string.header_gplsa,
            R.string.header_fp, R.string.header_children, R.string.header_status,
            R.string.header_edit};

    private WrappedSmartRegisterClientsProvider listItemProvider = null;

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public int getColumnWeightSum() {
        return COLUMN_WEIGHT_SUM;
    }

    @Override
    public int[] getColumnWeights() {
        return COLUMN_WEIGHTS;
    }

    @Override
    protected int[] getColumnHeaderTextResourceIds() {
        return COLUMN_HEADER_RES_IDS;
    }

    @Override
    protected WrappedSmartRegisterPaginatedAdapter adapter() {
        return new WrappedSmartRegisterPaginatedAdapter(this, listItemProvider());
    }

    @Override
    protected WrappedSmartRegisterClientsProvider listItemProvider() {
        if (listItemProvider == null) {
            listItemProvider = new SmartECRegisterClientsProvider(this, Person.getCityPeople());
        }
        return listItemProvider;
    }

    @Override
    protected String getDefaultTypeName() {
        return "All Eligible Couples";
    }

    @Override
    protected String getDefaultVillageFilterOption() {
        return FILTER_BY_ALL;
    }

    @Override
    protected String getDefaultSortOption() {
        return SORT_BY_NAME;
    }

    @Override
    protected String getRegisterTitle() {
        return "EC REGISTER";
    }

    @Override
    protected String[] getSortingOptions() {

        return DEFAULT_SORT_OPTIONS;
    }

    @Override
    protected String[] getFilterOptions() {
        // TODO: check for better way to merge two list.
        List<String> villageNames = listItemProvider.getAllUniqueVillageNames();
        List<String> combinedList = new ArrayList<String>(villageNames.size() + DEFAULT_FILTER_OPTIONS.length);
        combinedList.addAll(Arrays.asList(DEFAULT_FILTER_OPTIONS));
        combinedList.addAll(villageNames);
        String[] toArray = new String[combinedList.size()];
        combinedList.toArray(toArray);
        return toArray;
    }

    @Override
    protected String[] getTypeOptions() {
        return new String[]{};
    }
}
