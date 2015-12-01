package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import org.ei.telemedicine.view.activity.LoginActivity;
import org.ei.telemedicine.view.customControls.CustomFontTextView;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
    ImageButton ib_start_sync_doc_data, ib_clear_search, ib_filter_selection, ib_sort_selection, ib_logout;
    ProgressBar sync_progressBar;
    ImageView iv_profile;
    EditText edt_search;
    TextView tv_anc_count, tv_pnc_count, tv_child_count;
    int anc_count, pnc_count, child_count;
    AllDoctorRepository allDoctorRepository;
    private Context context;

    PendingConsultantBaseAdapter adapter;

    List<DoctorData> doctorDatas;
    static ArrayList<DoctorData> doctorDataArrayList;
    private String TAG = "NativeDoctorActivity";


    Dialog popup_dialog;
    Object obj;
    private MenuItem updateMenuItem;
    private static final String DIALOG_TAG = "dialog";
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
            updateInfo(adapter);
        }
    };
    private Listener<String> updateANMDetailsListener = new Listener<String>() {
        @Override
        public void onEvent(String data) {
            updateInfo(adapter);
            Toast.makeText(NativeDoctorActivity.this, "Completed", Toast.LENGTH_SHORT).show();
        }
    };

    private ArrayList<DoctorData> updateRegisterCounts() {
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
        return _doctorDataArrayList;
    }

    public void updateInfo(PendingConsultantBaseAdapter pendingConsultantBaseAdapter) {
        ArrayList<DoctorData> doctorDatas = updateRegisterCounts();
        if (pendingConsultantBaseAdapter == null)
            pendingConsultantBaseAdapter = new PendingConsultantBaseAdapter(NativeDoctorActivity.this, doctorDatas, this);
        pendingConsultantBaseAdapter.notifyDataSetChanged(doctorDatas);
    }


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
        ib_logout = (ImageButton) findViewById(R.id.ib_logout);

        ib_start_sync_doc_data.setOnClickListener(this);
        ib_filter_selection.setOnClickListener(this);
        ib_sort_selection.setOnClickListener(this);
        ib_clear_search.setOnClickListener(this);
        ib_logout.setOnClickListener(this);
    }

    private void initalize() {
        SYNC_STARTED.addListener(onSyncStartListener);
        SYNC_COMPLETED.addListener(onSyncCompleteListener);
        ACTION_HANDLED.addListener(updateANMDetailsListener);
        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
        allDoctorRepository = context.allDoctorRepository();
    }

    public void updateFromServer() {
//        allDoctorRepository.clearDataNoPoc();
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(
                this, context.actionService(), context.formSubmissionSyncService(), new SyncProgressIndicator());
        updateActionsTask.updateFromServer(new SyncAfterFetchListener(),"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null && lv_pending_consultants != null)
            updateInfo(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home_screen);
//        _context = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        setupViews();
        initalize();

//        updateRegisterCounts();
        adapter = new PendingConsultantBaseAdapter(NativeDoctorActivity.this, updateRegisterCounts(), this);
        lv_pending_consultants.setAdapter(adapter);
        updateInfo(adapter);

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
                adapter.notifyDataSetChanged(adapter.filter(text, updateRegisterCounts()));

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

    public void showFragmentDialog(ArrayList<String> arrayList, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DoctorSmartRegisterDialogFragment.newInstance(this, arrayList, tag, adapter, updateRegisterCounts())
                .show(ft, DIALOG_TAG);
    }

    public void logoutUser() {
        context.userService().logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    private void backup() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String SAMPLE_DB_NAME = "drishti.db";
        String currentDBPath = "/data/" + "org.ei.telemedicine" + "/databases/" + SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Comple", Toast.LENGTH_SHORT).show();

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
            case R.id.ib_logout:
                new AlertDialog.Builder(this).setTitle("Do you want logout?").setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutUser();
//                        backup();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
        }
    }
}