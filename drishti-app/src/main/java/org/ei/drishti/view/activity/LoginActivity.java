package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.BackgroundAction;
import org.ei.drishti.view.LockingBackgroundTask;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static org.ei.drishti.util.Log.logVerbose;

public class LoginActivity extends Activity {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.login);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        fillUserIfExists();
    }

    public void login(final View view) {
        showMessage("");
        hideKeyboard();
        view.setClickable(false);

        final String userName = userNameEditText().getText().toString();
        final String password = ((EditText) findViewById(R.id.login_passwordText)).getText().toString();

        if (context.loginService().hasARegisteredUser()) {
            localLogin(userName, password);
        }
        else {
            remoteLogin(userName, password);
        }
        view.setClickable(true);
    }

    private void localLogin(String userName, String password) {
        if (context.loginService().isValidLocalLogin(userName, password)) {
            loginWith(userName, password);
        } else {
            showMessage("Local login failed. Please check the credentials.");
        }
    }

    private void remoteLogin(final String userName, final String password) {
        showMessage("Logging in using CommCare ...");
        tryRemoteLogin(userName, password, new Listener<Boolean>() {
            public void onEvent(Boolean isLoginSuccessful) {
                if (isLoginSuccessful == null) {
                    return;
                }

                if (isLoginSuccessful) {
                    loginWith(userName, password);
                } else {
                    showMessage("Remote login failed. Please check the credentials.");
                }
            }
        });
    }

    private void tryRemoteLogin(final String userName, final String password, final Listener<Boolean> afterLoginCheck) {
        LockingBackgroundTask task = new LockingBackgroundTask(((ProgressBar) findViewById(R.id.login_progressBar)));

        task.doActionInBackground(new BackgroundAction<Boolean>() {
            public Boolean actionToDoInBackgroundThread() {
                return context.loginService().isValidRemoteLogin(userName, password);
            }

            public void postExecuteInUIThread(Boolean result) {
                afterLoginCheck.onEvent(result);
            }
        });
    }

    private void fillUserIfExists() {
        if (context.loginService().hasARegisteredUser()) {
            userNameEditText().setText(context.allSettings().fetchRegisteredANM());
            userNameEditText().setEnabled(false);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), HIDE_NOT_ALWAYS);
    }

    private void loginWith(String userName, String password) {
        context.loginService().loginWith(userName, password);
        startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
        finish();
    }

    private void showMessage(String message) {
        ((TextView) findViewById(R.id.login_status)).setText(message);
    }

    private EditText userNameEditText() {
        return ((EditText) findViewById(R.id.login_userNameText));
    }
}
