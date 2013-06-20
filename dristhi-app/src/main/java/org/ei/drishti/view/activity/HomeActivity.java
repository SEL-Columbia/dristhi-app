package org.ei.drishti.view.activity;

import android.view.Menu;
import android.view.MenuItem;
import org.ei.drishti.R;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.HomeController;

import static org.ei.drishti.event.Event.*;

public class HomeActivity extends SecuredWebActivity {
    private MenuItem updateMenuItem;

    private Listener<Boolean> onSyncStartListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            updateMenuItem.setActionView(R.layout.progress);
        }
    };

    private Listener<Boolean> onSyncCompleteListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            updateMenuItem.setActionView(null);
        }
    };

    private Listener<String> onFormSubmittedListener = new Listener<String>() {
        @Override
        public void onEvent(String instanceId) {
            updateController.updateANMDetails();
        }
    };

    @Override
    protected void onInitialization() {
        SYNC_STARTED.addListener(onSyncStartListener);
        SYNC_COMPLETED.addListener(onSyncCompleteListener);
        FORM_SUBMITTED.addListener(onFormSubmittedListener);

        webView.loadUrl("file:///android_asset/www/home.html");
        webView.addJavascriptInterface(new HomeController(this, updateController), "context");
    }

    @Override
    protected void onResumption() {
        if (updateMenuItem != null) {
            if (context.allSettings().fetchIsSyncInProgress()) {
                updateMenuItem.setActionView(R.layout.progress);
            } else
                updateMenuItem.setActionView(null);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        updateMenuItem = menu.findItem(R.id.updateMenuItem);
        return true;
    }
}
