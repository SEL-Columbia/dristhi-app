package org.ei.drishti.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.domain.Criterion;

import java.util.ArrayList;
import java.util.List;

public class DialogAction implements ActionBar.Action {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Criterion[] options;
    private int icon;

    public DialogAction(Context context, int icon, String title, Criterion... options) {
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
        builder.setSingleChoiceItems(buildDisplayItemsFrom(options), 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                onSelectionChangeListener.selectionChanged(options[item]);
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }

    private String[] buildDisplayItemsFrom(Criterion[] options) {
        List<String> displayItems = new ArrayList<String>();
        for (Criterion option : options) {
            displayItems.add(option.displayValue());
        }
        return displayItems.toArray(new String[options.length]);
    }
}
