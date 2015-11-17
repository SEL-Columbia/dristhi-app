package org.ei.opensrp.indonesia.view.fragment;

import android.view.View;

import org.ei.opensrp.adapter.SmartRegisterPaginatedAdapter;
import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.indonesia.AllConstantsINA;
import org.ei.opensrp.indonesia.Context;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.provider.KIPNCClientsProvider;
import org.ei.opensrp.indonesia.view.activity.NativeKIPNCSmartRegisterActivity;
import org.ei.opensrp.indonesia.view.contract.KIPNCClient;
import org.ei.opensrp.indonesia.view.controller.BidanVillageController;
import org.ei.opensrp.indonesia.view.controller.KIPNCRegisterController;
import org.ei.opensrp.indonesia.view.dialog.AllHighRiskSort;
import org.ei.opensrp.indonesia.view.dialog.DusunSort;
import org.ei.opensrp.indonesia.view.dialog.KIPNCOverviewServiceMode;
import org.ei.opensrp.indonesia.view.dialog.WifeAgeSort;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.AllClientsFilter;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.DialogOptionMapper;
import org.ei.opensrp.view.dialog.DialogOptionModel;
import org.ei.opensrp.view.dialog.EditOption;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.NameSort;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.json.JSONException;
import org.json.JSONObject;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;

/**
 * Created by koros on 10/29/15.
 */
public class NativeKIPNCSmartRegisterFragment extends BidanSecuredNativeSmartRegisterFragment{

    private SmartRegisterClientsProvider clientProvider = null;
    private KIPNCRegisterController controller;
    private final ClientActionHandler clientActionHandler = new ClientActionHandler();
    private DialogOptionMapper dialogOptionMapper;
    private BidanVillageController villageController;

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected SecuredNativeSmartRegisterActivity.DefaultOptionsProvider getDefaultOptionsProvider() {
        return new SecuredNativeSmartRegisterActivity.DefaultOptionsProvider() {
            @Override
            public ServiceModeOption serviceMode() {
                return new KIPNCOverviewServiceMode(clientsProvider());
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
                return getResources().getString(R.string.home_pnc_label) + ": ";
            }
        };
    }

    @Override
    protected SecuredNativeSmartRegisterActivity.NavBarOptionsProvider getNavBarOptionsProvider() {
        return new SecuredNativeSmartRegisterActivity.NavBarOptionsProvider() {

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
                return new DialogOption[]{new NameSort(), new WifeAgeSort(), new DusunSort(), new AllHighRiskSort()};
            }

            @Override
            public String searchHint() {
                return getString(R.string.str_ki_search_hint);
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if(clientProvider == null) {
            clientProvider = new KIPNCClientsProvider((NativeKIPNCSmartRegisterActivity)getActivity(), clientActionHandler, controller);
        }
        return clientProvider;
    }

    @Override
    protected void onInitialization() {
        controller = new KIPNCRegisterController(
                ((Context)context).allKohort(),
                context.listCache(),
                ((Context)context).kartuIbuPNCClientsCache(),
                context.villagesCache());
        villageController = new BidanVillageController(
                context.villagesCache(),
                ((Context)context).allKartuIbus());
        dialogOptionMapper = new DialogOptionMapper();
    }

    @Override
    protected void startRegistration() {
        //
    }

    @Override
    protected void onCreation() {
        
    }

    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout_ki:
                    showProfileView((KIPNCClient) view.getTag());
                    break;
                case R.id.btn_edit:
                    showFragmentDialog(new EditDialogOptionModel(), view.getTag());
                    break;
            }
        }
        private void showProfileView(KIPNCClient kartuIbuClient) {
            navigationControllerINA.startMotherDetail(kartuIbuClient.getKartuIbuEntityId());
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
            JSONObject obj = new JSONObject();
            try {
                obj.put(AllConstantsINA.KartuAnakFields.DATE_OF_BIRTH, ((KIPNCClient) client).getLastChild().dateOfBirth());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FieldOverrides fieldOverrides = new FieldOverrides(obj.toString());
            onShowDialogOptionSelectionWithMetadata((EditOption) option, client, controller.getRandomNameChars(client), fieldOverrides.getJSONString());
        }
    }

    private DialogOption[] getEditOptions() {
        return ((NativeKIPNCSmartRegisterActivity)getActivity()).getEditOptions();
    }

}
