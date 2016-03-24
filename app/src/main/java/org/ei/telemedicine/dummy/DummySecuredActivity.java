package org.ei.telemedicine.dummy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.bluetooth.BlueToothInfoActivity;
import org.ei.telemedicine.domain.ANM;
import org.ei.telemedicine.domain.ProfileImage;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.domain.form.SubForm;
import org.ei.telemedicine.event.CapturedPhotoInformation;
import org.ei.telemedicine.event.Event;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.repository.ImageRepository;
import org.ei.telemedicine.sync.DrishtiSyncScheduler;
import org.ei.telemedicine.view.activity.LoginActivity;
import org.ei.telemedicine.view.contract.HomeContext;
import org.ei.telemedicine.view.controller.ANMController;
import org.ei.telemedicine.view.controller.FormController;
import org.ei.telemedicine.view.controller.NavigationController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

import static org.ei.telemedicine.AllConstants.*;
import static org.ei.telemedicine.AllConstants.ALERT_NAME_PARAM;
import static org.ei.telemedicine.AllConstants.ENTITY_ID;
import static org.ei.telemedicine.AllConstants.ENTITY_ID_PARAM;
import static org.ei.telemedicine.AllConstants.FIELD_OVERRIDES_PARAM;
import static org.ei.telemedicine.AllConstants.FORM_NAME_PARAM;
import static org.ei.telemedicine.AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_INVESTIGATIONS;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_ILLNESS;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_VISIT;
import static org.ei.telemedicine.AllConstants.VIEW_FORM;
import static org.ei.telemedicine.R.string.no_button_label;
import static org.ei.telemedicine.R.string.yes_button_label;
import static org.ei.telemedicine.event.Event.ON_LOGOUT;
import static org.ei.telemedicine.util.Log.logError;
import static org.ei.telemedicine.util.Log.logInfo;

public abstract class DummySecuredActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreation();
    }

    @Override
    public void onResume() {
        super.onResume();

        onResumption();
    }

    public abstract void onCreation();

    public abstract void onResumption();


}
