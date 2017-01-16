package org.ei.drishti.commonregistryexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;

import org.ei.drishti.R;
import org.ei.drishti.commonregistry.PersonObjectClient;
import org.ei.drishti.commonregistry.PersonObjectController;

import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;
import org.ei.drishti.view.viewHolder.OnClickFormLauncher;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by user on 2/12/15.
 */
public class PersonClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected PersonObjectController controller;

    public PersonClientsProvider(Context context,
                                 View.OnClickListener onClickListener,
                                 PersonObjectController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(R.color.text_black);
    }

    @Override
    public View getView(SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;

        itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_person_client, null);
        TextView id = (TextView)itemView.findViewById(R.id.id);
        TextView name = (TextView)itemView.findViewById(R.id.name);
        TextView age = (TextView)itemView.findViewById(R.id.age);
        TextView sex = (TextView)itemView.findViewById(R.id.sex);
        TextView resistance_type = (TextView)itemView.findViewById(R.id.resistance_type);
        TextView patient_type = (TextView)itemView.findViewById(R.id.patient_type);
        TextView risk_factors = (TextView)itemView.findViewById(R.id.risk_factors);
        TextView treatment_start_date = (TextView)itemView.findViewById(R.id.treatment_start_date);
        TextView start_date = (TextView)itemView.findViewById(R.id.start_date);
        TextView current = (TextView)itemView.findViewById(R.id.current);
        TextView current_drug = (TextView)itemView.findViewById(R.id.current_drug);
        TextView baseline_bmi = (TextView)itemView.findViewById(R.id.baseline_bmi);
        TextView current_bmi = (TextView)itemView.findViewById(R.id.current_bmi);
        TextView baseline_smear = (TextView)itemView.findViewById(R.id.baseline_smear);
        TextView baseline_drt = (TextView)itemView.findViewById(R.id.baseline_drt);
        TextView latest_smear = (TextView)itemView.findViewById(R.id.latest_smear);
        TextView latest_drt = (TextView)itemView.findViewById(R.id.latest_drt);
        Button follow_up = (Button)itemView.findViewById(R.id.follow_up);
        follow_up.setOnClickListener(onClickListener);
        follow_up.setTag(smartRegisterClient);

        PersonObjectClient pc = (PersonObjectClient) smartRegisterClient;

        id.setText(pc.getDetails().get("case_id")!=null?pc.getCaseId():"");
        name.setText(pc.getName()!=null?pc.getName():"");
        age.setText(pc.getDetails().get("age")!=null?pc.getDetails().get("age"):"");
        sex.setText(pc.getDetails().get("sex")!=null?pc.getDetails().get("sex"):"");
        resistance_type.setText(pc.getDetails().get("resistance_type")!=null?pc.getDetails().get("resistance_type"):"");
        patient_type.setText(pc.getDetails().get("patient_type")!=null?pc.getDetails().get("patient_type"):"");
        risk_factors.setText(pc.getDetails().get("risk_factors")!=null?pc.getDetails().get("risk_factors"):"");
        baseline_bmi.setText(pc.getDetails().get("bmi")!=null?pc.getDetails().get("bmi"):"");
        current_bmi.setText(pc.getDetails().get("current_bmi")!=null?pc.getDetails().get("current_bmi"):"");
        baseline_smear.setText(pc.getDetails().get("smear")!=null?pc.getDetails().get("smear"):"");
        baseline_drt.setText(pc.getDetails().get("resistance_drugs")!=null?pc.getDetails().get("resistance_drugs"):"");
        latest_smear.setText(pc.getDetails().get("current_smear")!=null?pc.getDetails().get("current_smear"):"");
        latest_drt.setText(pc.getDetails().get("current_resistance_drugs")!=null?pc.getDetails().get("current_resistance_drugs"):"");
        current_drug.setText(pc.getDetails().get("current_resistance_drugs")!=null?pc.getDetails().get("current_resistance_drugs"):"");
        treatment_start_date.setText(pc.getDetails().get("drug_regimen_start")!=null?pc.getDetails().get("drug_regimen_start"):"");
        start_date.setText(pc.getDetails().get("current_drug_regimen_start")!=null?pc.getDetails().get("current_drug_regimen_start"):"");
        current.setText("(10 mo)");

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    @Override
    public SmartRegisterClients getClients() {
        return controller.getClients();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        // do nothing.
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    public LayoutInflater inflater() {
        return inflater;
    }
}
