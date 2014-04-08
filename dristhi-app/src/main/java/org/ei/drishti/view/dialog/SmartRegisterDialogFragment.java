package org.ei.drishti.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity;

public class SmartRegisterDialogFragment extends DialogFragment {
    private final SecuredNativeSmartRegisterActivity parentActivity;
    private int dialogId;
    private final DialogOption[] options;

    private SmartRegisterDialogFragment(SecuredNativeSmartRegisterActivity activity,
                                        int dialogId, DialogOption[] options) {
        this.parentActivity = activity;
        this.dialogId = dialogId;
        this.options = options;
    }

    public static SmartRegisterDialogFragment newInstance(
            SecuredNativeSmartRegisterActivity activity, int dialogId, DialogOption[] options) {
        return new SmartRegisterDialogFragment(activity, dialogId, options);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup dialogView = (ViewGroup) inflater.inflate(R.layout.smart_register_dialog_view, container, false);
        ListView listView = (ListView) dialogView.findViewById(R.id.dialog_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        final ArrayAdapter<DialogOption> adapter = new ArrayAdapter<DialogOption>(
                parentActivity, R.layout.smart_register_dialog_list_item, options) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewGroup itemView;
                if (convertView == null) {
                    itemView = (ViewGroup) inflater.inflate(R.layout.smart_register_dialog_list_item, parent, false);
                } else {
                    itemView = (ViewGroup) convertView;
                }

                ((TextView) itemView.findViewById(R.id.dialog_list_option))
                        .setText(getItem(position).name());
                return itemView;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismiss();
                parentActivity.onDialogOptionSelected(dialogId, options[i]);
            }
        });

        return dialogView;
    }

}
