package org.ei.telemedicine.view.activity;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static java.util.Arrays.asList;
import static org.ei.telemedicine.AllConstants.FormNames.*;

import java.util.List;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.ei.telemedicine.adapter.SmartRegisterPaginatedAdapter;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.provider.ANCSmartRegisterClientsProvider;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.ANCSmartRegisterController;
import org.ei.telemedicine.view.controller.VillageController;
import org.ei.telemedicine.view.dialog.ANCOverviewServiceMode;
import org.ei.telemedicine.view.dialog.ANCVisitsServiceMode;
import org.ei.telemedicine.view.dialog.AllClientsFilter;
import org.ei.telemedicine.view.dialog.DeliveryPlanServiceMode;
import org.ei.telemedicine.view.dialog.DialogOption;
import org.ei.telemedicine.view.dialog.DialogOptionMapper;
import org.ei.telemedicine.view.dialog.DialogOptionModel;
import org.ei.telemedicine.view.dialog.EditOption;
import org.ei.telemedicine.view.dialog.EstimatedDateOfDeliverySort;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.HRPSort;
import org.ei.telemedicine.view.dialog.HbIFAServiceMode;
import org.ei.telemedicine.view.dialog.NameSort;
import org.ei.telemedicine.view.dialog.OpenFormOption;
import org.ei.telemedicine.view.dialog.OutOfAreaFilter;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;
import org.ei.telemedicine.view.dialog.TTServiceMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;

public class NativeANCSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private ANCSmartRegisterController controller;
    private VillageController villageController;
    private DialogOptionMapper dialogOptionMapper;
    public static final List<? extends DialogOption> DEFAULT_ANC_FILTER_OPTIONS =
            asList(new OutOfAreaFilter());

    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new ANCSmartRegisterClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new ANCOverviewServiceMode(clientsProvider());
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
                return getResources().getString(R.string.anc_label);
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
                return toArray(concat(DEFAULT_FILTER_OPTIONS, DEFAULT_ANC_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{
                        new ANCOverviewServiceMode(clientsProvider()),
                        new ANCVisitsServiceMode(clientsProvider()),
                        new HbIFAServiceMode(clientsProvider()),
                        new TTServiceMode(clientsProvider()),
                        new DeliveryPlanServiceMode(clientsProvider())
                };
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(), new EstimatedDateOfDeliverySort(),
                        new HRPSort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_anc_search_hint);
            }
        };
    }

    private DialogOption[] getEditOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_register_anc_visit_form), ANC_VISIT, formController),
//                new OpenFormOption("ANC Reg Edit", "anc_reg_edit", formController),
                new OpenFormOption("ANC Edit", ANC_EDIT, formController),
                new OpenFormOption(getString(R.string.str_register_hb_test_form), HB_TEST, formController),
                new OpenFormOption(getString(R.string.str_register_ifa_form), IFA, formController),
                new OpenFormOption(getString(R.string.str_register_tt_form), TT, formController),
                new OpenFormOption(getString(R.string.str_register_delivery_plan_form), DELIVERY_PLAN, formController),
                new OpenFormOption(getString(R.string.str_anc_plan_of_care), AllConstants.VIEW_PLAN_OF_CARE, formController, NativeANCSmartRegisterActivity.this),
                new OpenFormOption(getString(R.string.str_register_pnc_registration_form), DELIVERY_OUTCOME, formController),
                new OpenFormOption(getString(R.string.str_register_anc_investigations_form), ANC_INVESTIGATIONS, formController),
                new OpenFormOption(getString(R.string.str_register_anc_close_form), ANC_CLOSE, formController)
        };
    }

    @Override
    protected void onInitialization() {
        controller = new ANCSmartRegisterController(
                context.serviceProvidedService(),
                context.alertService(),
                context.allBeneficiaries(),
                context.listCache(),
                context.ancClientsCache());

        villageController = new VillageController(
                context.allEligibleCouples(),
                context.listCache(),
                context.villagesCache());

        dialogOptionMapper = new DialogOptionMapper();

        clientsProvider().onServiceModeSelected(new ANCOverviewServiceMode(clientsProvider()));
    }

    @Override
    protected void startRegistration(String village) throws JSONException {
        String locationJSON = context.anmLocationController().getFormInfoJSON();
        JSONObject jsonFormInfo = new JSONObject(locationJSON);
        jsonFormInfo.put("village", village.trim().replace(" ", "%20"));
        String customFields = context.allSettings().fetchFieldLabels("ANCRegistration");
        if (customFields != null && !customFields.equals("")) {
            JSONArray customFieldsArray = new JSONArray(customFields);
            for (int i = 0; i < customFieldsArray.length(); i++) {
                jsonFormInfo.put("field" + (i + 1), customFieldsArray.getString(i).trim().replace(" ", "%20"));
            }
        }
        FieldOverrides fieldOverrides = new FieldOverrides(jsonFormInfo.toString());
        Log.e("Json", fieldOverrides.getJSONString());
        startFormActivity(AllConstants.FormNames.ANC_REGISTRATION_OA, null, fieldOverrides.getJSONString());
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout:
                    showProfileView((SmartRegisterClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new EditDialogOptionModel(), view.getTag());
                    break;
            }
        }

        private void showProfileView(SmartRegisterClient client) {
            navigationController.startANC(client.entityId());
        }
    }

    private class EditDialogOptionModel implements DialogOptionModel {
        @Override
        public DialogOption[] getDialogOptions() {
            return getEditOptions();
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            onEditSelection((EditOption) option, (SmartRegisterClient) tag);
        }
    }
}
