package org.ei.telemedicine.view.activity;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static java.util.Arrays.asList;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_CLOSE;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_POSTPARTUM_FAMILY_PLANNING;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_REGISTRATION_OA;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_VISIT;

import java.util.List;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.ei.telemedicine.adapter.SmartRegisterPaginatedAdapter;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.provider.PNCSmartRegisterClientsProvider;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.PNCSmartRegisterController;
import org.ei.telemedicine.view.controller.VillageController;
import org.ei.telemedicine.view.dialog.AllClientsFilter;
import org.ei.telemedicine.view.dialog.BPLSort;
import org.ei.telemedicine.view.dialog.DateOfDeliverySort;
import org.ei.telemedicine.view.dialog.DialogOption;
import org.ei.telemedicine.view.dialog.DialogOptionMapper;
import org.ei.telemedicine.view.dialog.DialogOptionModel;
import org.ei.telemedicine.view.dialog.EditOption;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.HighRiskSort;
import org.ei.telemedicine.view.dialog.NameSort;
import org.ei.telemedicine.view.dialog.OpenFormOption;
import org.ei.telemedicine.view.dialog.OutOfAreaFilter;
import org.ei.telemedicine.view.dialog.PNCOverviewServiceMode;
import org.ei.telemedicine.view.dialog.PNCVisitsServiceMode;
import org.ei.telemedicine.view.dialog.SCSort;
import org.ei.telemedicine.view.dialog.STSort;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;

import android.view.View;

public class NativePNCSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private PNCSmartRegisterController controller;
    private VillageController villageController;
    private DialogOptionMapper dialogOptionMapper;
    public static final List<? extends DialogOption> DEFAULT_PNC_FILTER_OPTIONS =
            asList(new OutOfAreaFilter());

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
                return new PNCOverviewServiceMode(clientsProvider());
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
                return getResources().getString(R.string.pnc_register_title_in_short);
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
                return toArray(concat(DEFAULT_FILTER_OPTIONS, DEFAULT_PNC_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {

                return new DialogOption[]{
                        new PNCOverviewServiceMode(clientsProvider()),
                        new PNCVisitsServiceMode(clientsProvider()),
                };
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(), new DateOfDeliverySort(),
                        new HighRiskSort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_pnc_search_hint);
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new PNCSmartRegisterClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    private DialogOption[] getUpdateOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_pnc_visit_form), PNC_VISIT, formController),
                new OpenFormOption(getString(R.string.str_anc_plan_of_care), AllConstants.VIEW_PNC_PLAN_OF_CARE, formController, NativePNCSmartRegisterActivity.this),
                new OpenFormOption(getString(R.string.str_pnc_postpartum_family_planning_form), PNC_POSTPARTUM_FAMILY_PLANNING, formController),
                new OpenFormOption(getString(R.string.str_pnc_close_form), PNC_CLOSE, formController),
        };
    }

    @Override
    protected void onInitialization() {
        controller = new PNCSmartRegisterController(context.serviceProvidedService(), context.alertService(), context.allEligibleCouples(),
                context.allBeneficiaries(),
                context.listCache(), context.pncClientsCache());
        villageController = new VillageController(context.allEligibleCouples(),
                context.listCache(), context.villagesCache());
        dialogOptionMapper = new DialogOptionMapper();
        clientsProvider().onServiceModeSelected(new PNCOverviewServiceMode(clientsProvider()));
    }

    @Override
    public void setupViews() {
        super.setupViews();
    }

    @Override
    protected void startRegistration() {
        FieldOverrides fieldOverrides = new FieldOverrides(context.anmLocationController().getLocationJSON());
        startFormActivity(PNC_REGISTRATION_OA, null, fieldOverrides.getJSONString());
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout:
                    showProfileView((SmartRegisterClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new UpdateDialogOptionModel(), view.getTag());
                    break;
            }
        }

        private void showProfileView(SmartRegisterClient client) {
            navigationController.startPNC(client.entityId());
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
