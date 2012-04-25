package org.ei.drishti.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.R;

public class DialogAction implements ActionBar.Action {
    private Context context;

    public DialogAction(Context context) {
        this.context = context;
    }

    public int getDrawable() {
        return R.drawable.icon;
    }

    public void performAction(View view) {
        final CharSequence[] items = {"All", "Past Due", "Upcoming"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Filter by Time");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(context, items[item], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
