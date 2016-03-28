package org.ei.opensrp.mcare.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.ei.opensrp.Context;
import org.ei.opensrp.adapter.SmartRegisterPaginatedAdapter;
import org.ei.opensrp.commonregistry.CommonObjectSort;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.commonregistry.CommonRepository;
import org.ei.opensrp.cursoradapter.SecuredNativeSmartRegisterCursorAdapterFragment;
import org.ei.opensrp.cursoradapter.SmartRegisterPaginatedCursorAdapter;
import org.ei.opensrp.cursoradapter.SmartRegisterQueryBuilder;
import org.ei.opensrp.mcare.LoginActivity;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.mcare.elco.ElcoDetailActivity;
import org.ei.opensrp.mcare.elco.ElcoMauzaCommonObjectFilterOption;
import org.ei.opensrp.mcare.elco.ElcoPSRFDueDateSort;
import org.ei.opensrp.mcare.elco.ElcoSearchOption;
import org.ei.opensrp.mcare.elco.ElcoServiceModeOption;
import org.ei.opensrp.mcare.elco.ElcoSmartClientsProvider;
import org.ei.opensrp.mcare.elco.ElcoSmartRegisterActivity;
import org.ei.opensrp.mcare.elco.PSRFHandler;
import org.ei.opensrp.mcare.household.HouseHoldSmartClientsProvider;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.util.StringUtil;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.contract.ECClient;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.controller.VillageController;
import org.ei.opensrp.view.dialog.AllClientsFilter;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.DialogOptionMapper;
import org.ei.opensrp.view.dialog.DialogOptionModel;
import org.ei.opensrp.view.dialog.EditOption;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.fragment.SecuredNativeSmartRegisterFragment;
import org.opensrp.api.domain.Location;
import org.opensrp.api.util.EntityUtils;
import org.opensrp.api.util.LocationTree;
import org.opensrp.api.util.TreeNode;

import java.util.ArrayList;
import java.util.Map;

import util.AsyncTask;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by koros on 11/2/15.
 */
public class ElcoSmartRegisterFragment extends SecuredNativeSmartRegisterCursorAdapterFragment {

    private SmartRegisterClientsProvider clientProvider = null;
    private CommonPersonObjectController controller;
    private VillageController villageController;
    private DialogOptionMapper dialogOptionMapper;

    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected SecuredNativeSmartRegisterActivity.DefaultOptionsProvider getDefaultOptionsProvider() {
        return new SecuredNativeSmartRegisterActivity.DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new ElcoServiceModeOption(clientsProvider());
            }

            @Override
            public FilterOption villageFilter() {
                return new AllClientsFilter();
            }

            @Override
            public SortOption sortOption() {
                return new ElcoPSRFDueDateSort();

            }

            @Override
            public String nameInShortFormForTitle() {
                return getResources().getString(org.ei.opensrp.R.string.ec_register_title_in_short);
            }
        };
    }

    @Override
    protected SecuredNativeSmartRegisterActivity.NavBarOptionsProvider getNavBarOptionsProvider() {
        return new SecuredNativeSmartRegisterActivity.NavBarOptionsProvider() {

            @Override
            public DialogOption[] filterOptions() {
                ArrayList<DialogOption> dialogOptionslist = new ArrayList<DialogOption>();
                dialogOptionslist.add(new AllClientsFilter());
                String locationjson = context.anmLocationController().get();
                LocationTree locationTree = EntityUtils.fromJson(locationjson, LocationTree.class);

                Map<String,TreeNode<String, Location>> locationMap =
                        locationTree.getLocationsHierarchy();
                addChildToList(dialogOptionslist,locationMap);
                DialogOption[] dialogOptions = new DialogOption[dialogOptionslist.size()];
                for (int i = 0;i < dialogOptionslist.size();i++){
                    dialogOptions[i] = dialogOptionslist.get(i);
                }

                return  dialogOptions;
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{
                        new ElcoPSRFDueDateSort(),
                        new CommonObjectSort(CommonObjectSort.ByColumnAndByDetails.byDetails,false,"FWWOMFNAME", Context.getInstance().applicationContext().getString(R.string.elco_alphabetical_sort)),
                        new CommonObjectSort(CommonObjectSort.ByColumnAndByDetails.byDetails,true,"GOBHHID", Context.getInstance().applicationContext().getString(R.string.hh_fwGobhhid_sort)),
                        new CommonObjectSort(CommonObjectSort.ByColumnAndByDetails.byDetails,true,"JiVitAHHID", Context.getInstance().applicationContext().getString(R.string.hh_fwJivhhid_sort))

//                        new CommonObjectSort(true,false,true,"age")
                };
            }

            @Override
            public String searchHint() {
                return getString(org.ei.opensrp.R.string.str_ec_search_hint);
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
//            clientProvider = new ElcoSmartClientsProvider(
//                    getActivity(),clientActionHandler , controller);
        }
        return clientProvider;
    }

    @Override
    protected void onInitialization() {
//        controller = new CommonPersonObjectController(context.allCommonsRepositoryobjects("elco"),
//                context.allBeneficiaries(), context.listCache(),
//                context.personObjectClientsCache(),"FWWOMFNAME","elco","FWELIGIBLE","1", CommonPersonObjectController.ByColumnAndByDetails.byDetails.byDetails,"FWWOMFNAME", CommonPersonObjectController.ByColumnAndByDetails.byDetails,new ElcoPSRFDueDateSort());
////                context.personObjectClientsCache(),"FWWOMFNAME","elco","FWELIGIBLE","1", CommonPersonObjectController.ByColumnAndByDetails.byDetails.byDetails,"FWWOMFNAME", CommonPersonObjectController.ByColumnAndByDetails.byDetails);
//
//        villageController = new VillageController(context.allEligibleCouples(),
//                context.listCache(), context.villagesCache());
//        dialogOptionMapper = new DialogOptionMapper();
        context.formSubmissionRouter().getHandlerMap().put("psrf_form", new PSRFHandler());
    }

    @Override
    protected void startRegistration() {
        ((ElcoSmartRegisterActivity)getActivity()).startRegistration();
    }

    @Override
    protected void onCreation() {
    }
    @Override
    protected void onResumption() {
        super.onResumption();
        getDefaultOptionsProvider();
        updateSearchView();
        try{
            LoginActivity.setLanguage();
        }catch (Exception e){

        }

    }

    @Override
    public void setupViews(View view) {
        super.setupViews(view);
//
        view.findViewById(R.id.btn_report_month).setVisibility(INVISIBLE);
//
        ImageButton startregister = (ImageButton)view.findViewById(org.ei.opensrp.R.id.register_client);
        startregister.setVisibility(View.GONE);
        setServiceModeViewDrawableRight(null);
//        updateSearchView();
        getDefaultOptionsProvider();

//        super.setupViews(view);
        view.findViewById(R.id.btn_report_month).setVisibility(INVISIBLE);
        ListView list = (ListView) view.findViewById(R.id.list);
        list.setVisibility(View.VISIBLE);
        clientsProgressView.setVisibility(View.INVISIBLE);
//        list.setBackgroundColor(Color.RED);
        CommonRepository commonRepository = context.commonrepository("elco");
        setTablename("elco");
        SmartRegisterQueryBuilder countqueryBUilder = new SmartRegisterQueryBuilder();
        countqueryBUilder.SelectInitiateMainTableCounts("elco");
        countqueryBUilder.joinwithALerts("elco","ELCO PSRF");
        countSelect = countqueryBUilder.mainCondition(" FWWOMFNAME is not null  and details LIKE '%\"FWELIGIBLE\":\"1\"%' ");
        CountExecute();


        SmartRegisterQueryBuilder queryBUilder = new SmartRegisterQueryBuilder();
        queryBUilder.SelectInitiateMainTable("elco", new String[]{"relationalid", "details", "FWWOMFNAME", "JiVitAHHID", "GOBHHID"});
        queryBUilder.joinwithALerts("elco","ELCO PSRF");
        mainSelect = queryBUilder.mainCondition(" FWWOMFNAME != \"\"  and FWWOMFNAME is not null  and details LIKE '%\"FWELIGIBLE\":\"1\"%' ");
        queryBUilder.addCondition(filters);
        Sortqueries = sortByAlertmethod();
        currentquery  = queryBUilder.orderbyCondition(Sortqueries);


//        queryBUilder.queryForRegisterSortBasedOnRegisterAndAlert("household", new String[]{"relationalid" ,"details","FWHOHFNAME", "FWGOBHHID","FWJIVHHID"}, null, "FW CENSUS");
//        Cursor c = commonRepository.CustomQueryForAdapter(new String[]{"id as _id","relationalid","details"},"household",""+currentlimit,""+currentoffset);
        Cursor c = commonRepository.RawCustomQueryForAdapter(queryBUilder.Endquery(queryBUilder.addlimitandOffset(currentquery, 20, 0)));
        ElcoSmartClientsProvider hhscp = new ElcoSmartClientsProvider(getActivity(),clientActionHandler,context.alertService());
        clientAdapter = new SmartRegisterPaginatedCursorAdapter(getActivity(), c, hhscp, new CommonRepository("elco",new String []{ "FWWOMFNAME", "JiVitAHHID", "GOBHHID"}));
        list.setAdapter(clientAdapter);
//        setServiceModeViewDrawableRight(null);
//        updateSearchView();
        refresh();
    }
    @Override
    public void onSortSelection(SortOption sortBy) {
        appliedSortView.setText(sortBy.name());
        if(sortBy.name().equalsIgnoreCase(Context.getInstance().applicationContext().getString(R.string.elco_alphabetical_sort))){
            householdSortByName();
        }
        if(sortBy.name().equalsIgnoreCase( Context.getInstance().applicationContext().getString(R.string.hh_fwGobhhid_sort))){
            householdSortByFWGOBHHID();
        }
        if(sortBy.name().equalsIgnoreCase(Context.getInstance().applicationContext().getString(R.string.hh_fwJivhhid_sort))){
            householdSortByFWJIVHHID();
        }
        if(sortBy.name().equalsIgnoreCase(Context.getInstance().applicationContext().getString(R.string.due_status))){
            Sortqueries = sortByAlertmethod();
            filterandSortExecute();
        }
    }
    @Override
    public void onFilterSelection(FilterOption filter) {

       if(filter.name().equalsIgnoreCase(getString(R.string.filter_by_all_label))){
            filters = "";
            CountExecute();
            filterandSortExecute();
        }else{
           ElcoMauzaCommonObjectFilterOption mauzafilter = (ElcoMauzaCommonObjectFilterOption)filter;
            String criteria = mauzafilter.criteria;
            CountExecute();
            filters = " and details LIKE '%"+criteria+"%'";
            filterandSortExecute();
        }

    }
    private void householdSortByName() {
        Sortqueries = " FWWOMFNAME ASC";
        filterandSortExecute();
    }
    private void householdSortByFWGOBHHID(){
        Sortqueries = " GOBHHID ASC";
        filterandSortExecute();
    }
    private void householdSortByFWJIVHHID(){
        Sortqueries = " JiVitAHHID ASC";
        filterandSortExecute();
    }


    private DialogOption[] getEditOptions(CommonPersonObjectClient tag) {
        return ((ElcoSmartRegisterActivity)getActivity()).getEditOptions(tag);
    }


    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_info_layout:
                    ElcoDetailActivity.Elcoclient = (CommonPersonObjectClient)view.getTag();
                    Intent intent = new Intent(getActivity(), ElcoDetailActivity.class);
                    startActivity(intent);
                    break;
                case R.id.psrf_due_date:
                    showFragmentDialog(new EditDialogOptionModel((CommonPersonObjectClient)view.getTag()), view.getTag());
                    break;
            }
        }

        private void showProfileView(ECClient client) {
            navigationController.startEC(client.entityId());
        }
    }

    private class EditDialogOptionModel implements DialogOptionModel {
        CommonPersonObjectClient tag;
        public EditDialogOptionModel(CommonPersonObjectClient tag) {
            this.tag = tag;
        }

        @Override
        public DialogOption[] getDialogOptions() {
            return getEditOptions(tag);
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {
            onEditSelection((EditOption) option, (SmartRegisterClient) tag);
        }
    }
    @Override
    public void setupSearchView(View view) {
        searchView = (EditText) view.findViewById(org.ei.opensrp.R.id.edt_search);
        searchView.setHint(getNavBarOptionsProvider().searchHint());
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(final CharSequence cs, int start, int before, int count) {

                (new AsyncTask() {
                    SmartRegisterClients filteredClients;

                    @Override
                    protected Object doInBackground(Object[] params) {
//                        currentSearchFilter =
//                        setCurrentSearchFilter(new HHSearchOption(cs.toString()));
//                        filteredClients = getClientsAdapter().getListItemProvider()
//                                .updateClients(getCurrentVillageFilter(), getCurrentServiceModeOption(),
//                                        getCurrentSearchFilter(), getCurrentSortOption());
//
                        if(cs.toString().equalsIgnoreCase("")){
                            filters = "";
                        }else {
                            filters = "and FWWOMFNAME Like '%" + cs.toString() + "%' or GOBHHID Like '%" + cs.toString() + "%'  or JiVitAHHID Like '%" + cs.toString() + "%' ";
                        }
                            return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
//                        clientsAdapter
//                                .refreshList(currentVillageFilter, currentServiceModeOption,
//                                        currentSearchFilter, currentSortOption);
//                        getClientsAdapter().refreshClients(filteredClients);
//                        getClientsAdapter().notifyDataSetChanged();
                        getSearchCancelView().setVisibility(isEmpty(cs) ? INVISIBLE : VISIBLE);
                        CountExecute();
                        filterandSortExecute();
                        super.onPostExecute(o);
                    }
                }).execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        searchCancelView = view.findViewById(org.ei.opensrp.R.id.btn_search_cancel);
        searchCancelView.setOnClickListener(searchCancelHandler);
    }
    public void updateSearchView(){
        getSearchView().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(final CharSequence cs, int start, int before, int count) {
                (new AsyncTask() {
                    SmartRegisterClients filteredClients;

                    @Override
                    protected Object doInBackground(Object[] params) {
//                        currentSearchFilter =
//                        setCurrentSearchFilter(new HHSearchOption(cs.toString()));
//                        filteredClients = getClientsAdapter().getListItemProvider()
//                                .updateClients(getCurrentVillageFilter(), getCurrentServiceModeOption(),
//                                        getCurrentSearchFilter(), getCurrentSortOption());
//
                        if(cs.toString().equalsIgnoreCase("")){
                            filters = "";
                        }else {
                            filters = "and FWWOMFNAME Like '%" + cs.toString() + "%' or GOBHHID Like '%" + cs.toString() + "%'  or JiVitAHHID Like '%" + cs.toString() + "%' ";
                        }
                            return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
//                        clientsAdapter
//                                .refreshList(currentVillageFilter, currentServiceModeOption,
//                                        currentSearchFilter, currentSortOption);
//                        getClientsAdapter().refreshClients(filteredClients);
//                        getClientsAdapter().notifyDataSetChanged();
                        getSearchCancelView().setVisibility(isEmpty(cs) ? INVISIBLE : VISIBLE);
                        filterandSortExecute();
                        super.onPostExecute(o);
                    }
                }).execute();
//                currentSearchFilter = new HHSearchOption(cs.toString());
//                clientsAdapter
//                        .refreshList(currentVillageFilter, currentServiceModeOption,
//                                currentSearchFilter, currentSortOption);
//
//                searchCancelView.setVisibility(isEmpty(cs) ? INVISIBLE : VISIBLE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void addChildToList(ArrayList<DialogOption> dialogOptionslist,Map<String,TreeNode<String, Location>> locationMap){
        for(Map.Entry<String, TreeNode<String, Location>> entry : locationMap.entrySet()) {

            if(entry.getValue().getChildren() != null) {
                addChildToList(dialogOptionslist,entry.getValue().getChildren());

            }else{
                StringUtil.humanize(entry.getValue().getLabel());
                String name = StringUtil.humanize(entry.getValue().getLabel());
                dialogOptionslist.add(new ElcoMauzaCommonObjectFilterOption(name,"location_name", ElcoMauzaCommonObjectFilterOption.ByColumnAndByDetails.byDetails,name));

            }
        }
    }
    private String sortByAlertmethod() {
        return " CASE WHEN alerts.status = 'urgent' THEN '1'"
                +
                "WHEN alerts.status = 'upcoming' THEN '2'\n" +
                "WHEN alerts.status = 'normal' THEN '3'\n" +
                "WHEN alerts.status = 'expired' THEN '4'\n" +
                "WHEN alerts.status is Null THEN '5'\n" +
                "Else alerts.status END ASC";
    }

}
