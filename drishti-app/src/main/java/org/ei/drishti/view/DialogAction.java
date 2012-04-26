package org.ei.drishti.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.R;

public class DialogAction implements ActionBar.Action {
    private int selection = 0;
    private AlertDialog.Builder builder;
    private CharSequence[] items;
    private AlertDialog dialog;

    public DialogAction(Context context) {
        items = new CharSequence[]{"All", "Past Due", "Upcoming"};
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Filter by Time");
    }

    public int getDrawable() {
        return R.drawable.icon;
    }

    public void performAction(View view) {
        dialog.show();
    }

    public void setOnSelectionChangedListener(final OnSelectionChangeListener onSelectionChangeListener) {
        builder.setSingleChoiceItems(items, selection, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selection = item;
                onSelectionChangeListener.selectionChanged(items[item].toString());
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }
}
