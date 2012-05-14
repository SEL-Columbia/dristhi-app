package org.ei.drishti.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.R;
import org.ei.drishti.domain.Displayable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DialogAction<T extends Displayable> implements ActionBar.Action {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private T[] options;
    private int icon;
    private final Activity context;
    private View viewOfLatestAction;
    private OnSelectionChangeListener<T> onSelectionChangeListener;

    public DialogAction(Activity context, int icon, String title, T... options) {
        this.options = options;
        this.icon = icon;
        this.context = context;
        builder = new AlertDialog.Builder(this.context);
        builder.setTitle(title);
    }

    public int getDrawable() {
        return icon;
    }

    public void performAction(View view) {
        dialog.show();
    }

    public void setOnSelectionChangedListener(final OnSelectionChangeListener<T> onSelectionChangeListener) {
        LinearLayout actionItemsLayout = (LinearLayout) context.findViewById(R.id.actionbar_actions);
        viewOfLatestAction = actionItemsLayout.getChildAt(actionItemsLayout.getChildCount() - 1);
        this.onSelectionChangeListener = onSelectionChangeListener;
        onOptionsChanged(asList(options));
    }

    public void onOptionsChanged(final List<T> newOptions) {
        builder.setSingleChoiceItems(buildDisplayItemsFrom(newOptions), 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (onSelectionChangeListener != null) {
                    onSelectionChangeListener.selectionChanged(viewOfLatestAction, newOptions.get(item));
                }
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }

    private String[] buildDisplayItemsFrom(List<T> options) {
        List<String> displayItems = new ArrayList<String>();
        for (T option : options) {
            displayItems.add(option.displayValue());
        }
        return displayItems.toArray(new String[options.size()]);
    }
}
