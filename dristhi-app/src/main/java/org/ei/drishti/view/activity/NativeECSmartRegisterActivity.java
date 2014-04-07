package org.ei.drishti.view.activity;

import org.ei.drishti.R;
import org.ei.drishti.adapter.SmartRegisterPaginatedAdapter;
import org.ei.drishti.provider.ECSmartRegisterClientsProvider;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.controller.ECSmartRegisterController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NativeECSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private ECSmartRegisterController controller;

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public int getColumnWeightSum() {
        return 140;
    }

    @Override
    public int[] getColumnWeights() {
        return new int[]{30, 10, 20, 20, 20, 30, 10};
    }

    @Override
    protected int[] getColumnHeaderTextResourceIds() {
        return new int[]{
                R.string.header_name, R.string.header_ec_no, R.string.header_gplsa,
                R.string.header_fp, R.string.header_children, R.string.header_status,
                R.string.header_edit};
    }

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(this, clientProvider());
    }

    @Override
    protected SmartRegisterClientsProvider clientProvider() {
        if (clientProvider == null) {
            clientProvider = new ECSmartRegisterClientsProvider(this, controller.getClients());
        }
        return clientProvider;
    }

    @Override
    protected String getDefaultTypeName() {
        return getResources().getString(R.string.couple_selection);
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
        return getResources().getString(R.string.ec_register_title);
    }

    @Override
    protected String[] getSortingOptions() {
        return new String[]{SORT_BY_NAME, SORT_BY_AGE, SORT_BY_EC_NO};
    }

    @Override
    protected String[] getFilterOptions() {
        // TODO: check for better way to merge two list.
        List<String> villageNames = clientProvider.getAllUniqueVillageNames();
        List<String> combinedList = new ArrayList<String>(villageNames.size() + DEFAULT_FILTER_OPTIONS.length);
        combinedList.addAll(Arrays.asList(DEFAULT_FILTER_OPTIONS));
        combinedList.addAll(villageNames);
        String[] toArray = new String[combinedList.size()];
        combinedList.toArray(toArray);
        return toArray;
    }

    @Override
    protected String[] getServiceModeOptions() {
        return new String[]{};
    }

    @Override
    protected void onInitialization() {
        controller = new ECSmartRegisterController(context.allEligibleCouples(),
                context.allBeneficiaries(), context.listCache(),
                context.ecClientsCache());
    }
}
