package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.repository.AllDoctorRepository;

import org.ei.telemedicine.sync.SyncAfterFetchListener;
import org.ei.telemedicine.sync.SyncProgressIndicator;
import org.ei.telemedicine.sync.UpdateActionsTask;
import org.ei.telemedicine.view.contract.HomeContext;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.NativeAfterANMDetailsFetchListener;
import org.ei.telemedicine.view.controller.NativeUpdateANMDetailsTask;
import org.ei.telemedicine.view.controller.VillageController;
import org.ei.telemedicine.view.customControls.CustomFontTextView;
import org.ei.telemedicine.view.dialog.AllClientsFilter;
import org.ei.telemedicine.view.dialog.DialogOption;
import org.ei.telemedicine.view.dialog.DialogOptionMapper;
import org.ei.telemedicine.view.dialog.DialogOptionModel;
import org.ei.telemedicine.view.dialog.EditOption;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.OutOfAreaFilter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.*;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.visit_type;
import static org.ei.telemedicine.event.Event.ACTION_HANDLED;
import static org.ei.telemedicine.event.Event.SYNC_COMPLETED;
import static org.ei.telemedicine.event.Event.SYNC_STARTED;

/**
 * Created by naveen on 5/20/15.
 */

public class NativeDoctorActivity extends Activity implements View.OnClickListener {
    ListView lv_pending_consultants;
    ImageButton ib_start_sync_doc_data, ib_clear_search, ib_filter_selection, ib_sort_selection;
    ProgressBar sync_progressBar;
    ImageView iv_profile;
    EditText edt_search;
    TextView tv_anc_count, tv_pnc_count, tv_child_count;
    int anc_count, pnc_count, child_count;
    AllDoctorRepository allDoctorRepository;
    private Context context;
    JSONObject formData = new JSONObject();
    static PendingConsultantBaseAdapter pendingConsultantBaseAdapter, syncAdapter;
    List<DoctorData> doctorDatas;
    static ArrayList<DoctorData> doctorDataArrayList;
    private String TAG = "NativeDoctorActivity";


    Dialog popup_dialog;
    Object obj;
    private MenuItem updateMenuItem;
    private static final String DIALOG_TAG = "dialog";
    ArrayList<String> tempList;
    static String sort_by = "", village_name = "All";
    public static org.ei.telemedicine.view.customControls.CustomFontTextView sorted_by_name, village_name_view;

    private Listener<Boolean> onSyncStartListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            if (ib_start_sync_doc_data != null && sync_progressBar != null) {
                sync_progressBar.setVisibility(View.VISIBLE);
                ib_start_sync_doc_data.setVisibility(View.INVISIBLE);
            }
        }
    };
    private Listener<Boolean> onSyncCompleteListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            if (ib_start_sync_doc_data != null && sync_progressBar != null) {
                sync_progressBar.setVisibility(View.INVISIBLE);
                ib_start_sync_doc_data.setVisibility(View.VISIBLE);
            }
            updateRegisterCounts();
        }
    };

    private void updateRegisterCounts() {
        ArrayList<DoctorData> _doctorDataArrayList = new ArrayList<DoctorData>();
        _doctorDataArrayList.addAll(allDoctorRepository.getAllConsultants());

        if (allDoctorRepository != null && tv_anc_count != null && tv_pnc_count != null && tv_child_count != null && lv_pending_consultants != null) {
            anc_count = 0;
            pnc_count = 0;
            child_count = 0;
            if (_doctorDataArrayList.size() != 0) {
                for (DoctorData doctorDataInfo : _doctorDataArrayList) {
                    if (doctorDataInfo.getVisitType().equalsIgnoreCase("ANC"))
                        anc_count = anc_count + 1;
                    else if (doctorDataInfo.getVisitType().equalsIgnoreCase("PNC"))
                        pnc_count = pnc_count + 1;
                    else
                        child_count = child_count + 1;
                }
//                tv_anc_count.setText(anc_count + "");
                tv_pnc_count.setText(pnc_count + "");
                tv_child_count.setText(child_count + "");
            }
        }
        tv_anc_count.setText(allDoctorRepository.getCount(DoctorFormDataConstants.ancvisit) + "");
        syncAdapter = new PendingConsultantBaseAdapter(NativeDoctorActivity.this, _doctorDataArrayList);
        syncAdapter.notifyDataSetChanged();
        lv_pending_consultants.setAdapter(syncAdapter);

    }

    private Listener<String> updateANMDetailsListener = new Listener<String>() {
        @Override
        public void onEvent(String data) {
            updateRegisterCounts();
            Toast.makeText(NativeDoctorActivity.this, "Completed", Toast.LENGTH_SHORT).show();
        }
    };

    private void setupViews() {
        village_name_view = (CustomFontTextView) findViewById(R.id.village);
        sorted_by_name = (CustomFontTextView) findViewById(R.id.sorted_by);

        lv_pending_consultants = (ListView) findViewById(R.id.consultants_list);
        ib_start_sync_doc_data = (ImageButton) findViewById(R.id.start_sync_doc_data);
        sync_progressBar = (ProgressBar) findViewById(R.id.doc_client_list_progress);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_anc_count = (TextView) findViewById(R.id.anc_count);
        tv_pnc_count = (TextView) findViewById(R.id.pnc_count);
        tv_child_count = (TextView) findViewById(R.id.child_count);
        edt_search = (EditText) findViewById(R.id.edt_doc_search);

        ib_clear_search = (ImageButton) findViewById(R.id.ib_doc_search_cancel);
        ib_filter_selection = (ImageButton) findViewById(R.id.ib_filter_selection);
        ib_sort_selection = (ImageButton) findViewById(R.id.ib_sort_selection);

        ib_start_sync_doc_data.setOnClickListener(this);
        ib_filter_selection.setOnClickListener(this);
        ib_sort_selection.setOnClickListener(this);
        ib_clear_search.setOnClickListener(this);
    }

    private void initalize() {
        SYNC_STARTED.addListener(onSyncStartListener);
        SYNC_COMPLETED.addListener(onSyncCompleteListener);
        ACTION_HANDLED.addListener(updateANMDetailsListener);
        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
        allDoctorRepository = context.allDoctorRepository();
    }

    public void updateFromServer() {
        allDoctorRepository.clearDataNoPoc();
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(
                this, context.actionService(), context.formSubmissionSyncService(), new SyncProgressIndicator());
        updateActionsTask.updateFromServer(new SyncAfterFetchListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home_screen);
        setupViews();
        initalize();

        doctorDataArrayList = new ArrayList<DoctorData>();
        tempList = new ArrayList<String>();


        doctorDatas = allDoctorRepository.getAllConsultants();
        doctorDataArrayList.addAll(doctorDatas);

        updateRegisterCounts();
        pendingConsultantBaseAdapter = new PendingConsultantBaseAdapter(NativeDoctorActivity.this, doctorDataArrayList);
        lv_pending_consultants.setAdapter(pendingConsultantBaseAdapter);


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ib_clear_search.setVisibility(View.VISIBLE);
                String text = edt_search.getText().toString().toLowerCase(Locale.getDefault());
                if (syncAdapter != null)
                    NativeDoctorActivity.syncAdapter.filter(text);
                else
                    NativeDoctorActivity.pendingConsultantBaseAdapter.filter(text);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SYNC_STARTED.removeListener(onSyncStartListener);
        SYNC_COMPLETED.removeListener(onSyncCompleteListener);
        ACTION_HANDLED.removeListener(updateANMDetailsListener);
    }

//    public void getData(final Listener<String> afterResult) {
//        new AsyncTask<Void, Void, String>() {
//
//            @Override
//            protected String doInBackground(Void... params) {
//                org.ei.telemedicine.Context context = org.ei.telemedicine.Context.getInstance();
//                String url = context.configuration().dristhiDoctorBaseURL() + "/docname=" + context.allSharedPreferences().fetchRegisteredANM() + "&pwd=" + context.allSharedPreferences().getPwd();
//                Log.e(TAG, "Url " + url);
//                String result = context.userService().gettingFromRemoteURL(url);
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(String resultData) {
//                super.onPostExecute(resultData);
//                afterResult.onEvent(resultData);
//            }
//        }.execute();
//
//    }
//
//    public String getData(String key, JSONObject formData) {
//        if (formData != null) try {
//            return (formData != null && formData.has(key) && formData.getString(key) != null) ? formData.getString(key) : "";
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void showFragmentDialog(ArrayList<String> arrayList, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DoctorSmartRegisterDialogFragment.newInstance(this, arrayList, tag)
                .show(ft, DIALOG_TAG);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_doc_search_cancel:
                edt_search.setText("");
                ib_clear_search.setVisibility(View.INVISIBLE);
                break;
            case R.id.ib_filter_selection:

                ArrayList<String> villages_with_all = new ArrayList<String>();
                villages_with_all.add("All");
                villages_with_all.addAll(Context.getInstance().allDoctorRepository().getVillages());
                showFragmentDialog(villages_with_all, "Filter");
                break;
            case R.id.ib_sort_selection:
                ArrayList<String> sortingList = new ArrayList<String>();
                sortingList.add(sorting_name);
                sortingList.add(sorting_anc);
                sortingList.add(sorting_pnc);
                sortingList.add(sorting_child);
                sortingList.add(sorting_hr);

                showFragmentDialog(sortingList, "Sort");
                break;
            case R.id.start_sync_doc_data:

                updateFromServer();
                break;
        }
    }
}