package org.ei.drishti.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.markupartist.android.widget.ActionBar;

import java.util.ArrayList;
import java.util.List;

public class DialogAction<T> implements ActionBar.Action {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private T[] options;
    private int icon;

    public DialogAction(Context context, int icon, String title, T... options) {
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

    public void setOnSelectionChangedListener(final OnSelectionChangeListener<T> onSelectionChangeListener) {
        builder.setSingleChoiceItems(buildDisplayItemsFrom(options), 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                onSelectionChangeListener.selectionChanged(options[item]);
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }

    private String[] buildDisplayItemsFrom(T[] options) {
        List<String> displayItems = new ArrayList<String>();
        for (T option : options) {
            displayItems.add(option.toString());
        }
        return displayItems.toArray(new String[options.length]);
    }
}
