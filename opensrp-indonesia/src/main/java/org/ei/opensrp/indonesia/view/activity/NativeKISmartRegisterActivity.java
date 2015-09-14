package org.ei.opensrp.indonesia.view.activity;

import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import org.apache.commons.lang3.ArrayUtils;
import org.ei.bidan.R;
import org.ei.bidan.adapter.SmartRegisterPaginatedAdapter;
import org.ei.bidan.bidan.view.contract.BidanVillageController;
import org.ei.bidan.bidan.view.contract.KartuIbuClient;
import org.ei.bidan.bidan.view.dialog.AllHighRiskSort;
import org.ei.bidan.bidan.view.dialog.AllKartuIbuServiceMode;
import org.ei.bidan.bidan.view.controller.KartuIbuRegisterController;
import org.ei.bidan.bidan.view.dialog.NoIbuSort;
import org.ei.bidan.bidan.provider.KIClientsProvider;
import org.ei.bidan.domain.form.FieldOverrides;
import org.ei.bidan.provider.SmartRegisterClientsProvider;
import org.ei.bidan.view.contract.SmartRegisterClient;
import org.ei.bidan.view.dialog.AllClientsFilter;
import org.ei.bidan.view.dialog.DialogOption;
import org.ei.bidan.view.dialog.DialogOptionMapper;
import org.ei.bidan.view.dialog.DialogOptionModel;
import org.ei.bidan.view.dialog.EditOption;
import org.ei.bidan.view.dialog.EstimatedDateOfDeliverySort;
import org.ei.bidan.view.dialog.FilterOption;
import org.ei.bidan.view.dialog.NameSort;
import org.ei.bidan.view.dialog.OpenFormOption;
import org.ei.bidan.view.dialog.ReverseNameSort;
import org.ei.bidan.view.dialog.ServiceModeOption;
import org.ei.bidan.view.dialog.SortOption;
import org.ei.opensrp.adapter.SmartRegisterPaginatedAdapter;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.DialogOptionMapper;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.OpenFormOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.mindscapehq.android.raygun4android.RaygunClient;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static org.ei.bidan.AllConstants.FormNames.ANAK_BAYI_REGISTRATION;
import static org.ei.bidan.AllConstants.FormNames.KARTU_IBU_ANC_REGISTRATION;
import static org.ei.bidan.AllConstants.FormNames.KARTU_IBU_CLOSE;
import static org.ei.bidan.AllConstants.FormNames.KARTU_IBU_EDIT;
import static org.ei.bidan.AllConstants.FormNames.KARTU_IBU_REGISTRATION;
import static org.ei.bidan.AllConstants.FormNames.KOHORT_KB_PELAYANAN;

/**
 * Created by Dimas Ciputra on 2/18/15.
 */
public class NativeKISmartRegisterActivity extends BidanSecuredNativeSmartRegisterActivity {

    private SmartRegisterClientsProvider clientProvider = null;
    private KartuIbuRegisterController controller;
    private DialogOptionMapper dialogOptionMapper;
    private BidanVillageController villageController;

    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    private DialogOption[] getEditOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_register_kb_form), KOHORT_KB_PELAYANAN,
                        formController),
                new OpenFormOption(getString(R.string.str_register_anc_form), KARTU_IBU_ANC_REGISTRATION, formController),
                new OpenFormOption(getString(R.string.str_register_anak_form), ANAK_BAYI_REGISTRATION, formController),
                new OpenFormOption(getString(R.string.str_edit_ki_form), KARTU_IBU_EDIT, formController),
                new OpenFormOption(getString(R.string.str_close_ki_form),KARTU_IBU_CLOSE, formController),
        };
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new AllKartuIbuServiceMode(clientsProvider());
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
                return getResources().getString(R.string.ki_register_title_in_short);
            }
        };
    }

    @Override
    public void setupViews() {
        super.setupViews();
    }

    @Override
    protected NavBarOptionsProvider getNavBarOptionsProvider() {

        return new NavBarOptionsProvider() {

            @Override
            public DialogOption[] filterOptions() {
                Iterable<? extends DialogOption> villageFilterOptions =
                        dialogOptionMapper.mapToVillageFilterOptions(villageController.getVillagesIndonesia());
                return toArray(concat(DEFAULT_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(),
                        new ReverseNameSort(), new NoIbuSort(),
                        new EstimatedDateOfDeliverySort(), new AllHighRiskSort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_ki_search_hint);
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new KIClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    @Override
    protected void onInitialization() {
        controller = new KartuIbuRegisterController(context.allKartuIbus(),
                context.listCache(),context.kiClientsCache(),context.allKohort());
        villageController = new BidanVillageController(context.villagesCache(), context.allKartuIbus());
        dialogOptionMapper = new DialogOptionMapper();
    }

    @Override
    protected void startRegistration() {
        FlurryAgent.logEvent("new_registration");
        String uniqueIdJson = context.uniqueIdController().getUniqueIdJson();
        if(uniqueIdJson == null || uniqueIdJson.isEmpty()) {
            Toast.makeText(this, "No Unique Id", Toast.LENGTH_SHORT).show();
            return;
        }
        FieldOverrides fieldOverrides = new FieldOverrides(uniqueIdJson);
        startFormActivity(KARTU_IBU_REGISTRATION, null, fieldOverrides.getJSONString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.logEvent("kohort_ibu_dashboard", true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("kohort_ibu_dashboard");
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout_ki:
                    showProfileView((KartuIbuClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new EditDialogOptionModel(), view.getTag());
                    break;
            }
        }

        private void showProfileView(KartuIbuClient kartuIbuClient) {
            navigationController.startKI(kartuIbuClient.entityId());
        }
    }

    private class EditDialogOptionModel implements DialogOptionModel {

        @Override
        public DialogOption[] getDialogOptions() {
            return getEditOptions();
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            SmartRegisterClient client = (SmartRegisterClient) tag;

            if(option.name().equalsIgnoreCase(getString(R.string.str_register_anc_form)) ) {
                if(controller.isMotherInANCorPNC(client.entityId())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.mother_already_registered), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            onShowDialogOptionSelection((EditOption) option, client, controller.getRandomNameChars(client));
        }
    }

}
