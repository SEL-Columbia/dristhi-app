package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import org.ei.drishti.Context;
import org.ei.drishti.event.Listener;

import static org.ei.drishti.event.Event.ON_LOGOUT;

public abstract class SecuredActivity extends Activity {
    protected Context context;
    protected Listener<Boolean> logoutListener;

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

        if (context.loginService().hasSessionExpired()) {
            startActivity(new Intent(this, LoginActivity.class));
            context.loginService().logoutSession();
            return;
        }

        onCreation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (context.loginService().hasSessionExpired()) {
            startActivity(new Intent(this, LoginActivity.class));
            context.loginService().logoutSession();
            return;
        }

        onResumption();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case org.ei.drishti.R.id.logoutMenuItem:
                context.loginService().logout();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void onCreation();

    protected abstract void onResumption();
}
