package org.ei.opensrp.gizi.gizi;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.gizi.LoginActivity;
import org.ei.opensrp.gizi.fragment.GiziSmartRegisterFragment;
import org.ei.opensrp.gizi.pageradapter.BaseRegisterActivityPagerAdapter;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.ZiggyService;
import org.ei.opensrp.gizi.LoginActivity;
import org.ei.opensrp.gizi.R;
import org.ei.opensrp.gizi.fragment.GiziSmartRegisterFragment;
import org.ei.opensrp.gizi.pageradapter.BaseRegisterActivityPagerAdapter;
import org.ei.opensrp.util.FormUtils;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.OpenFormOption;
import org.ei.opensrp.view.fragment.DisplayFormFragment;
import org.ei.opensrp.view.fragment.SecuredNativeSmartRegisterFragment;
import org.ei.opensrp.view.viewpager.OpenSRPViewPager;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//import org.ei.opensrp.gizi.fragment.HouseHoldSmartRegisterFragment;

public class GiziSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    public static final String TAG = "GiziActivity";
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

        formNames = this.buildFormNameList();
        mBaseFragment = new GiziSmartRegisterFragment();

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
    protected void setupViews() {


    }

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
                new OpenFormOption("Kunjungan Umur 1 Bulan ", "kunjungan_gizi", formController),
                    new OpenFormOption("Kunjungan Umur 2 Bulan ", "kunjungan_bulan2", formController),
                    new OpenFormOption("Kunjungan Umur 3 Bulan ", "kunjungan_bulan3", formController),
                    new OpenFormOption("Kunjungan Umur 4 Bulan ", "kunjungan_bulan4", formController),
                    new OpenFormOption("Kunjungan Umur 5 Bulan ", "kunjungan_bulan5", formController),
                    new OpenFormOption("Kunjungan Umur 6 Bulan ", "kunjungan_bulan6", formController),
                    new OpenFormOption("Kunjungan Umur 7 Bulan ", "kunjungan_bulan7", formController),
                    new OpenFormOption("Kunjungan Umur 8 Bulan ", "kunjungan_bulan8", formController),
                    new OpenFormOption("Kunjungan Umur 9 Bulan ", "kunjungan_bulan9", formController),
                    new OpenFormOption("Kunjungan Umur 10 Bulan ", "kunjungan_bulan10", formController),
                    new OpenFormOption("Kunjungan Umur 11 Bulan ", "kunjungan_bulan11", formController),
                    new OpenFormOption("Kunjungan Umur 12 Bulan ", "kunjungan_bulan12", formController)


            };


    }

    /*
    private String getalertstateforcensus(CommonPersonObjectClient pc) {
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
    }

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
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
                    displayFormFragment.loadFormData();
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
                    displayFormFragment.loadFormData();
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
       formNames.add("registrasi_gizi");
        formNames.add("kunjungan_gizi");
        formNames.add("kunjungan_bulan2");
        formNames.add("kunjungan_bulan3");
        formNames.add("kunjungan_bulan4");
        formNames.add("kunjungan_bulan5");
        formNames.add("kunjungan_bulan6");
        formNames.add("kunjungan_bulan7");
        formNames.add("kunjungan_bulan8");
        formNames.add("kunjungan_bulan9");
        formNames.add("kunjungan_bulan10");
        formNames.add("kunjungan_bulan11");
        formNames.add("kunjungan_bulan12");


     //   formNames.add("census_enrollment_form");
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
