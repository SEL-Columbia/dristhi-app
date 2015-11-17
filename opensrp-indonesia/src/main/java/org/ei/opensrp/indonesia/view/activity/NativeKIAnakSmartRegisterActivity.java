package org.ei.opensrp.indonesia.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.indonesia.AllConstantsINA;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.adapter.SmartRegisterPaginatedAdapter;
import org.ei.opensrp.indonesia.Context;
import org.ei.opensrp.indonesia.lib.FlurryFacade;
import org.ei.opensrp.indonesia.provider.AnakRegisterClientsProvider;
import org.ei.opensrp.indonesia.service.formSubmissionHandler.AnakRegistrationHandler;
import org.ei.opensrp.indonesia.util.StringUtil;
import org.ei.opensrp.indonesia.view.contract.AnakClient;
import org.ei.opensrp.indonesia.view.controller.AnakRegisterController;
import org.ei.opensrp.indonesia.view.dialog.AllHighRiskSort;
import org.ei.opensrp.indonesia.view.dialog.AnakImmunizationServiceMode;
import org.ei.opensrp.indonesia.view.dialog.AnakOverviewServiceMode;
import org.ei.opensrp.indonesia.view.dialog.ReverseNameSort;
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
import static org.ei.opensrp.indonesia.AllConstantsINA.FormNames.*;

/**
 * Created by Dimas Ciputra on 4/7/15.
 */
public class NativeKIAnakSmartRegisterActivity extends BidanSecuredNativeSmartRegisterActivity implements LocationSelectorDialogFragment.OnLocationSelectedListener{

    private SmartRegisterClientsProvider clientProvider = null;
    private AnakRegisterController controller;
    private final ClientActionHandler clientActionHandler = new ClientActionHandler();
    private DialogOptionMapper dialogOptionMapper;
    public static final String locationDialogTAG = "locationDialogTAG";

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if(clientProvider == null) {
            clientProvider = new AnakRegisterClientsProvider(this, clientActionHandler, controller);
        }
        return clientProvider;
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new AnakOverviewServiceMode(clientsProvider());
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
                        dialogOptionMapper.mapToVillageFilterOptions(controller.villages());
                return toArray(concat(DEFAULT_FILTER_OPTIONS, villageFilterOptions), DialogOption.class);
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{new AnakOverviewServiceMode(clientsProvider()),
                new AnakImmunizationServiceMode(clientsProvider())};
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{new NameSort(), new ReverseNameSort(), new AllHighRiskSort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_ki_search_hint);
            }
        };
    }

    @Override
    protected void onInitialization() {
        controller = new AnakRegisterController(
                ((Context)context).allKohort(),
                context.alertService(),
                context.serviceProvidedService(),
                context.listCache(),
                context.smartRegisterClientsCache(),
                context.villagesCache());

        clientsProvider().onServiceModeSelected(new AnakOverviewServiceMode(clientsProvider()));
        dialogOptionMapper = new DialogOptionMapper();

        context.formSubmissionRouter().getHandlerMap()
                .put(AllConstantsINA.FormNames.KARTU_IBU_REGISTRATION,
                        new AnakRegistrationHandler(((Context)context).kartuAnakService()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryFacade.logEvent("anak_dashboard");
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
                .newInstance(this, new EditDialogOptionModel(), context.anmLocationController().get(), ANAK_NEW_REGISTRATION)
                .show(ft, locationDialogTAG);
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout:
                    showProfileView((AnakClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new EditDialogOptionModel(), view.getTag());
                    break;
                case R.id.immunization_service_mode_views:
                    formController.startFormActivity(BAYI_IMUNISASI, "" + view.getTag(), null);
                    break;
            }
        }

        public void showProfileView(AnakClient client) {
            navigationControllerINA.startChildDetail(client.entityId());
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

    public DialogOption[] getEditOptions() {
        return new DialogOption[]{
                new OpenFormOption(getString(R.string.str_anak_neonatal),
                        BAYI_NEONATAL_PERIOD, formController),
                new OpenFormOption(getString(R.string.str_anak_bayi_visit),
                        KOHORT_BAYI_KUNJUNGAN, formController),
                new OpenFormOption(getString(R.string.str_anak_balita_visit),
                        BALITA_KUNJUNGAN, formController),
                new OpenFormOption(getString(R.string.str_anak_edit),
                        KOHORT_BAYI_EDIT, formController),
                new OpenFormOption(getString(R.string.str_tutup_anak),
                        KARTU_IBU_ANAK_CLOSE, formController),
        };
    }

    @Override
    public void OnLocationSelected(String locationJSONString) {
        FieldOverrides fieldOverrides = new FieldOverrides(
                StringUtil.mergeTwoJSONString(locationJSONString, ((Context)context).uniqueIdController().getUniqueIdJson()));
        startFormActivity(ANAK_NEW_REGISTRATION, null, fieldOverrides.getJSONString());
    }
}
