package org.ei.drishti.view.activity;

import android.view.View;
import org.ei.drishti.R;
import org.ei.drishti.adapter.SmartRegisterPaginatedAdapter;
import org.ei.drishti.domain.form.FieldOverrides;
import org.ei.drishti.provider.FPSmartRegisterClientsProvider;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.FPClient;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.controller.FPSmartRegisterController;
import org.ei.drishti.view.controller.VillageController;
import org.ei.drishti.view.dialog.*;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static org.ei.drishti.AllConstants.FormNames.*;

public class NativeFPSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private FPSmartRegisterController controller;
    private VillageController villageController;
    private DialogOptionMapper dialogOptionMapper;

    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new FPAllMethodsServiceMode(clientsProvider());
            }

            @Override
            public FilterOption villageFilter() {
                return new AllClientsFilter();
            }

            @Override
            public SortOption sortOption() {
                return new NameSort();
            }

            @Override
            public String nameInShortFormForTitle() {
                return getResources().getString(R.string.fp_register_title_in_short);
            }
        };
    }

    @Override
    protected NavBarOptionsProvider getNavBarOptionsProvider() {
        return new NavBarOptionsProvider() {

            @Override
            public DialogOption[] filterOptions() {
                Iterable<? extends DialogOption> villageFilterOptions =
                        dialogOptionMapper.mapToVillageFilterOptions(villageController.getVillages());
                return toArray(concat(DEFAULT_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(), new ECNumberSort(),
                        new HighPrioritySort(), new BPLSort(),
                        new SCSort(), new STSort()};
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new FPSmartRegisterClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    private DialogOption[] getUpdateOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_fp_change_form), FP_CHANGE, formController),
                new OpenFormOption(getString(R.string.str_record_ecp_form), RECORD_ECPS, formController),
        };
    }

    @Override
    protected void onInitialization() {
        controller = new FPSmartRegisterController(context.allEligibleCouples(),
                context.allBeneficiaries(), context.alertService(),
                context.listCache(), context.fpClientsCache());
        villageController = new VillageController(context.allEligibleCouples(),
                context.listCache(), context.villagesCache());
        dialogOptionMapper = new DialogOptionMapper();
    }

    @Override
    public void setupViews() {
        super.setupViews();

        setServiceModeViewDrawableRight(null);
    }

    @Override
    protected void startRegistration() {
        FieldOverrides fieldOverrides = new FieldOverrides(context.anmLocationController().getLocationJSON());
        startFormActivity(EC_REGISTRATION, null, fieldOverrides.getJSONString());
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout:
                    showProfileView((FPClient) view.getTag());
                    break;
                case R.id.btn_fp_method_update:
                    showFragmentDialog(new UpdateDialogOptionModel(), view.getTag());
                    break;
            }
        }

        private void showProfileView(FPClient client) {
            navigationController.startEC(client.entityId());
        }
    }

    private class UpdateDialogOptionModel implements DialogOptionModel {
        @Override
        public DialogOption[] getDialogOptions() {
            return getUpdateOptions();
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            onEditSelection((EditOption) option, (SmartRegisterClient) tag);
        }
    }
}
