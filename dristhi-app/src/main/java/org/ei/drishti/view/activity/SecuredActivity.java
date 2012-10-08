package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.ei.drishti.Context;
import org.ei.drishti.R;
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

        if (context.userService().hasSessionExpired()) {
            startActivity(new Intent(this, LoginActivity.class));
            context.userService().logoutSession();
            return;
        }

        onCreation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (context.userService().hasSessionExpired()) {
            context.userService().logoutSession();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        onResumption();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case org.ei.drishti.R.id.logoutMenuItem:
                context.userService().logout();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected abstract void onCreation();

    protected abstract void onResumption();
}
