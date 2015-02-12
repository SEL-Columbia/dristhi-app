package org.ei.drishti.person;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;
import org.ei.drishti.view.viewHolder.OnClickFormLauncher;

import java.awt.Button;
import java.awt.Image;

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

    protected PersonController controller;

    public PersonClientsProvider(Context context,
                                          View.OnClickListener onClickListener,
                                          PersonController controller) {
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
        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.follow_up);

        PersonClient pc = (PersonClient) smartRegisterClient;

        id.setText(pc.getCaseId());
        name.setText(pc.getName());
        age.setText(pc.getAge());
        sex.setText(pc.getSex());
        resistance_type.setText(pc.getResistanceType());
        patient_type.setText(pc.getPatientType());
        risk_factors.setText(pc.getRiskFactors());
        baseline_bmi.setText(pc.getBmi());
        current_bmi.setText(pc.getCurrentBmi());
        baseline_smear.setText(pc.getSmear());
        baseline_drt.setText(pc.getResistanceDrugs());
        latest_smear.setText(pc.getCurrentSmear());
        latest_drt.setText(pc.getCurrentResistanceDrugs());
        current_drug.setText(pc.getCurrentResistanceDrugs());
        treatment_start_date.setText(pc.getDrugRegimenStart());
        start_date.setText(pc.getCurrentDrugRegimenStart());
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
