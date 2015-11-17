package org.ei.opensrp.indonesia.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import org.ei.opensrp.indonesia.view.fragment.NativeKIAnakSmartRegisterFragment;
import org.ei.opensrp.indonesia.view.pageradapter.BaseRegisterActivityPagerAdapter;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.util.FormUtils;
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
import org.ei.opensrp.view.fragment.DisplayFormFragment;
import org.ei.opensrp.view.fragment.SecuredNativeSmartRegisterFragment;
import org.ei.opensrp.view.viewpager.SampleViewPager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static org.ei.opensrp.indonesia.AllConstantsINA.FormNames.*;

/**
 * Created by Dimas Ciputra on 4/7/15.
 */
public class NativeKIAnakSmartRegisterActivity extends BidanSecuredNativeSmartRegisterActivity implements LocationSelectorDialogFragment.OnLocationSelectedListener{

    @Bind(R.id.view_pager)
    SampleViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;
    private int currentPage;

    private String[] formNames = new String[]{};
    private android.support.v4.app.Fragment mBaseFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        formNames = this.buildFormNameList();
        mBaseFragment = new NativeKIAnakSmartRegisterFragment();

        // Instantiate a ViewPager and a PagerAdapter.
        mPagerAdapter = new BaseRegisterActivityPagerAdapter(getSupportFragmentManager(), formNames, mBaseFragment);
        mPager.setOffscreenPageLimit(getEditOptions().length);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                onPageChanged(position);
            }
        });
    }

    public void onPageChanged(int page){
        setRequestedOrientation(page == 0 ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return null;
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
    protected void onStart() {
        super.onStart();
        FlurryFacade.logEvent("anak_dashboard");
    }

    @Override
    protected void startRegistration() {
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

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
        try {
            int formIndex = FormUtils.getIndexForFormName(formName, formNames) + 1; // add the offset
            if (entityId != null || metaData != null){
                String data = FormUtils.getInstance(getApplicationContext()).generateXMLInputForFormWithEntityId(entityId, formName, metaData);
                DisplayFormFragment displayFormFragment = getDisplayFormFragmentAtIndex(formIndex);
                if (displayFormFragment != null) {
                    displayFormFragment.setFormData(data);
                    displayFormFragment.loadFormData();
                    displayFormFragment.setRecordId(entityId);
                }
            }

            mPager.setCurrentItem(formIndex, false); //Don't animate the view on orientation change the view disapears

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String[] buildFormNameList(){
        List<String> formNames = new ArrayList<String>();
        //formNames.add(ANAK_NEW_REGISTRATION);

        DialogOption[] options = getEditOptions();
        for (int i = 0; i < options.length; i++){
            formNames.add(((OpenFormOption) options[i]).getFormName());
        }
        return formNames.toArray(new String[formNames.size()]);
    }

    @Override
    public void saveFormSubmission(String formSubmission, String id, String formName, Map<String, String> fieldOverrides){
        // save the form
        try{
//            FormUtils formUtils = FormUtils.getInstance(getApplicationContext());
//            FormSubmission submission = formUtils.generateFormSubmisionFromXMLString(id, formSubmission, formName, new HashMap<String, String>());
//
//            org.ei.opensrp.Context context = org.ei.opensrp.Context.getInstance();
//            ZiggyService ziggyService = context.ziggyService();
//            ziggyService.saveForm(getParams(submission), submission.instance());

            //switch to forms list fragment
            switchToBaseFragment(formSubmission); // Unnecessary!! passing on data

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
        if (currentPage != 0){
            switchToBaseFragment(null);
        }else if (currentPage == 0) {
            super.onBackPressed(); // allow back key only if we are
        }
    }
}
