package org.ei.drishti.view.activity;

import android.view.View;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.ei.drishti.R;
import org.ei.drishti.adapter.SmartRegisterPaginatedAdapter;
import org.ei.drishti.provider.ECSmartRegisterClientsProvider;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.util.StringUtil;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.Village;
import org.ei.drishti.view.controller.ECSmartRegisterController;
import org.ei.drishti.view.controller.VillageController;
import org.ei.drishti.view.dialog.*;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static org.ei.drishti.AllConstants.FormNames.*;

public class NativeECSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private ECSmartRegisterController controller;
    private VillageController villageController;

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public int getColumnWeightSum() {
        return 1000;
    }

    @Override
    public int[] getColumnWeights() {
        return new int[]{239, 73, 103, 107, 158, 221, 87};
    }

    @Override
    protected int[] getColumnHeaderTextResourceIds() {
        return new int[]{
                R.string.header_name, R.string.header_ec_no, R.string.header_gplsa,
                R.string.header_fp, R.string.header_children, R.string.header_status,
                R.string.header_edit};
    }

    @Override
    protected String getRegisterTitleInShortForm() {
        return getResources().getString(R.string.ec_register_title_in_short);
    }

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(this, clientProvider());
    }

    @Override
    protected SmartRegisterClientsProvider clientProvider() {
        if (clientProvider == null) {
            clientProvider = new ECSmartRegisterClientsProvider(this, this,
                    controller);
        }
        return clientProvider;
    }

    @Override
    protected String getDefaultServiceModeName() {
        return getResources().getString(R.string.couple_selection);
    }

    @Override
    protected FilterOption getDefaultVillageFilterOption() {
        return new AllClientsFilter();
    }

    @Override
    protected SortOption getDefaultSortOption() {
        return new NameSort();
    }

    @Override
    protected ServiceModeOption getDefaultServiceModeOption() {
        return new AllEligibleCoupleServiceMode();
    }

    @Override
    protected FilterOption getDefaultSearchOption() {
        return new ECSearchOption(null);
    }

    @Override
    protected DialogOption[] getSortingOptions() {
        return new DialogOption[]{new NameSort(), new ECNumberSort(),
                new HighPrioritySort(), new BPLSort(),
                new SCSort(), new STSort()};
    }

    @Override
    protected DialogOption[] getFilterOptions() {
        Iterable<Village> villages = villageController.getVillages();
        Iterable<? extends DialogOption> villageNames =
                Iterables.transform(villages, new Function<Village, DialogOption>() {
                    @Override
                    public DialogOption apply(Village village) {
                        return new VillageFilter(StringUtil.humanize(village.name()));
                    }
                });

        return toArray(concat(DEFAULT_FILTER_OPTIONS, villageNames), DialogOption.class);
    }

    @Override
    protected DialogOption[] getServiceModeOptions() {
        return new DialogOption[]{};
    }

    @Override
    protected DialogOption[] getEditOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_register_anc_form), ANC_REGISTRATION, formController),
                new OpenFormOption(getString(R.string.str_register_fp_form), FP_CHANGE, formController),
                new OpenFormOption(getString(R.string.str_register_child_form), CHILD_REGISTRATION_EC, formController),
                new OpenFormOption(getString(R.string.str_edit_ec_form), EC_EDIT, formController),
                new OpenFormOption(getString(R.string.str_close_ec_form), EC_CLOSE, formController),
        };
    }

    @Override
    protected void onInitialization() {
        controller = new ECSmartRegisterController(context.allEligibleCouples(),
                context.allBeneficiaries(), context.listCache(),
                context.ecClientsCache());
        villageController = new VillageController(context.allEligibleCouples(),
                context.listCache(), context.villagesCache());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_info_layout:
                showProfileView((ECClient) view.getTag());
                break;

            default:
                super.onClick(view);
        }
    }

    private void showProfileView(ECClient client) {
        navigationController.startEC(client.entityId());
    }

    @Override
    protected void startRegistration() {
        startFormActivity(EC_REGISTRATION, null, null);

    }
}
