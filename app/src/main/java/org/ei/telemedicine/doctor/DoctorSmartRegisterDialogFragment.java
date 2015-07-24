package org.ei.telemedicine.doctor;


import org.ei.telemedicine.R;
import org.ei.telemedicine.util.StringUtil;
import org.ei.telemedicine.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.telemedicine.view.dialog.DialogOption;
import org.ei.telemedicine.view.dialog.DialogOptionModel;
import org.ei.telemedicine.view.dialog.SmartRegisterDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Struct;
import java.util.ArrayList;

public class DoctorSmartRegisterDialogFragment extends DialogFragment {
    //    private final SecuredNativeSmartRegisterActivity parentActivity;
    private Context context;
    //    private final DialogOption[] options;
    private ArrayList<String> values;
    //    private final DialogOptionModel dialogOptionModel;
    private final Object tag;
    private Activity activity;

    private DoctorSmartRegisterDialogFragment(Context context,
                                              ArrayList<String> values,
                                              Object tag) {
//        this.parentActivity = activity;
        this.activity = (Activity) context;
        this.context = context;
        this.values = values;
//        this.options = dialogOptionModel.getDialogOptions();
//        this.dialogOptionModel = dialogOptionModel;
        this.tag = tag;
    }

    public static DoctorSmartRegisterDialogFragment newInstance(
            Activity activity,
            ArrayList<String> values, String tag) {
        return new DoctorSmartRegisterDialogFragment(activity, values, tag);
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

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, R.layout.smart_register_dialog_list_item, values) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewGroup itemView;
                if (convertView == null) {
                    itemView = (ViewGroup) inflater.inflate(R.layout.smart_register_dialog_list_item, parent, false);
                } else {
                    itemView = (ViewGroup) convertView;
                }

                ((TextView) itemView.findViewById(R.id.dialog_list_option))
                        .setText(getItem(position));
                return itemView;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismiss();
                Toast.makeText(context, values.get(i).toString(), Toast.LENGTH_SHORT).show();
                if (tag.toString().equals("Filter")) {
                    String value = values.get(i).toString();
                    if (NativeDoctorActivity.syncAdapter != null)
                        NativeDoctorActivity.syncAdapter.villagesFilter(values.get(i).toString());
                    else
                        NativeDoctorActivity.pendingConsultantBaseAdapter.villagesFilter(values.get(i).toString());
                } else if (tag.toString().equals("Sort")) {
                    Log.e("Coming to Sort", "Sotr");
                    NativeDoctorActivity.sorted_by_name.setText(values.get(i).toString());
//                    NativeDoctorActivity.sort_by = values.get(i).toString();
                    if (NativeDoctorActivity.syncAdapter != null)
                        NativeDoctorActivity.syncAdapter.sort(values.get(i).toString());
                    else
                        NativeDoctorActivity.pendingConsultantBaseAdapter.sort(values.get(i).toString());
                }
//                dialogOptionModel.onDialogOptionSelection(values.get(i), tag);
            }
        });

        return dialogView;
    }
}
