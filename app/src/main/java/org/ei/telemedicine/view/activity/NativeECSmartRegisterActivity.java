package org.ei.telemedicine.view.activity;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_REGISTRATION;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_REGISTRATION_EC;
import static org.ei.telemedicine.AllConstants.FormNames.EC_CLOSE;
import static org.ei.telemedicine.AllConstants.FormNames.EC_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.EC_REGISTRATION;
import static org.ei.telemedicine.AllConstants.FormNames.FP_CHANGE;
import static org.ei.telemedicine.AllConstants.FormNames.VIEW_ANC_REGISTRATION_EC;
import static org.ei.telemedicine.AllConstants.FormNames.VIEW_EC_REGISTRATION;

import org.codehaus.jackson.node.POJONode;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.ei.telemedicine.adapter.SmartRegisterPaginatedAdapter;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.event.Event;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.provider.ECSmartRegisterClientsProvider;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.ECClient;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.ECSmartRegisterController;
import org.ei.telemedicine.view.controller.VillageController;
import org.ei.telemedicine.view.dialog.AllClientsFilter;
import org.ei.telemedicine.view.dialog.AllEligibleCoupleServiceMode;
import org.ei.telemedicine.view.dialog.DialogOption;
import org.ei.telemedicine.view.dialog.DialogOptionMapper;
import org.ei.telemedicine.view.dialog.DialogOptionModel;
import org.ei.telemedicine.view.dialog.ECNumberSort;
import org.ei.telemedicine.view.dialog.EditOption;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.HighPrioritySort;
import org.ei.telemedicine.view.dialog.NameSort;
import org.ei.telemedicine.view.dialog.OpenFormOption;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

public class NativeECSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private ECSmartRegisterController controller;
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
                return new AllEligibleCoupleServiceMode(clientsProvider());
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
                return getResources().getString(R.string.ec_register_title_in_short);
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
                        new HighPrioritySort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_ec_search_hint);
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new ECSmartRegisterClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    private DialogOption[] getEditOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_register_anc_form), ANC_REGISTRATION, formController, this),
                new OpenFormOption(getString(R.string.str_register_fp_form), FP_CHANGE, formController),
                new OpenFormOption(getString(R.string.str_register_child_form), CHILD_REGISTRATION_EC, formController),
                new OpenFormOption(getString(R.string.str_edit_ec_form), EC_EDIT, formController),
                new OpenFormOption(getString(R.string.view_ec_form), VIEW_EC_REGISTRATION, formController, true),
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
        dialogOptionMapper = new DialogOptionMapper();
    }

    @Override
    public void setupViews() {
        super.setupViews();
        setServiceModeViewDrawableRight(null);
    }

    @Override
    protected void startRegistration(String village) throws JSONException {
        String locationJSON = context.anmLocationController().getFormInfoJSON();
        JSONObject formData = new JSONObject(locationJSON);
        formData.put("village", village.trim().replace(" ", "%20"));
        String customFields = context.allSettings().fetchFieldLabels("ECRegistration");
        if (customFields != null && !customFields.equals("")) {
            JSONArray customFieldsArray = new JSONArray(customFields);
            for (int i = 0; i < customFieldsArray.length(); i++) {
                formData.put("field" + (i + 1), customFieldsArray.getString(i).trim().replace(" ", "%20"));
            }
        }
//        formData.put("ecNumber", context.allSharedPreferences().fetchRegisteredANM() + "-" + System.currentTimeMillis());
        FieldOverrides fieldOverrides = new FieldOverrides(formData.toString());
        startFormActivity(EC_REGISTRATION, null, fieldOverrides.getJSONString());
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout:
                    showProfileView((ECClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new EditDialogOptionModel(), view.getTag());
                    break;
            }
        }

        private void showProfileView(ECClient client) {

            navigationController.startEC(client.entityId());
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
