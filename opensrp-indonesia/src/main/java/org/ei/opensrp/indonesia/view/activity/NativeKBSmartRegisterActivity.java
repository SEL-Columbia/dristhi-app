package org.ei.opensrp.indonesia.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import static org.ei.opensrp.indonesia.AllConstantsINA.FormNames.*;

import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.indonesia.AllConstantsINA;
import org.ei.opensrp.indonesia.Context;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.adapter.SmartRegisterPaginatedAdapter;
import org.ei.opensrp.indonesia.lib.FlurryFacade;
import org.ei.opensrp.indonesia.provider.KBClientsProvider;
import org.ei.opensrp.indonesia.service.formSubmissionHandler.KBRegistrationHandler;
import org.ei.opensrp.indonesia.service.formSubmissionHandler.KIPNCRegistrationHandler;
import org.ei.opensrp.indonesia.view.contract.KBClient;
import org.ei.opensrp.indonesia.view.controller.BidanVillageController;
import org.ei.opensrp.indonesia.view.controller.KohortKBRegisterController;
import org.ei.opensrp.indonesia.view.dialog.AllHighRiskSort;
import org.ei.opensrp.indonesia.view.dialog.AllKBServiceMode;
import org.ei.opensrp.indonesia.view.dialog.KBMethodSort;
import org.ei.opensrp.indonesia.view.dialog.WifeAgeSort;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.AllClientsFilter;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.DialogOptionMapper;
import org.ei.opensrp.view.dialog.DialogOptionModel;
import org.ei.opensrp.view.dialog.EditOption;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.LocationSelectorDialogFragment;
import org.ei.opensrp.view.dialog.NameSort;
import org.ei.opensrp.view.dialog.OpenFormOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;

/**
 * Created by Dimas Ciputra on 2/18/15.
 */
public class NativeKBSmartRegisterActivity extends BidanSecuredNativeSmartRegisterActivity implements LocationSelectorDialogFragment.OnLocationSelectedListener {

    private SmartRegisterClientsProvider clientProvider = null;
    private KohortKBRegisterController controller;
    private DialogOptionMapper dialogOptionMapper;
    private BidanVillageController villageController;
    public static final String locationDialogTAG = "locationDialogTAG";

    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    private DialogOption[] getEditOptions() {
        return new DialogOption[]{
            new OpenFormOption(getString(R.string.str_kb_update),
                    KOHORT_KB_UPDATE, formController),
            new OpenFormOption(getString(R.string.str_kb_edit),
                    KOHORT_KB_EDIT, formController),
            new OpenFormOption(getString(R.string.str_kb_close),
                        KOHORT_KB_CLOSE, formController),
        };
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new AllKBServiceMode(clientsProvider());
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
                return getResources().getString(R.string.kb_register_title_in_short);
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
                        dialogOptionMapper.mapToVillageFilterOptions(controller.villages());
                return toArray(concat(DEFAULT_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(), new WifeAgeSort(),
                        new KBMethodSort(), new AllHighRiskSort()};
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
            clientProvider = new KBClientsProvider(
                    this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    @Override
    protected void onInitialization() {
        controller = new KohortKBRegisterController(((Context)context).allKartuIbus(),
                context.listCache(),((Context)context).kbClientsCache(),((Context)context).allKohort(), context.villagesCache());
        villageController = new BidanVillageController(context.villagesCache(),((Context)context).allKartuIbus());
        dialogOptionMapper = new DialogOptionMapper();

        context.formSubmissionRouter().getHandlerMap()
                .put(AllConstantsINA.FormNames.KOHORT_KB_REGISTER,
                        new KBRegistrationHandler(((Context)context).kartuIbuService()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryFacade.logEvent("kb_dashboard");
    }

    @Override
    protected void startRegistration() {
        String uniqueIdJson = ((Context)context).uniqueIdController().getUniqueIdJson();
        if(uniqueIdJson == null || uniqueIdJson.isEmpty()) {
            Toast.makeText(this, "No Unique Id", Toast.LENGTH_SHORT).show();
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(locationDialogTAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        LocationSelectorDialogFragment
                .newInstance(this, new EditDialogOptionModel(), context.anmLocationController().get(), KOHORT_KB_REGISTER)
                .show(ft, locationDialogTAG);
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout_ki:
                    showProfileView((KBClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new EditDialogOptionModel(), view.getTag());
                    break;
            }
        }

        private void showProfileView(KBClient kartuIbuClient) {
            navigationControllerINA.startMotherDetail(kartuIbuClient.entityId());
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
            onShowDialogOptionSelection((EditOption)option, client, controller.getRandomNameChars(client));
        }
    }

    @Override
    public void OnLocationSelected(String locationJSONString) {
        JSONObject combined = null;

        try{
            JSONObject locationJSON = new JSONObject(locationJSONString);
            JSONObject uniqueId = new JSONObject(((Context)context).uniqueIdController().getUniqueIdJson());

            combined = locationJSON;
            Iterator<String> iter = uniqueId.keys();

            while(iter.hasNext()){
                String key = iter.next();
                combined.put(key, uniqueId.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(combined != null) {
            FieldOverrides fieldOverrides = new FieldOverrides(combined.toString());
            startFormActivity(KOHORT_KB_REGISTER, null, fieldOverrides.getJSONString());
        }
    }
}
