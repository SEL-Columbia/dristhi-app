package org.ei.drishti.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.markupartist.android.widget.ActionBar;

public class DialogAction implements ActionBar.Action {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private String[] options;
    private int icon;

    public DialogAction(Context context, int icon, String title, String... options) {
        this.options = options;
        this.icon = icon;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
    }

    public int getDrawable() {
        return icon;
    }

    public void performAction(View view) {
        dialog.show();
    }

    public void setOnSelectionChangedListener(final OnSelectionChangeListener onSelectionChangeListener) {
        builder.setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                onSelectionChangeListener.selectionChanged(options[item]);
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }
}
