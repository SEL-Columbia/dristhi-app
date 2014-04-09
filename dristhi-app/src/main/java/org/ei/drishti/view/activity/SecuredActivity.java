package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.FormController;

import static android.widget.Toast.LENGTH_SHORT;
import static org.ei.drishti.AllConstants.ENTITY_ID_PARAM;
import static org.ei.drishti.AllConstants.FORM_NAME_PARAM;
import static org.ei.drishti.event.Event.ON_LOGOUT;

public abstract class SecuredActivity extends Activity {
    protected Context context;
    protected Listener<Boolean> logoutListener;
    protected FormController formController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        logoutListener = new Listener<Boolean>() {
            public void onEvent(Boolean data) {
                finish();
            }
        };
        ON_LOGOUT.addListener(logoutListener);

        if (context.IsUserLoggedOut()) {
            startActivity(new Intent(this, LoginActivity.class));
            context.userService().logoutSession();
            return;
        }
        formController = new FormController(this);
        onCreation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (context.IsUserLoggedOut()) {
            context.userService().logoutSession();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        onResumption();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switchLanguageMenuItem: {
                String newLanguagePreference = context.userService().switchLanguagePreference();
                Toast.makeText(this, "Language preference set to " + newLanguagePreference + ". Please restart the application.", LENGTH_SHORT).show();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logoutUser() {
        context.userService().logout();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected abstract void onCreation();

    protected abstract void onResumption();

    public void startFormActivity(String formName, String entityId, String metaData) {
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        startActivity(intent);
    }

    public void startMicroFormActivity(String formName, String entityId, String metaData) {
        Intent intent = new Intent(getApplicationContext(), MicroFormActivity.class);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        startActivity(intent);
    }
}
