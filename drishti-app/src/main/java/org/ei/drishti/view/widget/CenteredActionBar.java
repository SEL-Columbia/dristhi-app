package org.ei.drishti.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.R;

public class CenteredActionBar extends ActionBar {
    public CenteredActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initActionBar();
    }

    private void initActionBar() {
        setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout actionsView = (LinearLayout) findViewById(R.id.actionbar_actions);
        LayoutParams layoutParams = (LayoutParams) actionsView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
    }

}
