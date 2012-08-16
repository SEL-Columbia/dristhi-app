package org.ei.drishti.view;

import android.view.View;
import android.widget.ProgressBar;

public class AndroidProgressIndicator implements ProgressIndicator {
    private ProgressBar progressBar;

    public AndroidProgressIndicator(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void setVisibile() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setInvisible() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
