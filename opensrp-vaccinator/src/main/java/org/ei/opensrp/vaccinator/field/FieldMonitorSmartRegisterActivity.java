package org.ei.opensrp.vaccinator.field;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.vaccinator.R;
import org.ei.opensrp.vaccinator.child.ChildSearchOption;
import org.ei.opensrp.vaccinator.child.ChildSmartClientsProvider;
import org.ei.opensrp.vaccinator.domain.Field;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.AllClientsFilter;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.json.JSONObject;
import org.opensrp.api.domain.Location;
import org.opensrp.api.util.EntityUtils;
import org.opensrp.api.util.LocationTree;
import org.opensrp.api.util.TreeNode;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by muhammad.ahmed@ihsinformatics.com on 19-Oct-15.
 */
public class FieldMonitorSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private CommonPersonObjectController controller;
    HashMap<String,String> map=new HashMap<>();
    private SmartRegisterClientsProvider clientProvider = null;
    private DefaultOptionsProvider defaultOptionProvider;
    private NavBarOptionsProvider navBarOptionsProvider;
    private final ClientActionHandler clientActionHandler = new ClientActionHandler();
    //private CommonRepository
    public static boolean sortbymonth;


    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {
            @Override
            public ServiceModeOption serviceMode() {
                if(sortbymonth) {
                    return new FieldMonitorServiceModeOption(clientsProvider());
                }else{

                    return new FieldMonitorServiceModeOptionDaily(clientsProvider());
                }
                }

            @Override
            public FilterOption villageFilter() {
                return new AllClientsFilter();
            }

            @Override
            public SortOption sortOption() {
                return new FieldSort(FieldSort.ByMonthANDByDAILY.ByMonth,"report");
            }

            @Override
            public String nameInShortFormForTitle() {
               return getResources().getString(R.string.field_title);
            }
        };
    }

    @Override
    protected NavBarOptionsProvider getNavBarOptionsProvider() {
        return new NavBarOptionsProvider() {
            @Override
            public DialogOption[] filterOptions() {
                return new DialogOption[0];
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[0];
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[0];
            }

            @Override
            public String searchHint() {
                return  getResources().getString(R.string.str_field_search_hint);
            }
        };
    }



    @Override
    protected void onInitialization() {
        if(sortbymonth) {
            controller = new CommonPersonObjectController(context.allCommonsRepositoryobjects("field"),
                    context.allBeneficiaries(), context.listCache(),
                    context.personObjectClientsCache(), "date", "field", "report", "monthly",
                    CommonPersonObjectController.ByColumnAndByDetails.byColumn
                    ,"date",
                    CommonPersonObjectController.ByColumnAndByDetails.byColumn);

        }else {
            controller = new CommonPersonObjectController(context.allCommonsRepositoryobjects("field"),
                    context.allBeneficiaries(), context.listCache(),
                    context.personObjectClientsCache(), "date", "field", "report", "daily",
                    CommonPersonObjectController.ByColumnAndByDetails.byColumn
                    ,"date",
                    CommonPersonObjectController.ByColumnAndByDetails.byColumn);
        }
    }


    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
if(sortbymonth) {
    clientProvider = new FieldMonitorSmartClientsProvider(
            this, clientActionHandler, controller, context.alertService(), FieldMonitorSmartClientsProvider.ByMonthANDByDAILY.ByMonth, context);
}else{

    clientProvider = new FieldMonitorSmartClientsProvider(
            this, clientActionHandler, controller, context.alertService(), FieldMonitorSmartClientsProvider.ByMonthANDByDAILY.ByDaily, context);

}
        return clientProvider;
    }
    @Override
    public void startRegistration() {
        //embedding data for testing purpose


        String locationjson = context.anmLocationController().get();
        LocationTree locationTree = EntityUtils.fromJson(locationjson, LocationTree.class);

        Map<String,TreeNode<String, Location>> locationMap =
                locationTree.getLocationsHierarchy();

        addChildToList(locationMap,"Country");
        addChildToList(locationMap,"province");
        addChildToList(locationMap,"city");
        addChildToList(locationMap,"town");
        addChildToList(locationMap, "uc");


        map.put("provider_uc", locations.get("uc"));
        map.put("provider_town", locations.get("town"));
        map.put("provider_city",locations.get("city"));
        map.put("provider_province", locations.get("province"));
        map.put("provider_location_id",locations.get("uc"));
        map.put("provider_location_name", locations.get("uc"));
        map.put("provider_id",context.anmService().fetchDetails().name());

        startForm("vaccine_stock_position_form", null, map,ByColumnAndByDetails.bydefault);
       // startFormActivity("vaccine_stock_position_form",null ,);

    }

    @Override
    protected void onCreation() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreation();
        setContentView(R.layout.smart_register_activity);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        onInitialization();

        defaultOptionProvider = getDefaultOptionsProvider();
        navBarOptionsProvider = getNavBarOptionsProvider();

        setupViews();
    }


    private class ClientActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(sortbymonth){
                    FieldMonitorMonthlyDetailActivity.userMap=map;
                FieldMonitorMonthlyDetailActivity.fieldclient=(CommonPersonObjectClient)v.getTag(R.id.field_daymonth);
                FieldMonitorMonthlyDetailActivity.usedVaccines=(HashMap<String,String>)v.getTag(R.id.field_daymonth_layout);
                FieldMonitorMonthlyDetailActivity.wastedVaccines=(HashMap<String,String>)v.getTag(R.id.field_month_target_layout);

                Intent intent =new Intent(FieldMonitorSmartRegisterActivity.this,FieldMonitorMonthlyDetailActivity.class);
                startActivity(intent);
                finish();
            }else{
                FieldMonitorDailyDetailActivity.userMap=map;
                FieldMonitorDailyDetailActivity.fieldclient=(CommonPersonObjectClient)v.getTag(R.id.field_day);
                FieldMonitorDailyDetailActivity.usedVaccines=(HashMap<String,String>)v.getTag(R.id.field_day_layout);
                Intent intent =new Intent(FieldMonitorSmartRegisterActivity.this,FieldMonitorDailyDetailActivity.class);
                startActivity(intent);
                finish();

            }

        }
    }


    @Override
    protected void onResumption() {
        super.onResumption();
        getDefaultOptionsProvider();
        updateSearchView();
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
                        setCurrentSearchFilter(new FieldSearchOption(cs.toString()));
                        filteredClients = getClientsAdapter().getListItemProvider()
                                .updateClients(getCurrentVillageFilter(), getCurrentServiceModeOption(),
                                        getCurrentSearchFilter(), getCurrentSortOption());


                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
//                        clientsAdapter
//                                .refreshList(currentVillageFilter, currentServiceModeOption,
//                                        currentSearchFilter, currentSortOption);
                        getClientsAdapter().refreshClients(filteredClients);
                        getClientsAdapter().notifyDataSetChanged();
                        getSearchCancelView().setVisibility(isEmpty(cs) ? INVISIBLE : VISIBLE);
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



    public enum ByColumnAndByDetails{
        byColumn,byDetails,bydefault;
    }
    private void startForm(String formName,SmartRegisterClient client ,HashMap<String , String> overrideStringmap , ByColumnAndByDetails byColumnAndByDetails) {

       // Log.d("form COntroller check", formController.toString() );
        if(overrideStringmap == null) {
            org.ei.opensrp.util.Log.logDebug("overrides data is null");
            if(null==client){
                formController.startFormActivity(formName, null, null);

            }else {

                formController.startFormActivity(formName, client.entityId(), null);
            }
        }else{
            JSONObject overridejsonobject = new JSONObject();
            try {
                for (Map.Entry<String, String> entry : overrideStringmap.entrySet()) {
                    switch (byColumnAndByDetails){
                        case byDetails:
                            overridejsonobject.put(entry.getKey() , ((CommonPersonObjectClient)client).getDetails().get(entry.getValue()));
                            break;
                        case byColumn:
                            overridejsonobject.put(entry.getKey() , ((CommonPersonObjectClient)client).getColumnmaps().get(entry.getValue()));
                            break;
                        case bydefault:
                            overridejsonobject.put(entry.getKey() ,entry.getValue());
                            break;
                    }
                }
//                overridejsonobject.put("existing_MWRA", );
            }catch (Exception e){
                e.printStackTrace();
            }
            // org.ei.opensrp.util.Log.logDebug("overrides data is : " + overrideStringmap);
            FieldOverrides fieldOverrides = new FieldOverrides(overridejsonobject.toString());
            org.ei.opensrp.util.Log.logDebug("fieldOverrides data is : " + fieldOverrides.getJSONString());
            //formController.startFormActivity(formName, client.entityId(), fieldOverrides.getJSONString());

            if(null==client){
                formController.startFormActivity(formName, null, fieldOverrides.getJSONString());

            }else {

                formController.startFormActivity(formName, client.entityId(), fieldOverrides.getJSONString());
            }

        }
    }

    private Map<String,String> locations =new HashMap<String,String>();


    public void addChildToList(Map<String,TreeNode<String, Location>> locationMap, String locationTag){
        for(Map.Entry<String, TreeNode<String, Location>> entry : locationMap.entrySet()) {
            boolean tagFound=false;
            if(entry.getValue() != null) {
                Location l= entry.getValue().getNode();

                for(String s:l.getTags()){
                    if(s.equalsIgnoreCase(locationTag))
                    {
                        locations.put(locationTag,l.getName());
                        tagFound=true;
                        return;
                    }
                }



            }
            if(!tagFound){
                if(entry.getValue().getChildren()!=null) {
                    addChildToList(entry.getValue().getChildren(), locationTag);
                }
            }
        }
    }
}
