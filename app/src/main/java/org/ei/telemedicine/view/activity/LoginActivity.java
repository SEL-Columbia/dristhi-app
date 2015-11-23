package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.doctor.NativeDoctorActivity;
import org.ei.telemedicine.domain.LoginResponse;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.sync.DrishtiCallScheduler;
import org.ei.telemedicine.sync.DrishtiSyncScheduler;
import org.ei.telemedicine.view.BackgroundAction;
import org.ei.telemedicine.view.LockingBackgroundTask;
import org.ei.telemedicine.view.ProgressIndicator;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.json.JSONObject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;




import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static org.ei.telemedicine.domain.LoginResponse.SUCCESS;
import static org.ei.telemedicine.util.Log.logError;
import static org.ei.telemedicine.util.Log.logVerbose;

public class LoginActivity extends Activity {
    private Context context;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private final String CALLER = "name";

    private ProgressDialog progressDialog;
    private String TAG = "LoginActivity";

    private int waitTime = 5;
    private final WebSocketConnection mConnection = new WebSocketConnection();

    public String getUsern()
    {
        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
        return context.allSharedPreferences().fetchRegisteredANM();
    }

    private void start() {


        final String wsuri = "ws://202.153.34.166:8004/wslogin?id=%s";
        try {
            mConnection.connect(String.format(wsuri,getUsern()), new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + wsuri);
                    Toast.makeText(getApplicationContext(),String.format(wsuri,getUsern()), Toast.LENGTH_SHORT).show();
                    //mConnection.sendTextMessage("Hello, world!");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
                    try {
                        JSONObject jObject = new JSONObject(payload);
                        String status = jObject.getString("status");
                        String msg = jObject.getString("msg_type");
                        String caller = jObject.getString("caller");
                        //Log.d(TAG, check);
                        String match = "INI";
                        boolean response = (status.equals(match))? true : false;
                        if (response)
                        {
                            Toast.makeText(getApplicationContext(),"call started",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), ActionActivity.class);
                            i.putExtra(CALLER, caller);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }

                    }catch (Exception ex)
                    {
                        Log.d(TAG, ex.toString());
                    }
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost.");
                    Toast.makeText(getApplicationContext(),"closed",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(waitTime);
                        waitTime = waitTime*2;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    start();

                }
            });
        } catch (WebSocketException e) {

            Log.d(TAG, e.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.login);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
        initializeLoginFields();
        initializeBuildDetails();
        setDoneActionHandlerOnPasswordField();
        initializeProgressDialog();

    }

    private void initializeBuildDetails() {
        TextView buildDetailsTextView = (TextView) findViewById(R.id.login_build);
        try {
            buildDetailsTextView.setText("Version " + getVersion() + ", Built on: " + getBuildDate());
        } catch (Exception e) {
            logError("Error fetching build details: " + e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!context.IsUserLoggedOut()) {
            goToHome(context.userService().getUserRole());
        }

        fillUserIfExists();
    }

    public void login(final View view) {
        hideKeyboard();
        view.setClickable(false);

        final String userName = userNameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString();

        if (context.userService().hasARegisteredUser()) {
            localLogin(view, userName, password);
        } else {
            remoteLogin(view, userName, password);
        }
    }

    private void initializeLoginFields() {
        userNameEditText = ((EditText) findViewById(R.id.login_userNameText));
        passwordEditText = ((EditText) findViewById(R.id.login_passwordText));
    }

    private void setDoneActionHandlerOnPasswordField() {
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(findViewById(R.id.login_loginButton));
                }
                return false;
            }
        });
    }

    private void initializeProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.loggin_in_dialog_title));
        progressDialog.setMessage(getString(R.string.loggin_in_dialog_message));
    }

    private void localLogin(View view, String userName, String password) {
        if (context.userService().isValidLocalLogin(userName, password)) {
            localLoginWith(userName, password);

        } else {
            showErrorDialog(getString(R.string.login_failed_dialog_message));
            view.setClickable(true);
        }
    }

    private void remoteLogin(final View view, final String userName, final String password) {
        tryRemoteLogin(userName, password, new Listener<LoginResponse>() {
            public void onEvent(LoginResponse loginResponse) {
                if (loginResponse == SUCCESS) {
                    remoteLoginWith(userName, password, loginResponse.payload());

                } else {
                    if (loginResponse == null) {
                        showErrorDialog("Login failed. Unknown reason. Try Again");
                    } else {
                        showErrorDialog(loginResponse.message());
                    }
                    view.setClickable(true);
                }
            }
        });
    }

    private void showErrorDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.login_failed_dialog_title))
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create();
        dialog.show();
    }

    private void tryRemoteLogin(final String userName, final String password, final Listener<LoginResponse> afterLoginCheck) {
        LockingBackgroundTask task = new LockingBackgroundTask(new ProgressIndicator() {
            @Override
            public void setVisible() {
                if (progressDialog != null)
                    progressDialog.show();
            }

            @Override
            public void setInvisible() {
                progressDialog.dismiss();
            }
        });

        task.doActionInBackground(new BackgroundAction<LoginResponse>() {
            public LoginResponse actionToDoInBackgroundThread() {
                LoginResponse loginResponse = context.userService().isValidRemoteLogin(userName, password);
//                Log.e(TAG, "Payload Data on Login Activity" + loginResponse.payload() != null ? loginResponse.payload() : "No Response");
                return loginResponse;
            }

            public void postExecuteInUIThread(LoginResponse result) {
                afterLoginCheck.onEvent(result);
            }
        });
    }


    private void fillUserIfExists() {
        if (context.userService().hasARegisteredUser()) {
            userNameEditText.setText(context.allSharedPreferences().fetchRegisteredANM());
            userNameEditText.setEnabled(false);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), HIDE_NOT_ALWAYS);
    }

    private void localLoginWith(String userName, String password) {
        context.userService().localLogin(userName, password);
        context.allSharedPreferences().updateIsFirstLogin(false);
        String userRole = context.userService().getUserRole();
        goToHome(userRole);
        start();
        DrishtiSyncScheduler.startOnlyIfConnectedToNetwork(getApplicationContext(), userRole);
        //DrishtiCallScheduler.startOnlyIfConnectedToNetwork(getApplicationContext());
    }


    private String getFromJson(String jsonStr, String keyValue) {
        try {
            JSONObject jsonData = new JSONObject(jsonStr);
            if (jsonData != null) {
                return jsonData.has(keyValue) ? jsonData.getString(keyValue) : "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void remoteLoginWith(String userName, String password, String loginResponse) {
        String userRole = null, personalInfo = null, location = null, drugs = null, configuration = null, countryCode = null, formFields = null;
        if (loginResponse != null) {
            context.allSharedPreferences().updateIsFirstLogin(true);
            userRole = getFromJson(loginResponse, AllConstants.ROLE);
            personalInfo = getFromJson(loginResponse, AllConstants.PERSONAL_INFO);
            location = getFromJson(personalInfo, "location");
            drugs = getFromJson(personalInfo, "drugs");
            configuration = getFromJson(personalInfo, "configuration");
            countryCode = getFromJson(personalInfo, "countryCode");
            formFields = getFromJson(personalInfo, "formLabels");
            context.userService()
                    .remoteLogin(userName, password, userRole, location, drugs, configuration, countryCode, formFields);
        }
        if (userRole != null && userRole.equals(AllConstants.DOCTOR_ROLE)) {
            context.allSharedPreferences().savePwd(password);
        }
        goToHome(userRole);
        start();
        DrishtiSyncScheduler.startOnlyIfConnectedToNetwork(getApplicationContext(), userRole);

        //DrishtiCallScheduler.startOnlyIfConnectedToNetwork(getApplicationContext());
    }

    private void goToHome(String userRole) {
        Intent intent = new Intent(this, (userRole.equals(AllConstants.ANM_ROLE)) ? NativeHomeActivity.class : NativeDoctorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private String getVersion() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        return packageInfo.versionName;
    }

    private String getBuildDate() throws PackageManager.NameNotFoundException, IOException {
        ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), 0);
        ZipFile zf = new ZipFile(applicationInfo.sourceDir);
        ZipEntry ze = zf.getEntry("classes.dex");
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new java.util.Date(ze.getTime()));
    }
}
