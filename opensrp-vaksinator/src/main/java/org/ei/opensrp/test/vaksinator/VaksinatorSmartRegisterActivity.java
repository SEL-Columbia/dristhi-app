package org.ei.opensrp.test.vaksinator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flurry.android.FlurryAgent;

import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.ZiggyService;
import org.ei.opensrp.test.LoginActivity;
import org.ei.opensrp.test.R;
import org.ei.opensrp.test.fragment.VaksinatorSmartRegisterFragment;
import org.ei.opensrp.test.pageradapter.BaseRegisterActivityPagerAdapter;
import org.ei.opensrp.util.FormUtils;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.LocationSelectorDialogFragment;
import org.ei.opensrp.view.dialog.OpenFormOption;
import org.ei.opensrp.view.fragment.DisplayFormFragment;
import org.ei.opensrp.view.fragment.SecuredNativeSmartRegisterFragment;
import org.ei.opensrp.view.viewpager.OpenSRPViewPager;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

//import org.ei.opensrp.test.fragment.HouseHoldSmartRegisterFragment;

public class VaksinatorSmartRegisterActivity extends SecuredNativeSmartRegisterActivity implements
        LocationSelectorDialogFragment.OnLocationSelectedListener{
    SimpleDateFormat timer = new SimpleDateFormat("hh:mm:ss");

    public static final String TAG = "Vaksinator";
    @Bind(R.id.view_pager)
    OpenSRPViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;
    private int currentPage;

    private String[] formNames = new String[]{};
    private android.support.v4.app.Fragment mBaseFragment = null;


    ZiggyService ziggyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String VakStart = timer.format(new Date());
        Map<String, String> Vaksinator = new HashMap<String, String>();
        Vaksinator.put("start", VakStart);
        FlurryAgent.logEvent("vaksinator_dashboard",Vaksinator, true );

       // FlurryFacade.logEvent("anc_dashboard");
        formNames = this.buildFormNameList();
        mBaseFragment = new VaksinatorSmartRegisterFragment();

        // Instantiate a ViewPager and a PagerAdapter.
        mPagerAdapter = new BaseRegisterActivityPagerAdapter(getSupportFragmentManager(), formNames, mBaseFragment);
        mPager.setOffscreenPageLimit(formNames.length);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                onPageChanged(position);
            }
        });

        ziggyService = context.ziggyService();
    }
    public void onPageChanged(int page){
        setRequestedOrientation(page == 0 ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LoginActivity.setLanguage();
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {return null;}

    @Override
    protected void setupViews() {}

    @Override
    protected void onResumption(){}

    @Override
    protected NavBarOptionsProvider getNavBarOptionsProvider() {return null;}

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {return null;}

    @Override
    protected void onInitialization() {}

    @Override
    public void startRegistration() {
    }

    public DialogOption[] getEditOptions() {
            return new DialogOption[]{
                new OpenFormOption(context.getStringResource(R.string.editForm), "vaksinator_edit", formController),
                new OpenFormOption(context.getStringResource(R.string.hb0VisitLabel), "hb0_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.bcgVisitLabel), "bcg_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.polio1VisitLabel), "polio1_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.dpt1VisitLabel), "hb1_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.polio2VisitLabel), "polio2_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.dpt2VisitLabel), "dpt_hb2_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.polio3VisitLabel), "polio3_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.dpt3VisitLabel), "hb3_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.polio4VisitLabel), "polio4_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.ipvVisitLabel), "ipv_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.campakVisitLabel), "campak_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.dptTambahanVisitLabel), "dpthb_lanjutan_visit", formController),
                new OpenFormOption(context.getStringResource(R.string.campakTambahanVisitLabel), "campak_lanjutan_visit", formController),
                new OpenFormOption("Close Form", "close_form", formController),
        };
    }


    //alert not needed for now
  /*  private String getalertstateforcensus(CommonPersonObjectClient pc) {
        try {
            List<Alert> alertlist_for_client = Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "FW CENSUS");
            String alertstate = "";
            if (alertlist_for_client.size() == 0) {

            } else {
                for (int i = 0; i < alertlist_for_client.size(); i++) {
//           psrfdue.setText(alertlist_for_client.get(i).expiryDate());
                    Log.v("printing alertlist", alertlist_for_client.get(i).status().value());
                    alertstate = alertlist_for_client.get(i).status().value();

                }
            }
            return alertstate;
        }catch (Exception e){
            return "";
        }
    }
    */
    @Override
    public void saveFormSubmission(String formSubmission, String id, String formName, JSONObject fieldOverrides){
        Log.v("fieldoverride", fieldOverrides.toString());
        // save the form
        try{
            FormUtils formUtils = FormUtils.getInstance(getApplicationContext());
            FormSubmission submission = formUtils.generateFormSubmisionFromXMLString(id, formSubmission, formName, fieldOverrides);

            ziggyService.saveForm(getParams(submission), submission.instance());

            //switch to forms list fragment
            switchToBaseFragment(formSubmission); // Unnecessary!! passing on data

        }catch (Exception e){
            // TODO: show error dialog on the formfragment if the submission fails
            DisplayFormFragment displayFormFragment = getDisplayFormFragmentAtIndex(currentPage);
            if (displayFormFragment != null) {
                displayFormFragment.hideTranslucentProgressDialog();
            }
            e.printStackTrace();
        }
        if(formName.equals("registrasi_jurim")){
            saveuniqueid();
        }
        //end capture flurry log for FS
                String end = timer.format(new Date());
                Map<String, String> FS = new HashMap<String, String>();
                FS.put("end", end);
                FlurryAgent.logEvent(formName,FS, true);
    }

    @Override
    public void OnLocationSelected(String locationJSONString) {
        JSONObject combined = null;

        try {
            JSONObject locationJSON = new JSONObject(locationJSONString);
            JSONObject uniqueId = new JSONObject(context.uniqueIdController().getUniqueIdJson());

            combined = locationJSON;
            Iterator<String> iter = uniqueId.keys();

            while (iter.hasNext()) {
                String key = iter.next();
                combined.put(key, uniqueId.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (combined != null) {
            FieldOverrides fieldOverrides = new FieldOverrides(combined.toString());
            startFormActivity("registrasi_jurim", null, fieldOverrides.getJSONString());
        }
    }
    public void saveuniqueid() {
        try {
            JSONObject uniqueId = new JSONObject(context.uniqueIdController().getUniqueIdJson());
            String uniq = uniqueId.getString("unique_id");
            context.uniqueIdController().updateCurrentUniqueId(uniq);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
        //Start capture flurry log for FS
               String start = timer.format(new Date());
                Map<String, String> FS = new HashMap<String, String>();
                FS.put("start", start);
                FlurryAgent.logEvent(formName,FS, true );
     //   FlurryFacade.logEvent(formName);
       // Log.v("fieldoverride", metaData);
        try {
            int formIndex = FormUtils.getIndexForFormName(formName, formNames) + 1; // add the offset
            if (entityId != null || metaData != null){
                String data = null;
                //check if there is previously saved data for the form
                data = getPreviouslySavedDataForForm(formName, metaData, entityId);
                if (data == null){
                    data = FormUtils.getInstance(getApplicationContext()).generateXMLInputForFormWithEntityId(entityId, formName, metaData);
                }

                DisplayFormFragment displayFormFragment = getDisplayFormFragmentAtIndex(formIndex);
                if (displayFormFragment != null) {
                    displayFormFragment.setFormData(data);
                    displayFormFragment.setRecordId(entityId);
                    displayFormFragment.setFieldOverides(metaData);
                }
            }

            mPager.setCurrentItem(formIndex, false); //Don't animate the view on orientation change the view disapears

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void switchToBaseFragment(final String data){
        final int prevPageIndex = currentPage;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(0, false);
                SecuredNativeSmartRegisterFragment registerFragment = (SecuredNativeSmartRegisterFragment) findFragmentByPosition(0);
                if (registerFragment != null && data != null) {
                    registerFragment.refreshListView();
                }

                //hack reset the form
                DisplayFormFragment displayFormFragment = getDisplayFormFragmentAtIndex(prevPageIndex);
                if (displayFormFragment != null) {
                    displayFormFragment.hideTranslucentProgressDialog();
                    displayFormFragment.setFormData(null);
                }

                displayFormFragment.setRecordId(null);
            }
        });

    }

    public android.support.v4.app.Fragment findFragmentByPosition(int position) {
        FragmentPagerAdapter fragmentPagerAdapter = mPagerAdapter;
        return getSupportFragmentManager().findFragmentByTag("android:switcher:" + mPager.getId() + ":" + fragmentPagerAdapter.getItemId(position));
    }

    public DisplayFormFragment getDisplayFormFragmentAtIndex(int index) {
        return  (DisplayFormFragment)findFragmentByPosition(index);
    }

    @Override
    public void onBackPressed() {
        if (currentPage != 0) {
            switchToBaseFragment(null);
        } else if (currentPage == 0) {
            super.onBackPressed(); // allow back key only if we are
        }
    }

    private String[] buildFormNameList(){
        List<String> formNames = new ArrayList<String>();
        formNames.add("registrasi_jurim");
    //   formNames.add("jurim_visit");
        formNames.add("vaksinator_edit");
        formNames.add("hb0_visit");
        formNames.add("bcg_visit");
        formNames.add("polio1_visit");
        formNames.add("hb1_visit");
        formNames.add("polio2_visit");
        formNames.add("dpt_hb2_visit");
        formNames.add("polio3_visit");
        formNames.add("hb3_visit");
        formNames.add("polio4_visit");
        formNames.add("ipv_visit");
        formNames.add("campak_visit");
        formNames.add("dpthb_lanjutan_visit");
        formNames.add("campak_lanjutan_visit");
        formNames.add("close_form");
//        DialogOption[] options = getEditOptions();
//        for (int i = 0; i < options.length; i++){
//            formNames.add(((OpenFormOption) options[i]).getFormName());
//        }
        return formNames.toArray(new String[formNames.size()]);
    }

    @Override
    protected void onPause() {
        super.onPause();
        retrieveAndSaveUnsubmittedFormData();
        String VakEnd = timer.format(new Date());
        Map<String, String> Vaksinator = new HashMap<String, String>();
        Vaksinator.put("end", VakEnd);
        FlurryAgent.logEvent("vaksinator_dashboard",Vaksinator, true );
    }

    public void retrieveAndSaveUnsubmittedFormData(){
        if (currentActivityIsShowingForm()){
            DisplayFormFragment formFragment = getDisplayFormFragmentAtIndex(currentPage);
            formFragment.saveCurrentFormData();
        }
    }

    private boolean currentActivityIsShowingForm(){
        return currentPage != 0;
    }
}
