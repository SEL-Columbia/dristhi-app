package org.ei.drishti.provider;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.R;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.view.contract.ECChildClient;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.DialogOption;
import org.ei.drishti.view.viewModel.NativeECSmartRegisterViewModel;

import java.util.List;

import static java.text.MessageFormat.format;
import static org.ei.drishti.view.controller.ECSmartRegisterController.*;

public class ECSmartRegisterClientsProvider
        implements SmartRegisterClientsProvider, View.OnClickListener {

    private final LayoutInflater inflater;
    private final Context context;
    protected ECClients clients;

    public ECSmartRegisterClientsProvider(Context context, ECClients clients) {
        this.clients = clients;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(ECClient client, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        NativeECSmartRegisterViewModel viewModel;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_ec_client, null);
            viewModel = new NativeECSmartRegisterViewModel(itemView);
            itemView.setTag(viewModel);
        } else {
            itemView = (ViewGroup) convertView;
            viewModel = (NativeECSmartRegisterViewModel) itemView.getTag();
        }

        setupClientProfileView(client, viewModel);
        setupGPLSAView(client, viewModel);
        setupFPMethodView(client, viewModel);
        setupChildrenView(client, viewModel);
        setupStatusView(client, viewModel);

        return itemView;
    }

    private void setupFPMethodView(ECClient client, NativeECSmartRegisterViewModel viewModel) {
        FPMethod fpMethod = client.fpMethod();
        viewModel.refreshAllFPMethodDetailViews(context.getResources().getColor(R.color.text_black));
        viewModel.fpMethodView().setText(fpMethod.displayName());
        viewModel.fpMethodDateView().setVisibility(View.VISIBLE);
        viewModel.fpMethodDateView().setText(client.familyPlanningMethodChangeDate());

        if (fpMethod == FPMethod.NONE) {
            viewModel.fpMethodView().setTextColor(Color.RED);
            viewModel.fpMethodDateView().setVisibility(View.GONE);
        } else if (fpMethod == FPMethod.OCP) {
            viewModel.fpMethodQuantityLabelView().setVisibility(View.VISIBLE);
            viewModel.fpMethodQuantityView().setVisibility(View.VISIBLE);
            viewModel.fpMethodQuantityView().setText(client.numberOfOCPDelivered());
        } else if (fpMethod == FPMethod.CONDOM) {
            viewModel.fpMethodQuantityLabelView().setVisibility(View.VISIBLE);
            viewModel.fpMethodQuantityView().setVisibility(View.VISIBLE);
            viewModel.fpMethodQuantityView().setText(client.numberOfCondomsSupplied());
        } else if (fpMethod == FPMethod.CENTCHROMAN) {
            viewModel.fpMethodQuantityLabelView().setVisibility(View.VISIBLE);
            viewModel.fpMethodQuantityView().setVisibility(View.VISIBLE);
            viewModel.fpMethodQuantityView().setText(client.numberOfCondomsSupplied());
        } else if (fpMethod == FPMethod.IUD) {
            if (StringUtils.isNotBlank(client.iudPerson())) {
                viewModel.iudPersonView().setVisibility(View.VISIBLE);
                viewModel.iudPersonView().setText(client.iudPerson());
            }
            if (StringUtils.isNotBlank(client.iudPlace())) {
                viewModel.iudPlaceView().setVisibility(View.VISIBLE);
                viewModel.iudPlaceView().setText(client.iudPlace());
            }
            if (StringUtils.isNotBlank(client.iudPerson()) && StringUtils.isNotBlank(client.iudPlace())) {
                viewModel.iudPlacePersonSeparatorView().setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupStatusView(ECClient client, NativeECSmartRegisterViewModel viewModel) {
        String statusType = client.status().get(STATUS_TYPE_FIELD);
        String statusDate = client.status().get(STATUS_DATE_FIELD);
        viewModel.hideAllStatusLayouts();
        ViewGroup statusViewGroup = viewModel.statusLayout(statusType);
        statusViewGroup.setVisibility(View.VISIBLE);
        viewModel.getStatusDateView(statusViewGroup).setText(statusDate);

        if (EC_STATUS.equalsIgnoreCase(statusType) || FP_STATUS.equalsIgnoreCase(statusType)) {
            viewModel.getStatusTypeView(statusViewGroup)
                    .setText(StringUtils.upperCase(statusType));
        } else if (ANC_STATUS.equalsIgnoreCase(statusType)) {
            viewModel.getANCStatusEDDDateView(statusViewGroup)
                    .setText(client.status().get(STATUS_EDD_FIELD));
        } else if (PNC_FP_STATUS.equalsIgnoreCase(statusType)) {
            viewModel.getFPStatusDateView(statusViewGroup)
                    .setText(client.status().get(FP_METHOD_DATE_FIELD));
        }
    }

    private void setupClientProfileView(ECClient client, NativeECSmartRegisterViewModel viewModel) {
        viewModel.txtNameView().setText(client.name());
        viewModel.txtHusbandNameView().setText(client.husbandName());
        viewModel.txtVillageNameView().setText(client.village());
        viewModel.txtAgeView().setText(
                format(context.getResources().getString(R.string.ec_register_wife_age), client.age()));
        viewModel.txtECNumberView().setText(String.valueOf(client.ecNumber()));
        viewModel.badgeHPView().setVisibility(client.isHighPriority() ? View.VISIBLE : View.GONE);
        viewModel.badgeBPLView().setVisibility(client.isBPL() ? View.VISIBLE : View.GONE);
        viewModel.badgeSCView().setVisibility(client.isSC() ? View.VISIBLE : View.GONE);
        viewModel.badgeSTView().setVisibility(client.isST() ? View.VISIBLE : View.GONE);
    }

    private void setupGPLSAView(ECClient client, NativeECSmartRegisterViewModel viewModel) {
        viewModel.txtGravida().setText(client.numberOfPregnancies());
        viewModel.txtParity().setText(client.parity());
        viewModel.txtNumberOfLivingChildren().setText(client.numberOfLivingChildren());
        viewModel.txtNumberOfStillBirths().setText(client.numberOfStillbirths());
        viewModel.txtNumberOfAbortions().setText(client.numberOfAbortions());
    }

    private void setupChildrenView(ECClient client, NativeECSmartRegisterViewModel viewModel) {
        List<ECChildClient> children = client.children();
        if (children.size() == 0) {
            viewModel.maleChildrenView().setVisibility(View.GONE);
            viewModel.femaleChildrenView().setVisibility(View.GONE);
        } else if (children.size() == 1) {
            ECChildClient child = children.get(0);
            setupChildView(viewModel, child);
            if (child.isMale()) {
                viewModel.femaleChildrenView().setVisibility(View.GONE);
                ((LinearLayout.LayoutParams) viewModel.maleChildrenView().getLayoutParams()).weight = 100;
            } else {
                viewModel.maleChildrenView().setVisibility(View.GONE);
                ((LinearLayout.LayoutParams) viewModel.femaleChildrenView().getLayoutParams()).weight = 100;
            }
        } else {
            ((LinearLayout.LayoutParams) viewModel.maleChildrenView().getLayoutParams()).weight = 50;
            ((LinearLayout.LayoutParams) viewModel.femaleChildrenView().getLayoutParams()).weight = 50;
            setupChildView(viewModel, children.get(0));
            setupChildView(viewModel, children.get(1));
        }
    }

    private void setupChildView(NativeECSmartRegisterViewModel viewModel, ECChildClient child) {
        if (child.isMale()) {
            viewModel.maleChildrenView().setVisibility(View.VISIBLE);
            viewModel.maleChildrenView().setText(
                    format(context.getResources().getString(R.string.ec_register_male_child),
                            child.getAgeInMonths()));
        } else {
            viewModel.femaleChildrenView().setVisibility(View.VISIBLE);
            viewModel.femaleChildrenView().setText(
                    format(context.getResources().getString(R.string.ec_register_female_child),
                            child.getAgeInMonths()));
        }
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public ECClients getListItems() {
        return clients;
    }

    @Override
    public SmartRegisterClients sortBy(DialogOption sortBy) {
        return sortBy.apply(clients);
    }

    @Override
    public SmartRegisterClients filterBy(DialogOption filterBy) {
        return filterBy.apply(clients);
    }

    @Override
    public ECClients filter(CharSequence cs) {
        String filterCriterion = cs.toString().toLowerCase();
        return clients.applyFilter(filterCriterion);
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public void showSection(String section) {
        // do Nothing;
    }
}
