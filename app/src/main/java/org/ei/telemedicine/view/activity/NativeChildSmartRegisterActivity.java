package org.ei.telemedicine.view.activity;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static java.util.Arrays.asList;

import java.util.List;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.ei.telemedicine.adapter.SmartRegisterPaginatedAdapter;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.provider.ChildSmartRegisterClientsProvider;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.ChildSmartRegisterController;
import org.ei.telemedicine.view.controller.VillageController;
import org.ei.telemedicine.view.dialog.AllClientsFilter;
import org.ei.telemedicine.view.dialog.ChildAgeSort;
import org.ei.telemedicine.view.dialog.ChildHighRiskSort;
import org.ei.telemedicine.view.dialog.ChildImmunization0to9ServiceMode;
import org.ei.telemedicine.view.dialog.ChildImmunization9PlusServiceMode;
import org.ei.telemedicine.view.dialog.ChildOverviewServiceMode;
import org.ei.telemedicine.view.dialog.DialogOption;
import org.ei.telemedicine.view.dialog.DialogOptionMapper;
import org.ei.telemedicine.view.dialog.DialogOptionModel;
import org.ei.telemedicine.view.dialog.EditOption;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.NameSort;
import org.ei.telemedicine.view.dialog.OpenFormOption;
import org.ei.telemedicine.view.dialog.OutOfAreaFilter;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;

public class NativeChildSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private ChildSmartRegisterController controller;
    private VillageController villageController;
    private DialogOptionMapper dialogOptionMapper;
    public static final List<? extends DialogOption> DEFAULT_CHILD_FILTER_OPTIONS =
            asList(new OutOfAreaFilter());

    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new ChildSmartRegisterClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new ChildOverviewServiceMode(clientsProvider());
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
                return getResources().getString(R.string.child_register_title_in_short);
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
                return toArray(concat(DEFAULT_FILTER_OPTIONS, DEFAULT_CHILD_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{
                        new ChildOverviewServiceMode(clientsProvider()),
                        new ChildImmunization0to9ServiceMode(clientsProvider()),
                        new ChildImmunization9PlusServiceMode(clientsProvider())
                };
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(), new ChildAgeSort(),
                        new ChildHighRiskSort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_child_search_hint);
            }
        };
    }

    private DialogOption[] getEditOptions() {
        return new DialogOption[]{
                new OpenFormOption("Child Edit", AllConstants.FormNames.CHILD_EDIT, formController),
                new OpenFormOption(getString(R.string.str_child_immunizations), AllConstants.FormNames.CHILD_IMMUNIZATIONS, formController),
                new OpenFormOption(getString(R.string.str_child_illness), AllConstants.FormNames.CHILD_ILLNESS, formController),
                new OpenFormOption(getString(R.string.str_anc_plan_of_care), AllConstants.VIEW_CHILD_PLAN_OF_CARE, formController, NativeChildSmartRegisterActivity.this),
                new OpenFormOption(getString(R.string.str_child_close), AllConstants.FormNames.CHILD_CLOSE, formController),
                new OpenFormOption(getString(R.string.str_vitamin_a), AllConstants.FormNames.VITAMIN_A, formController)
        };
    }

    @Override
    protected void onInitialization() {
        controller = new ChildSmartRegisterController(
                context.serviceProvidedService(),
                context.alertService(),
                context.allBeneficiaries(),
                context.listCache(),
                context.smartRegisterClientsCache());

        villageController = new VillageController(
                context.allEligibleCouples(),
                context.listCache(),
                context.villagesCache());

        dialogOptionMapper = new DialogOptionMapper();

        clientsProvider().onServiceModeSelected(new ChildOverviewServiceMode(clientsProvider()));
    }

    @Override
    protected void startRegistration(String village) throws JSONException {
        String locationJSON = context.anmLocationController().getFormInfoJSON();
        JSONObject jsonFormInfo = new JSONObject(locationJSON);
        jsonFormInfo.put("village", village.trim().replace(" ", "%20"));
        String customFields = context.allSettings().fetchFieldLabels("ChildRegistration");
        if (customFields != null && !customFields.equals("")) {
            JSONArray customFieldsArray = new JSONArray(customFields);
            for (int i = 0; i < customFieldsArray.length(); i++) {
                jsonFormInfo.put("field" + (i + 1), customFieldsArray.getString(i).trim().replace(" ", "%20"));
            }
        }
        FieldOverrides fieldOverrides = new FieldOverrides(jsonFormInfo.toString());
        startFormActivity(AllConstants.FormNames.CHILD_REGISTRATION_OA, null, fieldOverrides.getJSONString());
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
            navigationController.startChild(client.entityId());
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
