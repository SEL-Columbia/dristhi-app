package org.ei.drishti.provider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.R;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.view.contract.ECChildClient;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.controller.ECSmartRegisterController;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;
import org.ei.drishti.view.viewHolder.NativeECSmartRegisterViewHolder;

import java.util.List;

import static java.text.MessageFormat.format;
import static org.ei.drishti.util.DateUtil.formatDate;
import static org.ei.drishti.view.controller.ECSmartRegisterController.*;

public class ECSmartRegisterClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    protected ECSmartRegisterController controller;
    private Drawable womanPlaceHolderDrawable;
    private Drawable iconPencilDrawable;

    public ECSmartRegisterClientsProvider(Context context,
                                          View.OnClickListener onClickListener,
                                          ECSmartRegisterController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(ECClient client, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        NativeECSmartRegisterViewHolder viewModel;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_ec_client, null);
            viewModel = new NativeECSmartRegisterViewHolder(itemView);
            itemView.setTag(viewModel);
        } else {
            itemView = (ViewGroup) convertView;
            viewModel = (NativeECSmartRegisterViewHolder) itemView.getTag();
        }

        setupClientProfileView(client, viewModel);
        setupGPLSAView(client, viewModel);
        setupFPMethodView(client, viewModel);
        setupChildrenView(client, viewModel);
        setupStatusView(client, viewModel);
        setupEditView(client, viewModel);

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.list_item_height));
        itemView.setLayoutParams(layoutParams);

        return itemView;
    }

    private void setupFPMethodView(ECClient client, NativeECSmartRegisterViewHolder viewModel) {
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
        }
    }

    private void setupStatusView(ECClient client, NativeECSmartRegisterViewHolder viewModel) {
        String statusType = client.status().get(STATUS_TYPE_FIELD);
        String statusDate = formatDate(client.status().get(STATUS_DATE_FIELD));
        viewModel.hideAllStatusLayouts();

        ViewGroup statusViewGroup = viewModel.statusLayout(statusType);
        statusViewGroup.setVisibility(View.VISIBLE);
        viewModel.getStatusDateView(statusViewGroup).setText(statusDate);

        if (EC_STATUS.equalsIgnoreCase(statusType) || FP_STATUS.equalsIgnoreCase(statusType)) {
            viewModel.getStatusTypeView(statusViewGroup)
                    .setText(StringUtils.upperCase(statusType));
        } else if (ANC_STATUS.equalsIgnoreCase(statusType)) {
            viewModel.getANCStatusEDDDateView(statusViewGroup)
                    .setText(formatDate(client.status().get(STATUS_EDD_FIELD)));
        } else if (PNC_FP_STATUS.equalsIgnoreCase(statusType)) {
            viewModel.getFPStatusDateView(statusViewGroup)
                    .setText(formatDate(client.status().get(FP_METHOD_DATE_FIELD)));
        }
    }

    private void setupClientProfileView(ECClient client, NativeECSmartRegisterViewHolder viewModel) {
        if (womanPlaceHolderDrawable == null) {
            womanPlaceHolderDrawable = context.getResources().getDrawable(R.drawable.woman_placeholder);
        }
        viewModel.imgProfileView().setBackground(womanPlaceHolderDrawable);
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
        viewModel.profileInfoLayout().setOnClickListener(onClickListener);
        viewModel.profileInfoLayout().setTag(client);
    }

    private void setupGPLSAView(ECClient client, NativeECSmartRegisterViewHolder viewModel) {
        viewModel.txtGravida().setText(client.numberOfPregnancies());
        viewModel.txtParity().setText(client.parity());
        viewModel.txtNumberOfLivingChildren().setText(client.numberOfLivingChildren());
        viewModel.txtNumberOfStillBirths().setText(client.numberOfStillbirths());
        viewModel.txtNumberOfAbortions().setText(client.numberOfAbortions());
    }

    private void setupChildrenView(ECClient client, NativeECSmartRegisterViewHolder viewModel) {
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

    private void setupChildView(NativeECSmartRegisterViewHolder viewModel, ECChildClient child) {
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

    private void setupEditView(ECClient client, NativeECSmartRegisterViewHolder viewModel) {
        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewModel.editButton().setImageDrawable(iconPencilDrawable);
        viewModel.editButton().setOnClickListener(onClickListener);
        viewModel.editButton().setTag(client);
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

    public LayoutInflater inflater() {
        return inflater;
    }
}
